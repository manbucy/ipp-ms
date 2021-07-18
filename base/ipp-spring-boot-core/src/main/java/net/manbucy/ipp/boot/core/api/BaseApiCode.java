package net.manbucy.ipp.boot.core.api;

import net.manbucy.ipp.boot.core.constants.BaseStatus;

/**
 * @author ManBu
 */
public enum BaseApiCode {
    /**
     * 成功，无错误
     */
    SUCCESS(BaseStatus.SUCCESS, "成功"),
    FAIL(BaseStatus.FAIL, "失败");

    public String code;
    public String msg;

    BaseApiCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
