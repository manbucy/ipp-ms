package net.manbucy.ipp.cover.auth.controller;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.exception.BizException;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.boot.core.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final LoadBalancerClient loadBalancerClient;

    private final StringRedisTemplate redisTemplate;

    private final RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/redis/{key}")
    public R<String> getValue(@PathVariable String key) {
        return R.ok(redisTemplate.opsForValue().get(key));
    }

    @PutMapping("/redis/{key}/{value}")
    public R<String> setValue(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        return R.ok();
    }

    @GetMapping("/user")
    public R<UserDetail> getUser() {
        return R.ok(SecurityUtil.getUser().orElse(null));
    }

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

    @GetMapping("/exception/{code}")
    public R<String> testException(@PathVariable String code) {
        if ("404".equals(code)) {
            throw new BizException("404", "自定义异常抛出");
        }
        return R.ok(code);
    }
}
