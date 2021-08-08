package net.manbucy.ipp.boot.web.handler;

import cn.hutool.core.util.StrUtil;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.constants.BaseStatus;
import net.manbucy.ipp.boot.core.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ManBu
 * @date 2021/8/8 18:26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({BizException.class})
    public R<Object> handleBizException(BizException e) {
        String code = e.getCode();
        if (StrUtil.isBlank(code)) {
            code = BaseStatus.FAIL;
        }
        return R.failed(code, e.getMessage(), e.getData());
    }
}
