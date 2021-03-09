package net.manbucy.ipp.cover.auth.pojo.vo;

/**
 * @author ManBu
 */
public class VerifyCodeSendResult {
    private final Boolean isInputError;
    private final Boolean isSuccess;
    private final String msg;

    private VerifyCodeSendResult(Boolean isInputError, Boolean isSuccess, String msg) {
        this.isInputError = isInputError;
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public static VerifyCodeSendResult ok() {
        return new VerifyCodeSendResult(false, true, null);
    }

    public static VerifyCodeSendResult fail() {
        return fail(null);
    }

    public static VerifyCodeSendResult fail(String msg) {
        return new VerifyCodeSendResult(false, false, msg);
    }

    public static VerifyCodeSendResult inputError() {
        return inputError(null);
    }

    public static VerifyCodeSendResult inputError(String msg) {
        return new VerifyCodeSendResult(true, null, msg);
    }


    public Boolean getInputError() {
        return isInputError;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getMsg() {
        return msg;
    }
}
