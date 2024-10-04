package io.goose.cloud.gateway.security.utils;

import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;
import io.goose.cloud.gateway.security.domain.auth.ResponseUserToken;
import io.goose.cloud.gateway.security.domain.auth.Role;
import io.goose.cloud.gateway.security.domain.auth.UserDetail;
import io.goose.cloud.gateway.security.exception.CustomException;
import io.goose.cloud.gateway.security.feign.AsyncFeignClient;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: goose
 * createAt: 2019/4/1
 */
@Component
public class JwtUtils {


   @Value("${redis.enabled}")
   private Boolean isRedisEnabled;

   @Autowired
   private AsyncFeignClient asyncFeignClient;

   private int REDIS_TOKEN_TYPE_ACCESS = 1;
   private int REDIS_TOKEN_TYPE_REFRESH = 2;

   private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);
   private Map<String, String> refreshTokenMap = new ConcurrentHashMap<>(32);

   @Resource
   private JwtConfig jwtCfg;

   @Value("${jwt.accessTokenExpiration}")
   private int accessTokenExpiration;

   @Value("${jwt.refreshTokenExpiration}")
   private int refreshTokenExpiration;

   private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


   public UserDetail getUserFromToken(String token) {
      UserDetail userDetail;
      try {
         final Claims claims = getClaimsFromToken(token);
         Long userId = getUserIdFromToken(token);
         String username = claims.getSubject();
//         String roleName = claims.get(JwtConfig.CLAIM_KEY_AUTHORITIES).toString();
//         Role role = Role.builder().name(roleName).build();
         userDetail = new UserDetail(userId, username, null, "");

      } catch (Exception e) {
         userDetail = null;
      }
      return userDetail;
   }

   public Long getUserIdFromToken(String token) {
      Long userId;
      try {
         final Claims claims = getClaimsFromToken(token);
         userId = Long.parseLong(String.valueOf(claims.get(JwtConfig.CLAIM_KEY_USER_ID)));
      } catch (Exception e) {
         userId = (long) 0;
      }
      return userId;
   }

   public String getUsernameFromToken(String token) {
      String username;
      try {
         final Claims claims = getClaimsFromToken(token);
         username = claims.getSubject();
      } catch (Exception e) {
         username = null;
      }
      return username;
   }

   public Date getCreatedDateFromToken(String token) {
      Date created;
      try {
         final Claims claims = getClaimsFromToken(token);
         created = claims.getIssuedAt();
      } catch (Exception e) {
         created = null;
      }
      return created;
   }

   public ResponseUserToken generateTokens(UserDetail userDetail) {
      String accessToken = generateAccessToken(userDetail);
      String refreshToken = generateRefreshToken(userDetail);
      return new ResponseUserToken(accessToken, refreshToken, null);
   }


   public Date getExpirationDateFromToken(String token) {
      Date expiration;
      try {
         final Claims claims = getClaimsFromToken(token);
         expiration = claims.getExpiration();
      } catch (ExpiredJwtException e) {
         expiration = e.getClaims().getExpiration();
      } catch (Exception e) {

         expiration = null;
      }
      return expiration;
   }

   public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
      final Date created = getCreatedDateFromToken(token);
      return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token));
   }


   public Boolean validateToken(String token, UserDetails userDetails) {
      UserDetail userDetail = (UserDetail) userDetails;
      final long userId = getUserIdFromToken(token);
      final String username = getUsernameFromToken(token);
      //        final Date created = getCreatedDateFromToken(token);
      if (isTokenExpired(token)) {
         throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_TOKEN_EXPIRED, ResultCode.EXCEPTION_TOKEN_EXPIRED.getMsg()));
      }
      return (userId == userDetail.getId()
            && username.equals(userDetail.getUsername())
            //                && !isTokenExpired(token)
            //                && !isCreatedBeforeLastPasswordReset(created, userDetail.getLastPasswordResetDate())
      );
   }


   private String generateAccessToken(UserDetail userDetail) {
      Map<String, Object> claims = generateClaims(userDetail);
//      claims.put(JwtConfig.CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()).get(0));
      return generateAccessToken(userDetail.getUsername(), claims);
   }

   private String generateRefreshToken(UserDetail userDetail) {
      Map<String, Object> claims = generateClaims(userDetail);
      // 只授于更新 token 的权限
      String roles[] = new String[]{JwtConfig.ROLE_REFRESH_TOKEN};
      claims.put(JwtConfig.CLAIM_KEY_AUTHORITIES, JSONUtil.toJSON(roles));
      return generateRefreshToken(userDetail.getUsername(), claims);
   }

   public void putToken(String userName, String token, String refreshToken) {
      if (isRedisEnabled) {
         asyncFeignClient.putToken(userName, token, refreshToken, accessTokenExpiration, refreshTokenExpiration);
      } else {
         tokenMap.put(userName, token);
         refreshTokenMap.put(userName, refreshToken);
      }
   }

   public void deleteToken(String userName) {
      if (isRedisEnabled) {
         asyncFeignClient.putToken(userName, "invalid", "invalid", 1, 1);//put invalid token with 1s expire
      } else {
         tokenMap.remove(userName);
         refreshTokenMap.remove(userName);
      }
   }

   public boolean containToken(String userName, String token) {
      if (isRedisEnabled) {
         return asyncFeignClient.existToken(userName, token, REDIS_TOKEN_TYPE_ACCESS);
      } else {
         if (userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
            return true;
         }
      }
      return false;
   }

   public boolean containRefreshToken(String userName, String token) {
      if (isRedisEnabled) {
         return asyncFeignClient.existToken(userName, token, REDIS_TOKEN_TYPE_REFRESH);
      } else {
         if (userName != null && refreshTokenMap.containsKey(userName)
               && refreshTokenMap.get(userName).equals(token)) {
            return true;
         }
      }
      return false;
   }

   private Claims getClaimsFromToken(String token) {
      Claims claims;
      claims = Jwts.parser()
            .setSigningKey(jwtCfg.getSecret())
            .parseClaimsJws(token)
            .getBody();

      return claims;
   }

   private Date generateExpirationDate(long expiration) {
      return new Date(System.currentTimeMillis() + expiration * 1000);
   }

   public Boolean isTokenExpired(String token) {
      final Date expiration = getExpirationDateFromToken(token);
      return expiration.before(new Date());
   }

   private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
      return (lastPasswordReset != null && created.before(lastPasswordReset));
   }

   private Map<String, Object> generateClaims(UserDetail userDetail) {
      Map<String, Object> claims = new HashMap<>(16);
      claims.put(JwtConfig.CLAIM_KEY_USER_ID, userDetail.getId());
      return claims;
   }


   private List<String> authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
      List<String> list = new ArrayList<>();
      for (GrantedAuthority ga : authorities) {
         list.add(ga.getAuthority());
      }
      return list;
   }

   public String validateBeforeRefresh(String accessToken, String refreshToken) {
      Claims refreshClaims, accessClaims;
      String username = null;
      try {
         refreshClaims = Jwts.parser()
               .setSigningKey(jwtCfg.getSecret())
               .parseClaimsJws(refreshToken)
               .getBody();
      } catch (ExpiredJwtException e) {
         return null;
      } catch (Exception e) {
         return null;
      }

      String scope = refreshClaims.get(JwtConfig.CLAIM_KEY_AUTHORITIES, String.class);
      if (StringUtils.isBlank(scope) || !scope.contains(JwtConfig.ROLE_REFRESH_TOKEN)) {
         return null;
      }

      try {
         accessClaims = Jwts.parser()
               .setSigningKey(jwtCfg.getSecret())
               .parseClaimsJws(accessToken)
               .getBody();
      } catch (ExpiredJwtException e) {
         accessClaims = e.getClaims();
      } catch (Exception e) {
         return null;
      }

      if (refreshClaims.getSubject().equals(accessClaims.getSubject()) &&
            refreshClaims.getIssuer().equals(accessClaims.getIssuer())) {
         username = refreshClaims.getSubject();
         if (!containToken(username, accessToken) || !containRefreshToken(username, refreshToken)) {
            username = null;
         }
      }

      return username;
   }

   private String generateRefreshToken(String subject, Map<String, Object> claims) {
      return generateToken(subject, claims, jwtCfg.getRefreshTokenExpiration());
   }

   private String generateAccessToken(String subject, Map<String, Object> claims) {
      return generateToken(subject, claims, jwtCfg.getAccessTokenExpiration());
   }

   private String generateToken(String subject, Map<String, Object> claims, long expiration) {
      return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setId(UUID.randomUUID().toString())
            .setIssuer(JwtConfig.ISSUER)
            .setIssuedAt(new Date())
            .setExpiration(generateExpirationDate(expiration))
            .compressWith(CompressionCodecs.DEFLATE)
            .signWith(SIGNATURE_ALGORITHM, jwtCfg.getSecret())
            .compact();
   }


}
