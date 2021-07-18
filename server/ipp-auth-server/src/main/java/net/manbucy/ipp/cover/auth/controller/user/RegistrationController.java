package net.manbucy.ipp.cover.auth.controller.user;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.boot.core.api.Q;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.constants.BaseStatus;
import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegCheckItem;
import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegInfo;
import net.manbucy.ipp.cover.auth.controller.user.vo.reg.RegCheckResult;
import net.manbucy.ipp.cover.auth.controller.user.vo.reg.VerifyCodeSendResult;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationError;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;
import net.manbucy.ipp.cover.auth.service.UserRegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {
    final UserRegistrationService userRegistrationService;

    @PostMapping("/checkRegInfo")
    public R<RegCheckResult> checkRegInfo(@RequestBody Q<RegCheckItem> regCheckItemQ) {
        RegCheckResult regCheckResult = userRegistrationService.checkRegInfo(regCheckItemQ.getData());
        return R.<RegCheckResult>builder()
                .code(regCheckResult.getCode())
                .msg(regCheckResult.getMsg())
                .data(regCheckResult)
                .build();
    }

    @PostMapping("/checkRegInfos")
    public R<List<RegCheckResult>> checkRegInfos(@RequestBody Q<List<RegCheckItem>> regCheckItemListQ) {
        List<RegCheckResult> regCheckResultList = new ArrayList<>();
        for (RegCheckItem regCheckItem : regCheckItemListQ.getData()) {
            regCheckResultList.add(userRegistrationService.checkRegInfo(regCheckItem));
        }
        boolean checkPassed = regCheckResultList.stream().allMatch(regCheckResult -> BaseStatus.SUCCESS.equals(regCheckResult.getCode()));
        return R.<List<RegCheckResult>>builder()
                .code(checkPassed ? BaseStatus.SUCCESS : BaseStatus.FAIL)
                .msg(checkPassed ? "校验通过" : "校验不通过")
                .data(regCheckResultList)
                .build();
    }

    @PostMapping("/sendVerifyCode")
    public R<VerifyCodeSendResult> sendRegVerifyCodeToAuthNum(@RequestBody Q<RegInfo> regInfoQ){
        VerifyCodeSendResult verifyCodeSendResult = userRegistrationService.sendRegVerifyCodeToAuthNum(regInfoQ.getData());
        return R.<VerifyCodeSendResult>builder()
                .code(verifyCodeSendResult.getCode())
                .msg(verifyCodeSendResult.getMsg())
                .data(verifyCodeSendResult)
                .build();
    }

    @PostMapping
    public R<RegistrationResult> register(@RequestBody Q<RegInfo> regInfoQ) {
        RegistrationResult result = userRegistrationService.register(regInfoQ.getData());
        if (result.isFail()) {
            if (result.getOneError().isPresent()) {
                RegistrationError error = result.getOneError().get();
                return R.failed(error.errorCode.code, error.getMsg(), result);
            }
            return R.failed(BaseStatus.FAIL, "未知错误", result);
        }
        return R.ok(result);
    }
}
