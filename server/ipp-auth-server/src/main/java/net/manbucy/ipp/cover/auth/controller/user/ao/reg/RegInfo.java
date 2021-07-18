package net.manbucy.ipp.cover.auth.controller.user.ao.reg;

import lombok.Data;

/**
 * 注册信息
 * @author ManBu
 */
@Data
public class RegInfo {
    private String username;
    private String password;
    private String authType;
    private String authValue;
    private String verifyCode;
}
