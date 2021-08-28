package net.manbucy.ipp.cloud.document.provider;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cloud.document.endpoint.DocumentProviderInfoEndpoint;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author ManBu
 * @date 2021/8/8 22:19
 */
@Slf4j
public class IppSwaggerResourceProvider implements SwaggerResourcesProvider {

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    private final String providerConfigEndPointUrl;

    public IppSwaggerResourceProvider(Environment environment, DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        // http://host:port/actuator/doc-info
        providerConfigEndPointUrl = "http://%s:%s" + environment.getProperty("management.endpoints.web.base-path", "/actuator")
                + "/" + DocumentProviderInfoEndpoint.ENDPOINT_ID;

        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = getSwaggerResourceListFromService();
        Collections.sort(resources);
        return resources;
    }

    private List<SwaggerResource> getSwaggerResourceListFromService() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> serviceList = discoveryClient.getServices();
        log.info("serviceList: {}", serviceList);
        for (String serviceId : serviceList) {
            Optional<ServiceInstance> serviceInstanceOpt = discoveryClient.getInstances(serviceId).stream().findFirst();
            if (serviceInstanceOpt.isPresent()) {
                ServiceInstance serviceInstance = serviceInstanceOpt.get();
                String url = String.format(providerConfigEndPointUrl, serviceInstance.getHost(), serviceInstance.getPort());
                try {
                    String resourceJson = restTemplate.getForEntity(url, String.class).getBody();
                    if (StrUtil.isNotBlank(resourceJson)) {
                        List<SwaggerResource> swaggerResources = JSONUtil.toList(resourceJson, SwaggerResource.class);
                        resources.addAll(swaggerResources);
                    }
                } catch (Exception e) {
                    log.info("get doc info from url:[{}] fail", url);
                }
            }
        }
        return resources;
    }
}
