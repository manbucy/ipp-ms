package net.manbucy.ipp.cloud.document.annotation;

import net.manbucy.ipp.cloud.document.config.DocumentProviderConfiguration;
import net.manbucy.ipp.cloud.document.config.properties.DocumentProviderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ManBu
 * @date 2021/8/9 20:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(DocumentProviderProperties.class)
@Import({DocumentProviderConfiguration.class})
public @interface EnableDocumentProvider {
}
