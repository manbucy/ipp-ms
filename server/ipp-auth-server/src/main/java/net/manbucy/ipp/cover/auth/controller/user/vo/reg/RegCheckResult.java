package net.manbucy.ipp.cover.auth.controller.user.vo.reg;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ManBu
 */
@Data
@AllArgsConstructor
public class RegCheckResult {
    private String type;
    private String code;
    private String msg;
}
