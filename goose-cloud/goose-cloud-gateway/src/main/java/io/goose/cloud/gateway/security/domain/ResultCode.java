package io.goose.cloud.gateway.security.domain;

/**
 * @author goose
 * 状态码
 * Created by jt on 2018/3/8.
 */
public enum ResultCode {
    /*
    请求返回状态码和说明信息
     */
    SUCCESS(200, "success"),

    BAD_REQUEST(400, "input data is not correct"),
    UNAUTHORIZED(401, "Authorization fails"),
    FORBIDDEN(403, "forbidden to access"),
    NOT_FOUND(404, "the resource is not there"),
    OPERATE_ERROR(405, "the resource to request is not there"),
    TIME_OUT(408, "timeout"),

    SERVER_ERROR(500, "Internal server error"),

    EXCEPTION_USER_REGISTER(-100, "注册失败"),
    EXCEPTION_PHONE_OR_PASSWORD_EMPTY(-101, "手机号和密码不能为空"),
    EXCEPTION_USER_ROLE_LIMITTED(-102, "User doesn't have the role"),
    EXCEPTION_USER_NOT_EXISTS(-103, "用户不存在"),
    EXCEPTION_LOGIN_ERROR(-104, "登录失败，请确认密码是正确的"),
    EXCEPTION_TOKEN_EMPTY(-105, "Token is empty"),
    EXCEPTION_TOKEN_EXPIRED(-106, "Token is expired"),
    EXCEPTION_PHONE_NOT_EXISTS(-107, "Phone doesn't exist"),
    EXCEPTION_PHONE_EXISTS(-108, "Phone already exists"),
    EXCEPTION_EMAIL_FAILURE(-109, "Failed to send email"),
    EXCEPTION_CODE_NOT_CORRECT(-110, "验证码错误，请重新输入或重新获取"),
    EXCEPTION_PHONE_OR_SMS_CODE_EMPTY(-111, "手机号和验证码不能为空"),
    EXCEPTION_SEND_SMS_CODE(-112, "验证码发送失败，请重试"),
    INVALID_USER_ID(-200, "Invalid user id");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
