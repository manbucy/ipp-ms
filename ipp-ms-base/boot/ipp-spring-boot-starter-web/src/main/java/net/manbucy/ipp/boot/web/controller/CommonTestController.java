package net.manbucy.ipp.boot.web.controller;

import net.manbucy.ipp.boot.core.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ManBu
 * @since 2021/10/30 15:36
 */
@RestController
@RequestMapping("/test")
public class CommonTestController {

    @GetMapping("/ping")
    public R<String> ping() {
        return R.ok("pong");
    }
}
