package io.goose.cloud.gateway.security.domain;

import lombok.Data;

import java.io.Serializable;

import io.goose.cloud.gateway.security.utils.TextUtil;
import io.goose.common.utils.StringUtils;

/**
 * @author goose
 * RESTful API 返回类型
 * Created at 2018/3/8.
 */
@Data
public class ResultJson<T> implements Serializable{

    private static final long serialVersionUID = 783015033603078674L;
    private int code;
    private String msg;
    private T data;

    public static ResultJson ok() {
        return ok("");
    }

    public static ResultJson ok(Object o) {
        return new ResultJson(ResultCode.SUCCESS, o);
    }

    public static ResultJson failure(ResultCode code) {
        return failure(code, "");
    }

    public static ResultJson failure(ResultCode code, Object o) {
        return new ResultJson(code, o);
    }

    public ResultJson (ResultCode resultCode) {
        setResultCode(resultCode);
    }

    public ResultJson (ResultCode resultCode,T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode) {
    	if(resultCode != null ) {
    		this.code = resultCode.getCode();
    		this.msg = resultCode.getMsg();
    		String msgProp = TextUtil.message(resultCode.name());
    		if(StringUtils.isNotEmpty(msgProp)) {
    			this.msg = msgProp;
    		}
    				
    	}
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":\"" + data + '\"'+
                '}';
    }
}
