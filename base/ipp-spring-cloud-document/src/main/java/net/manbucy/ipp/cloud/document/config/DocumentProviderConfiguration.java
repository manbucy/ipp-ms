package net.manbucy.ipp.cloud.document.config;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.cloud.document.config.properties.DocumentProviderProperties;
import net.manbucy.ipp.cloud.document.constants.SwaggerDocumentVersion;
import net.manbucy.ipp.cloud.document.endpoint.DocumentProviderInfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ManBu
 * @date 2021/8/9 20:56
 */
@Configuration
@RequiredArgsConstructor
public class DocumentProviderConfiguration {
    private final DocumentProviderProperties providerInfo;

    @Bean
    public Docket authApi() {
        return new Docket(SwaggerDocumentVersion.documentationType(providerInfo.getVersion()))
                .select()
                .apis(
                        StrUtil.isNotBlank(providerInfo.getBasePackage()) ?
                                RequestHandlerSelectors.basePackage(providerInfo.getBasePackage()) :
                                RequestHandlerSelectors.any()
                )
                .build()
                .groupName(providerInfo.getGroupName());
    }

    @Bean
    public DocumentProviderInfoEndpoint documentProviderInfoEndpoint() {
        return new DocumentProviderInfoEndpoint(providerInfo);
    }
}
