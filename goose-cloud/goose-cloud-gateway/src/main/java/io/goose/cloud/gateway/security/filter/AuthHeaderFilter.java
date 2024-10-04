package io.goose.cloud.gateway.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.goose.cloud.gateway.security.domain.auth.User;
import io.goose.cloud.gateway.security.domain.auth.UserDetail;

import java.util.Objects;

@Component
public class AuthHeaderFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestContext requestContext = RequestContext.getCurrentContext();
        if (Objects.nonNull(authentication) && Objects.nonNull(authentication.getPrincipal()) && authentication.getPrincipal() instanceof UserDetail) {
        	 User user = ((UserDetail)authentication.getPrincipal()).getUser();
             requestContext.addZuulRequestHeader("user",JSON.toJSONString(user)); // 登陆用户信息
        	}
        return null;
    }
}