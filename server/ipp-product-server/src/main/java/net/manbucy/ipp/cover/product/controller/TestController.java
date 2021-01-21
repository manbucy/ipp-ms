package net.manbucy.ipp.cover.product.controller;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.boot.core.utils.SecurityUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final StringRedisTemplate redisTemplate;

    @GetMapping("/redis/{key}")
    @PreAuthorize("hasAuthority('api:find:all:users')")
    public R<String> getValue(@PathVariable String key) {
        return R.ok(redisTemplate.opsForValue().get(key));
    }

    @PutMapping("/redis/{key}/{value}")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public R<String> setValue(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        return R.ok();
    }

    @GetMapping("/user")
    public R<UserDetail> getUser() {
        return R.ok(SecurityUtil.getUser());
    }

}