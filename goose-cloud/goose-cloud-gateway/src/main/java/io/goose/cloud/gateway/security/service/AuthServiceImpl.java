package io.goose.cloud.gateway.security.service;

import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;
import io.goose.cloud.gateway.security.domain.auth.ResponseUserToken;
import io.goose.cloud.gateway.security.domain.auth.Role;
import io.goose.cloud.gateway.security.domain.auth.User;
import io.goose.cloud.gateway.security.domain.auth.UserDetail;
import io.goose.cloud.gateway.security.exception.CustomException;
import io.goose.cloud.gateway.security.feign.RemitFeignClient;
import io.goose.cloud.gateway.security.repo.RoleRepository;
import io.goose.cloud.gateway.security.repo.UserRepository;
import io.goose.cloud.gateway.security.utils.JwtConfig;
import io.goose.cloud.gateway.security.utils.JwtUtils;
import io.goose.cloud.gateway.security.utils.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author: goose
 * createAt: 2019/4/1
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtTokenUtil;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    // TODO - should move the constants to config file
    private static final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private static final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光API
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    @Resource
    private JwtConfig jwtCfg;

    @Autowired
    RemitFeignClient clientFeign;

    @Autowired
    SmsUtil smsUtil;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            @Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService,
            JwtUtils jwtTokenUtil,
            UserRepository userRepo,
            RoleRepository roleRepo) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public ResponseUserToken loginBySmsCode(String phone, String smsCode) {
    	phone = phone.trim();
        log.info("User Parameter: " + phone + "/" + smsCode);

        if (StringUtils.isAnyBlank(phone, smsCode)) {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_PHONE_OR_SMS_CODE_EMPTY));
        }

        if (!smsUtil.containCode(smsUtil.formatPhone(phone), smsCode)) {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_CODE_NOT_CORRECT));
        }

        User user = userRepo.findByPhone(phone);

        // 用户不存在，自动注册
        if (user == null) {
            log.info("automatically register user using phone");

            user = new User();
            user.setUserName(phone);
            user.setPhone(phone);
            user.setJgUsername(phone);
            user.setNickname(phone);
            user.setStatus(0);

            try {
                userRepo.save(user);
            } catch (Exception e) {
                throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_USER_REGISTER));
            }

            if (user.getId() != null) {
                // 添加用户到极光
                try {
                    RegisterInfo registerInfo = RegisterInfo.newBuilder()
                            .setUsername(user.getJgUsername())
                            .setPassword(user.getJgUsername())
                            .setNickname(user.getNickname())
                            .addExtra("phone", user.getPhone())
                            .build();

                    JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
                    log.info("***【极光】*** 添加用户到极光成功: " + registerInfo);
                } catch (Exception e) {
                	JSONArray jsonArray= JSONArray.parseArray(e.getMessage());
                	JSONObject jsonObject =  (JSONObject)jsonArray.get(0);
                	Map<String,Object> mapError = (Map<String, Object>) jsonObject.get("error");
                	if(!mapError.get("code").toString().equals("899001")) {
                		 log.error("***【极光】*** 添加用户到极光失败!");
                         log.error("***【极光】*** " + e.getMessage());
                         userRepo.delete(user);
                         throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_USER_REGISTER));
                	}
                }
            }
        }

        // 用户存在或注册成功后，尝试登录
        if (user.getId() != null) {
            // set roles
            if(!StringUtils.isBlank(user.getRoleIds())) {
                String[] roleIds = StringUtils.split(user.getRoleIds(), ",");
                for (String roleId : roleIds) {
                    try {
                        Optional<Role> role = roleRepo.findById(Integer.parseInt(roleId));
                        if (role.isPresent()) {
                            if (user.getRoles() == null) {
                                Set<Role> roles = new HashSet<>();
                                roles.add(role.get());
                                user.setRoles(roles);
                            } else {
                                user.getRoles().add(role.get());
                            }
                        }
                    } catch (NumberFormatException e){
                        continue;
                    }
                }
            }

            UserDetail userDetail = new UserDetail(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getSmsCode(), userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            ResponseUserToken res = generateToken(user.getUserName(), userDetail);
            res.setUser(user);

            return res;
        } else {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_LOGIN_ERROR));
        }
    }

    @Override
    public ResponseUserToken loginByPassword(String phone, String password) {
        log.info("Login by phone and password: " + phone + "/" + password);

        if (StringUtils.isAnyBlank(phone, password)) {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_PHONE_OR_PASSWORD_EMPTY));
        }

        User user = userRepo.findByPhone(phone);
        if(user != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(password, user.getPassword())){
                throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_LOGIN_ERROR));
            } else {
                UserDetail userDetail = new UserDetail(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), password, userDetail.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                ResponseUserToken res = generateToken(user.getUserName(), userDetail);
                res.setUser(user);

                return res;
            }
        } else {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_USER_NOT_EXISTS));
        }
    }

    private ResponseUserToken generateToken(String username, UserDetail userDetail) {
        if (userDetail == null || userDetail.getUser() == null) {
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_LOGIN_ERROR));
        }
        ResponseUserToken res = jwtTokenUtil.generateTokens(userDetail);
        if (res != null) {
            jwtTokenUtil.putToken(username, res.getToken(), res.getRefreshToken());
        }

        return res;
    }

    @Override
    public void logout(String token) {
        token = token.replace(jwtCfg.getPrefix(), "");
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
    }

    @Override
    public ResponseUserToken refresh(String accessToken, String refreshToken) {
        accessToken = accessToken.replace(jwtCfg.getPrefix(), "");
        refreshToken = refreshToken.replace(jwtCfg.getPrefix(), "");
        String username = jwtTokenUtil.validateBeforeRefresh(accessToken, refreshToken);
        if (StringUtils.isBlank(username)) {
            return null;
        }
        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (!jwtTokenUtil.canTokenBeRefreshed(refreshToken, userDetail.getUser().getPasswordResetDate()))
            return null;

        ResponseUserToken res = jwtTokenUtil.generateTokens(userDetail);
        if (res != null) {
            jwtTokenUtil.putToken(username, res.getToken(), res.getRefreshToken());
        }
        return res;
    }

    @Override
    public User getUserByToken(String token) {
        if (token == null)
            throw new CustomException(ResultJson.failure(ResultCode.EXCEPTION_TOKEN_EMPTY, ResultCode.EXCEPTION_TOKEN_EMPTY.getMsg()));

        token = token.replace(jwtCfg.getPrefix(), "");
        UserDetail userDetail = jwtTokenUtil.getUserFromToken(token);
        if (userDetail == null)
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "登录过期"));

        return userDetail.getUser();
    }

    @Override
    public boolean validateToken(String token) {
        token = token.replace(jwtCfg.getPrefix(), "");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            return false;
        }

        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (userDetail == null || !jwtTokenUtil.canTokenBeRefreshed(token, userDetail.getUser().getPasswordResetDate()))
            return false;

        return true;
    }

    @Override
    public User findByPhone(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        return userRepo.findByPhone(mobile);
    }
}
