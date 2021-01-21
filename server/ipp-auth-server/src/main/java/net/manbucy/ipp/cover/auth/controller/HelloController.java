package net.manbucy.ipp.cover.auth.controller;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping
    public R<String> hello() {
        return R.ok("hello");
    }

    @GetMapping("/echo/app-name")
    public R<String> echoAppName() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("ipp-product-server");
        String path = String.format("http://%s:%s/hello/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        return R.ok(restTemplate.getForObject(path, String.class));
    }
}
