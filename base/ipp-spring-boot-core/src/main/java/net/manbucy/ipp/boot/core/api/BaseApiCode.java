package net.manbucy.ipp.boot.core.api;

/**
 * @author ManBu
 */
public enum BaseApiCode {
    /**
     * 成功，无错误
     */
    SUCCESS("00000", "成功"),
    FAIL("FFFFF", "失败");

    public String code;
    public String msg;

    BaseApiCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
