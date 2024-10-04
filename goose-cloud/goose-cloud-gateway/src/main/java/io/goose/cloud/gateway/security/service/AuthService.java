package io.goose.cloud.gateway.security.service;

import io.goose.cloud.gateway.security.domain.auth.ResponseUserToken;
import io.goose.cloud.gateway.security.domain.auth.User;

/**
 * @author: goose
 * createAt: 2019/4/1
 */
public interface AuthService {

   void logout(String token);

   /**
    * 刷新Token
    */
   ResponseUserToken refresh(String accessToken, String refreshToken);

   User getUserByToken(String token);

   User findByPhone(String mobile);

   boolean validateToken(String token);

   ResponseUserToken loginBySmsCode(String phone, String smsCode);

   ResponseUserToken loginByPassword(String phone, String password);
}
