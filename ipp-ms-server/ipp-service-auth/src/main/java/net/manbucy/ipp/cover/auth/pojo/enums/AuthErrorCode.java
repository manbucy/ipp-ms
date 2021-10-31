package net.manbucy.ipp.cover.auth.pojo.enums;

/**
 * @author ManBu
 */
public enum AuthErrorCode {
    /**
     * 一级宏观错误
     * 用户端错误
     */
    USER_ENDPOINT_ERROR("A0001", "用户端错误"),

    /**
     * 二级宏观错误
     * 用户注册错误
     */
    USER_REGISTRATION_ERROR("A0100", "用户注册失败"),

    /**
     * 用户注册错误
     * 用户名校验失败
     */
    USERNAME_CHECK_ERROR("A0110", "用户名校验失败"),

    /**
     * 用户注册错误
     * 用户名已存在
     */
    USERNAME_EXIST_ERROR("A0111", "用户名已存在"),

    MOBILE_CHECK_ERROR("A0120", "手机号码校验失败"),

    MOBILE_EXIST_ERROR("A0121", "手机号码已存在"),

    EMAIL_CHECK_ERROR("A0130", "电子邮箱校验失败"),

    EMAIL_EXIST_ERROR("A0131", "电子邮箱已存在"),

    VERIFY_CODE_SEND_ERROR("A0140", "验证码发送错误"),

    VERIFY_CODE_CHECK_ERROR("A0141", "验证码校验失败"),

    PASSWORD_CHECK_ERROR("A0150", "密码校验失败");


    public String code;
    public String msg;

    AuthErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
