package io.goose.cloud.gateway.security.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author goose
 * createAt: 2019/4/1
 */
@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private String refreshToken;
    private User user;
}
