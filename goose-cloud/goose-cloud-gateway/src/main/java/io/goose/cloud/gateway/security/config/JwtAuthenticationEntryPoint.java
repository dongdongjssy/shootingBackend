package io.goose.cloud.gateway.security.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;



/**
 * @author: goose
 * createAt: 2019/4/1
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        log.info("认证失败：" + authException.getMessage());
        
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = ResultJson.failure(ResultCode.UNAUTHORIZED, authException.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
