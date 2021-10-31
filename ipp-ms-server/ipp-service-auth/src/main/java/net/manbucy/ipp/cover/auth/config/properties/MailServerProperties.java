package net.manbucy.ipp.cover.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 邮件服务 配置类
 * @author ManBu
 * @since 2021/10/30 13:29
 */
@Data
@RefreshScope
@ConfigurationProperties("mail-server")
public class MailServerProperties {
    /**
     * 邮件服务地址
     */
    private String host;
    /**
     * 邮件服务端口
     */
    private int port;
    /**
     * 邮件服务-是否认证
     */
    private boolean auth;
    /**
     * 邮件服务-发送者
     */
    private String from;
    /**
     * 邮件服务-认证-用户名 邮件发送者
     */
    private String user;
    /**
     * 邮件服务-认证-密码
     */
    private String pass;
}
