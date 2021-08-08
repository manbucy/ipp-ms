package net.manbucy.ipp.cover.gateway.swagger;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.of;
import static springfox.documentation.schema.ClassSupport.classByName;

/**
 * @author ManBu
 * @date 2021/8/8 22:19
 */
@Component
@Primary
public class IppSwaggerResourceProvider implements SwaggerResourcesProvider, ApplicationContextAware {
    private final String swagger1Url;
    private final String swagger2Url;
    private final String oas3Url;

    private boolean oas3Available;
    private boolean swagger1Available;
    private boolean swagger2Available;

    private final List<SwaggerServerConfig> swaggerServerConfigs;

    @Autowired
    public IppSwaggerResourceProvider(Environment environment) {
        swagger1Url = environment.getProperty("springfox.documentation.swagger.v1.path", "/api-docs");
        swagger2Url = fixup(environment.getProperty("springfox.documentation.swagger.v2.path", "/v2/api-docs"));
        oas3Url = fixup(environment.getProperty("springfox.documentation.open-api.v3.path", "/v3/api-docs"));
        swaggerServerConfigs = new ArrayList<>();
        swaggerServerConfigs.add(new SwaggerServerConfig().setServerContext("auth").setGroupName("ipp-auth-api").setVersion("v3"));
    }

    private String fixup(String path) {
        if (StrUtil.isEmpty(path) || "/".equals(path) || "//".equals(path)) {
            return "/";
        }
        return StringUtils.trimTrailingCharacter(path.replace("//", "/"), '/');
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        for (SwaggerServerConfig swaggerServerConfig : swaggerServerConfigs) {
            if (swagger1Available && StrUtil.equalsIgnoreCase(SwaggerVersion.v1.toString(), swaggerServerConfig.version)) {
                SwaggerResource swaggerResource = resource(swaggerServerConfig, swagger1Url);
                swaggerResource.setSwaggerVersion("1.2");
                resources.add(swaggerResource);
            }

            if (swagger2Available && StrUtil.equalsIgnoreCase(SwaggerVersion.v2.toString(), swaggerServerConfig.version)) {
                SwaggerResource swaggerResource = resource(swaggerServerConfig, swagger2Url);
                swaggerResource.setSwaggerVersion("2.0");
                resources.add(swaggerResource);
            }

            if (oas3Available && StrUtil.equalsIgnoreCase(SwaggerVersion.v3.toString(), swaggerServerConfig.version)) {
                SwaggerResource swaggerResource = resource(swaggerServerConfig, oas3Url);
                swaggerResource.setSwaggerVersion("3.0.3");
                resources.add(swaggerResource);
            }
        }
        Collections.sort(resources);
        return resources;
    }

    private SwaggerResource resource(SwaggerServerConfig swaggerServerConfig, String baseUrl) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(swaggerServerConfig.groupName);
        swaggerResource.setUrl(swaggerLocation(baseUrl, swaggerServerConfig));
        return swaggerResource;
    }

    private String swaggerLocation(String swaggerUrl, SwaggerServerConfig swaggerServerConfig) {
        String base = "/" + swaggerServerConfig.serverContext + "/" + of(swaggerUrl).get();
        if (Docket.DEFAULT_GROUP_NAME.equals(swaggerServerConfig.groupName)) {
            return base;
        }
        return base + "?group=" + swaggerServerConfig.groupName;
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

    public static class SwaggerServerConfig {
        protected String serverContext;
        protected String groupName;
        protected String version;

        public String getServerContext() {
            return serverContext;
        }

        public SwaggerServerConfig setServerContext(String serverContext) {
            this.serverContext = serverContext;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public SwaggerServerConfig setGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public String getVersion() {
            return version;
        }

        public SwaggerServerConfig setVersion(String version) {
            this.version = version;
            return this;
        }
    }

    public static enum SwaggerVersion {
        v1, v2, v3;
    }
}
