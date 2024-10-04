package io.goose.cloud.gateway.security.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;
import io.goose.cloud.gateway.security.domain.auth.UserDetail;
import io.goose.cloud.gateway.security.exception.CustomException;
import io.goose.cloud.gateway.security.utils.JwtConfig;
import io.goose.cloud.gateway.security.utils.JwtUtils;

/**
 * token校验
 * @author: goose
 * createAt: 2019/4/1
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;
    
    @Resource
    private JwtConfig jwtCfg;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username=null;
        String bearer = jwtCfg.getPrefix();
    	
    	//filter authorization token
    	String auth_token = request.getHeader(jwtCfg.getHeader());
        if(StringUtils.isNotBlank(auth_token) && auth_token.startsWith(bearer) ) {
            auth_token = auth_token.replace(bearer, "");        
            username = jwtUtils.getUsernameFromToken(auth_token);       
        }
        
        if(StringUtils.isBlank(auth_token) ||
        		(StringUtils.isNotBlank(username) && 
        				!jwtUtils.containToken(username, auth_token))) {
        	chain.doFilter(request, response);
        	return;
        }
        
        //handle refresh token, since the auth token could expired
        if(StringUtils.isBlank(username) && request.getRequestURI().endsWith("/refresh")) {
        	auth_token = request.getHeader(jwtCfg.getRefreshTokenHeader());
            if(StringUtils.isNotBlank(auth_token) && auth_token.startsWith(bearer) ) {
                auth_token = auth_token.replace(bearer, "");        
                username = jwtUtils.getUsernameFromToken(auth_token);              	
            }
            if(StringUtils.isBlank(username) || 
            		(StringUtils.isNotBlank(username) && 
            				!jwtUtils.containRefreshToken(username, auth_token))) {
            	chain.doFilter(request, response);
            	return;
            }
        	
        }        
   
        logger.info(String.format("Checking authentication for user %s.", username));

        if (StringUtils.isNotBlank(username)  
        		&& SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetail userDetail = jwtUtils.getUserFromToken(auth_token);
            if (userDetail != null && jwtUtils.validateToken(auth_token, userDetail)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated user %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        Long userId = jwtUtils.getUserIdFromToken(auth_token);
        String headerUserId = request.getHeader("userId");
        if(headerUserId == null) {
        	;//Nothing
        }
        else if(userId != null && userId != 0 && Long.parseLong(headerUserId) != userId) {
        	response.getWriter().write(ResultJson.failure(ResultCode.INVALID_USER_ID, ResultCode.INVALID_USER_ID.getMsg()).toString());
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    
}
