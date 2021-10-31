package net.manbucy.ipp.cover.auth.config;

import cn.hutool.extra.mail.MailAccount;
import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.cover.auth.config.properties.MailServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ManBu
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MailServerProperties.class)
public class MailAccountConfiguration {
    private final MailServerProperties mailServerProperties;

    @Bean
    public MailAccount mailAccount() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(mailServerProperties.getHost())
                .setPort(mailServerProperties.getPort())
                .setAuth(mailServerProperties.isAuth())
                .setFrom(mailServerProperties.getFrom())
                .setUser(mailServerProperties.getUser())
                .setPass(mailServerProperties.getPass());
        return mailAccount;
    }
}
