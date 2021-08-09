package net.manbucy.ipp.cloud.document.config.properties;

import lombok.Builder;
import lombok.Data;
import net.manbucy.ipp.cloud.document.constants.SwaggerDocumentVersion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author ManBu
 * @date 2021/8/9 20:59
 */
@Data
@RefreshScope
@ConfigurationProperties("springfox.documentation")
public class DocumentProviderProperties {
    private String basePackage;
    private String groupName;
    private String context;
    private SwaggerDocumentVersion version;
}
