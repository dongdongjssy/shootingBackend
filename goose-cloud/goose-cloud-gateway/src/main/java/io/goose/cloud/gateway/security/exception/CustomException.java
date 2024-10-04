package io.goose.cloud.gateway.security.exception;

import io.goose.cloud.gateway.security.domain.ResultJson;
import lombok.Getter;

/**
 * @author goose
 * createAt: 2019/4/1.
 */
@Getter
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}
