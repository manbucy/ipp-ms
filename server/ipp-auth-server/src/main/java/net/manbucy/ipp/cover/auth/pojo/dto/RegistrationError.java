package net.manbucy.ipp.cover.auth.pojo.dto;

import lombok.Getter;
import net.manbucy.ipp.cover.auth.pojo.enums.AuthErrorCode;

/**
 * @author ManBu
 */
@Getter
public class RegistrationError {
    public static final int TYPE_USERNAME = 1;
    public static final int TYPE_PHONE = 2;
    public static final int TYPE_EMAIL = 3;
    public static final int TYPE_VERIFY_CODE = 4;
    public static final int TYPE_PASSWORD = 5;

    public static final int TYPE_OTHER = 6;

    public final AuthErrorCode errorCode;
    private String msg;

    private final Integer type;

    private boolean exist;
    private boolean inputError;
    private boolean operateError;

    private Long operateLockTime;

    public RegistrationError(Integer type, AuthErrorCode errorCode) {
        this.type = type;
        this.errorCode = errorCode;
    }

    public RegistrationError inputError(String msg) {
        this.inputError = true;
        this.msg = msg;
        return this;
    }

    public RegistrationError exist(String msg) {
        this.exist = true;
        this.msg = msg;
        return this;
    }

    public RegistrationError operateError(String msg) {
        this.operateError = true;
        this.msg = msg;
        return this;
    }

    public RegistrationError lockOperate(Long time) {
        this.operateLockTime = time;
        return this;
    }

}
