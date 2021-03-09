package net.manbucy.ipp.cover.auth.controller.user;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.cover.auth.pojo.vo.RegInfoCheckResult;
import net.manbucy.ipp.cover.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {
    final UserService userService;

    @GetMapping("/check/username")
    public R<RegInfoCheckResult> checkUsername(@NotEmpty @RequestParam("username") String username) {
        return R.ok(userService.checkUsername(username));
    }

    @GetMapping("/check/phone")
    public R<RegInfoCheckResult> checkPhone(@NotEmpty @RequestParam("phone") String phone) {
        return R.ok(userService.checkUserPhone(phone));
    }

    @GetMapping("/check/email")
    public R<RegInfoCheckResult> checkEmail(@NotEmpty @RequestParam("email") String email) {
        return R.ok(userService.checkUserEmail(email));
    }

    @GetMapping("/code/phone")
    public R<String> sendRegVerifyCodeToPhone(@NotEmpty @RequestParam("phone") String phone) {

        return R.ok();
    }


    @GetMapping("/code/email")
    public R<String> sendRegVerifyCodeToEmail(@NotEmpty @RequestParam("email") String email) {

        return R.ok();
    }
}
