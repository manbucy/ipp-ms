package net.manbucy.ipp.cover.auth.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ManBu
 */
@Configuration
public class MailAccountConfiguration {
    @Value("${mail-server.host}")
    private String host;

    @Value("${mail-server.port}")
    private int port;

    @Value("${mail-server.auth}")
    private boolean auth;

    @Value("${mail-server.from}")
    private String from;

    @Value("${mail-server.user}")
    private String user;

    @Value("${mail-server.pass}")
    private String pass;

    @Bean
    public MailAccount mailAccount() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(host)
                .setPort(port)
                .setAuth(auth)
                .setFrom(from)
                .setUser(user)
                .setPass(pass);
        return mailAccount;
    }
}
