package net.manbucy.ipp.cloud.document.annotation;

import net.manbucy.ipp.cloud.document.config.DocumentServerConfiguration;
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
@Import({DocumentServerConfiguration.class})
public @interface EnableDocumentServer {
}
