package io.goose.framework.web.exception.user;

import io.goose.framework.web.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author goose
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
