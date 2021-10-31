package net.manbucy.ipp.cover.auth.controller.user.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 验证码接收者
 *
 * @author ManBu
 * @since 2021/10/30 14:51
 */
@Data
@Schema(name = "验证码接收者", title = "接收验证码的手机或电子邮箱")
public class VerifyCodeReceiver {
    /**
     * 接收者类型
     */
    private UserThirdAuthType type;

    /**
     * 接收者
     */
    @Schema(name = "接收者")
    @NotBlank(message = "接收者不能为空")
    private String receiver;
}
