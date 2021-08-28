package net.manbucy.ipp.cover.content.controller;

import net.manbucy.ipp.boot.core.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public R<String> hello() {
        return R.ok("hello");
    }
}
