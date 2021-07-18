package net.manbucy.ipp.cover.auth.controller.user.vo.reg;

import lombok.Data;

/**
 * @author ManBu
 */
@Data
public class VerifyCodeSendResult {
    private String code;
    private String msg;
    private long lockTime;
}
