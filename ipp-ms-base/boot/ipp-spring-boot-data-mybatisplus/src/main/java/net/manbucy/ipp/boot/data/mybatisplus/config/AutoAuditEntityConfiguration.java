package net.manbucy.ipp.boot.data.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.manbucy.ipp.boot.data.mybatisplus.handler.AuditEntityMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ManBu
 */
@Configuration
public class AutoAuditEntityConfiguration {

    @Bean
    public MetaObjectHandler auditEntityMetaObjectHandler() {
        return new AuditEntityMetaObjectHandler();
    }
}
