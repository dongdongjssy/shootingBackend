package io.goose.common.enums;

public enum ResponseCodes {
	

	UnexpectedError(9008, "Unable to process. Please contact support team"),
	DB_ERROR(9011, "DB error. ");	
    
	

    private final Integer code;
    private final String info;

    ResponseCodes(Integer code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

}
