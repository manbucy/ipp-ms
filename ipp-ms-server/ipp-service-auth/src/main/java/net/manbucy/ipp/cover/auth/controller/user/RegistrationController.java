package net.manbucy.ipp.cover.auth.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.cover.auth.controller.user.ao.VerifyCodeReceiver;
import net.manbucy.ipp.cover.auth.service.user.RegistrationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ManBu
 * @since 2021/9/25 15:40
 */
@Slf4j
@RestController
@RequestMapping("/user/reg")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/sendVerifyCode")
    public R<Date> sendVerifyCode(@RequestBody @Validated VerifyCodeReceiver verifyCodeReceiver) {
        log.info("verifyCodeReceiver: {}", verifyCodeReceiver);
        return R.ok();
    }
}
