package net.manbucy.ipp.cover.auth.pojo.vo;

import lombok.Getter;

/**
 * @author ManBu
 */
@Getter
public class RegInfoCheckResult {

    private final Boolean isInputError;
    private final Boolean isExist;
    private final String msg;

    private RegInfoCheckResult(Boolean isInputError, Boolean isExist, String msg) {
        this.isInputError = isInputError;
        this.isExist = isExist;
        this.msg = msg;
    }

    public static RegInfoCheckResult ok() {
        return new RegInfoCheckResult(false, false, null);
    }

    public static RegInfoCheckResult inputError() {
        return inputError(null);
    }

    public static RegInfoCheckResult inputError(String msg) {
        return new RegInfoCheckResult(true, null, msg);
    }

    public static RegInfoCheckResult exist() {
        return exist(null);
    }

    public static RegInfoCheckResult exist(String msg) {
        return new RegInfoCheckResult(null, true, msg);
    }
}
