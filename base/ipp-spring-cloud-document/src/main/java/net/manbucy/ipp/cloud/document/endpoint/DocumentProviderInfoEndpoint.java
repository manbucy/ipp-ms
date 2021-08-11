package net.manbucy.ipp.cloud.document.endpoint;

import cn.hutool.core.util.StrUtil;
import net.manbucy.ipp.cloud.document.config.properties.DocumentProviderProperties;
import net.manbucy.ipp.cloud.document.constants.SwaggerDocumentVersion;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.of;
import static springfox.documentation.schema.ClassSupport.classByName;

/**
 * @author ManBu
 * @date 2021/8/9 21:48
 */
@Endpoint(id = DocumentProviderInfoEndpoint.ENDPOINT_ID)
public class DocumentProviderInfoEndpoint implements ApplicationContextAware {
    public static final String ENDPOINT_ID = "doc-info";

    private final String swagger1Url;
    private final String swagger2Url;
    private final String oas3Url;

    private boolean oas3Available;
    private boolean swagger1Available;
    private boolean swagger2Available;

    private final DocumentProviderProperties documentProviderProperties;

    public DocumentProviderInfoEndpoint(DocumentProviderProperties documentProviderProperties, Environment environment) {
        swagger1Url = environment.getProperty("springfox.documentation.swagger.v1.path", "/api-docs");
        swagger2Url = fixup(environment.getProperty("springfox.documentation.swagger.v2.path", "/v2/api-docs"));
        oas3Url = fixup(environment.getProperty("springfox.documentation.open-api.v3.path", "/v3/api-docs"));

        this.documentProviderProperties = documentProviderProperties;
    }

    @ReadOperation
    public List<SwaggerResource> swaggerResourceList() {
        List<SwaggerResource> resources = new ArrayList<>();
        if (swagger1Available && SwaggerDocumentVersion.V1 == documentProviderProperties.getVersion()) {
            SwaggerResource swaggerResource = resource(documentProviderProperties, swagger1Url);
            swaggerResource.setSwaggerVersion("1.2");
            resources.add(swaggerResource);
        }

        if (swagger2Available && SwaggerDocumentVersion.V2 == documentProviderProperties.getVersion()) {
            SwaggerResource swaggerResource = resource(documentProviderProperties, swagger2Url);
            swaggerResource.setSwaggerVersion("2.0");
            resources.add(swaggerResource);
        }

        if (oas3Available && SwaggerDocumentVersion.V3 == documentProviderProperties.getVersion()) {
            SwaggerResource swaggerResource = resource(documentProviderProperties, oas3Url);
            swaggerResource.setSwaggerVersion("3.0.3");
            resources.add(swaggerResource);
        }
        return resources;
    }

    private SwaggerResource resource(DocumentProviderProperties providerInfo, String baseUrl) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(providerInfo.getGroupName());
        swaggerResource.setUrl(swaggerLocation(baseUrl, providerInfo));
        return swaggerResource;
    }

    private String swaggerLocation(String swaggerUrl, DocumentProviderProperties providerInfo) {
        String base = "/" + providerInfo.getContext() + of(swaggerUrl).get();
        if (Docket.DEFAULT_GROUP_NAME.equals(providerInfo.getGroupName())) {
            return base;
        }
        return base + "?group=" + providerInfo.getGroupName();
    }

    private String fixup(String path) {
        if (StrUtil.isEmpty(path) || "/".equals(path) || "//".equals(path)) {
            return "/";
        }
        return StringUtils.trimTrailingCharacter(path.replace("//", "/"), '/');
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ClassLoader classLoader = applicationContext.getClassLoader();
        swagger1Available
                = classByName("springfox.documentation.swagger1.web.Swagger1Controller", classLoader).isPresent();

        swagger2Available = (classByName("springfox.documentation.swagger2.web.Swagger2ControllerWebFlux", classLoader).isPresent()
                || classByName("springfox.documentation.swagger2.web.Swagger2ControllerWebMvc", classLoader).isPresent());

        oas3Available = (classByName("springfox.documentation.oas.web.OpenApiControllerWebFlux", classLoader).isPresent()
                || classByName("springfox.documentation.oas.web.OpenApiControllerWebMvc", classLoader).isPresent());
    }
}
