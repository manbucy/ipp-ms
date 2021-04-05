package net.manbucy.ipp.cover.auth.controller.user.ao;

import lombok.Data;

/**
 * @author ManBu
 */
@Data
public class RegInfo {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String verifyCode;
}
