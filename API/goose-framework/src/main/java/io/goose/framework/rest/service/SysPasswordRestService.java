package io.goose.framework.rest.service;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.goose.common.constant.Constants;
import io.goose.framework.manager.AsyncManager;
import io.goose.framework.manager.factory.AsyncFactory;
import io.goose.framework.util.MessageUtils;
import io.goose.framework.web.exception.user.UserPasswordNotMatchException;
import io.goose.framework.web.exception.user.UserPasswordRetryLimitExceedException;
import io.goose.framework.web.service.ISysPasswordService;
import io.goose.system.domain.SysUser;

/**
 * 登录密码方法
 * 
 * @author goose
 */
@Component
@ConditionalOnProperty(name = "shiro.disable", havingValue = "true")
public class SysPasswordRestService implements ISysPasswordService 
{
//    @Autowired
//    private CacheManager cacheManager;
//
//    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

//    @PostConstruct
//    public void init()
//    {
//        loginRecordCache = cacheManager.getCache("loginRecordCache");
//    }

    public void validate(SysUser user, String password)
    {
        String loginName = user.getLoginName();
        
        //TODO: will use ehcache/redis to implement this instead

//        AtomicInteger retryCount = loginRecordCache.get(loginName);

//        if (retryCount == null)
//        {
//            retryCount = new AtomicInteger(0);
//            loginRecordCache.put(loginName, retryCount);
//        }
//        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
//            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
//        }

        if (!matches(user, password))
        {
            //AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
//            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
//        else
//        {
//            clearLoginRecordCache(loginName);
//        }
    }

    public boolean matches(SysUser user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

//    public void clearLoginRecordCache(String username)
//    {
//        loginRecordCache.remove(username);
//    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

}
