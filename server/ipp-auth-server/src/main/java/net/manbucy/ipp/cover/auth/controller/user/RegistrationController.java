package net.manbucy.ipp.cover.auth.controller.user;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.BaseApiCode;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.cover.auth.controller.user.ao.RegInfo;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationError;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;
import net.manbucy.ipp.cover.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {
    final UserService userService;



    @GetMapping("/check/username")
    public R<String> checkUsername(@RequestBody RegInfo regInfo) {
        RegistrationResult usernameCheckResult = userService.checkUsername(regInfo.getUsername());
        return parseCommonRegResult(usernameCheckResult);
    }

    @GetMapping("/check/phone")
    public R<String> checkPhone(@RequestBody RegInfo regInfo) {
        RegistrationResult phoneCheckResult = userService.checkUserPhone(regInfo.getPhone());
        return parseCommonRegResult(phoneCheckResult);
    }

    @GetMapping("/check/email")
    public R<String> checkEmail(@RequestBody RegInfo regInfo) {
        RegistrationResult emailCheckResult = userService.checkUserEmail(regInfo.getEmail());
        return parseCommonRegResult(emailCheckResult);
    }

    @PostMapping("/code/phone")
    public R<String> sendRegVerifyCodeToPhone(@RequestBody RegInfo regInfo) {
        RegistrationResult codeSendResult = userService.sendRegVerifyCodeToPhone(regInfo.getPhone());
        return parseCommonRegResult(codeSendResult);
    }

    @PostMapping("/code/email")
    public R<String> sendRegVerifyCodeToEmail(@RequestBody RegInfo regInfo) {
        RegistrationResult codeSendResult = userService.sendRegVerifyCodeToEmail(regInfo.getEmail());
        if (!codeSendResult.isSuccess()) {
            if (codeSendResult.getOneError().isPresent()) {
                RegistrationError error = codeSendResult.getOneError().get();
                String data = error.getType() == RegistrationError.TYPE_VERIFY_CODE && error.isOperateError() ?
                        String.valueOf(error.getOperateLockTime()) : null;
                return R.failed(error.errorCode.code, error.getMsg(), data);
            }
            return R.failed(BaseApiCode.FAIL.code, "未知错误", null);
        }

        return R.ok();
    }

    @PostMapping("/phone")
    public R<String> registerByPhone(@RequestBody RegInfo regInfo) {
        RegistrationResult result = userService.registrationByPhone(regInfo);
        return parseCommonRegResult(result);
    }

    @PostMapping("/email")
    public R<String> registerByEmail(@RequestBody RegInfo regInfo) {
        RegistrationResult result = userService.registrationByEmail(regInfo);
        return parseCommonRegResult(result);
    }

    private R<String> parseCommonRegResult(RegistrationResult result) {
        if (!result.isSuccess()) {
            if (result.getOneError().isPresent()) {
                RegistrationError error = result.getOneError().get();
                return R.failed(error.errorCode.code, error.getMsg(), null);
            }
            return R.failed(BaseApiCode.FAIL.code, "未知错误", null);
        }
        return R.ok();
    }
}
