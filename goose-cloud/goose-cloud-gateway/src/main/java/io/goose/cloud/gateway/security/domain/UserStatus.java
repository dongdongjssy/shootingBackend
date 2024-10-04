package io.goose.cloud.gateway.security.domain;

public enum UserStatus
{
	PENDING(1), ACTIVE(2), SUSPENDED(3), DELETED(3);
    private final int code;

    UserStatus(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
