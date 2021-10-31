package net.manbucy.ipp.cover.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 验证码校验 配置类
 * @author ManBu
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("verify-code.limit")
public class VerifyCodeLimitProperties {
    /**
     * 重复发送限制时间
     * 在 repeatLimitTime 时间内，不允许重复发送验证码
     */
    private int repeatLimitTime = 60;

    /**
     * 验证码有效时间
     * 在 codeExpireTime 时间内，验证码有效
     */
    private int codeExpireTime = 300;

    /**
     * 最大限制时间
     * 在 maxLimitTime 时间内，发送的验证码最多发送 maxLimitCount 次
     */
    private int maxLimitTime = 3600;

    /**
     * 最大发送次数限制
     * 在 maxLimitTime 时间内，发送的验证码最多发送 maxLimitCount 次
     */
    private int maxLimitCount = 10;
}
