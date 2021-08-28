package net.manbucy.ipp.cover.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.boot.core.utils.SecurityUtil;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final StringRedisTemplate redisTemplate;

    private final RabbitTemplate rabbitTemplate;

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
        return R.ok(SecurityUtil.getUser().orElse(null));
    }


    @PostMapping("/mq")
    public R<String> mq(String msg) {
        rabbitTemplate.convertAndSend("myQueue", msg);
        return R.ok();
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", true, false, false);
    }

    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        log.info("receive message: {}", in);
    }

    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange("testExchange");
    }

}