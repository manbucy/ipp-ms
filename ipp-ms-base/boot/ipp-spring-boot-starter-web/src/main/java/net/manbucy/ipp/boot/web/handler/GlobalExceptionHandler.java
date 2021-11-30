package net.manbucy.ipp.boot.web.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import net.manbucy.ipp.boot.core.api.R;
import net.manbucy.ipp.boot.core.constants.BaseStatus;
import net.manbucy.ipp.boot.core.exception.BizException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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

    @ResponseBody
    @ExceptionHandler({HttpMessageConversionException.class})
    public R<Void> handleHttpMessageConversionException(HttpMessageConversionException e) {
        return R.failed(BaseStatus.FAIL, "参数转换错误");
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<String> errorMsgList = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());

        return R.failed(BaseStatus.FAIL, CollectionUtil.join(errorMsgList, "|"));
    }
}