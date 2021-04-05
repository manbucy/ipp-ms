package net.manbucy.ipp.cover.auth.pojo.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author ManBu
 */
@Getter
public class RegistrationResult {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 错误结果集
     */
    private List<RegistrationError> errorList;

    public RegistrationResult() {
    }

    public RegistrationResult(boolean success) {
        this.success = success;
        this.errorList = new ArrayList<>();
    }

    public static RegistrationResult ok() {
        return new RegistrationResult(true);
    }

    public static RegistrationResult fail() {
        return new RegistrationResult(false);
    }

    public static RegistrationResult fail(RegistrationError error) {
        RegistrationResult result = new RegistrationResult(false);
        result.addError(error);
        return result;
    }

    public RegistrationResult addError(RegistrationError error) {
        this.errorList.add(error);
        return this;
    }

    /**
     * 获取错误列表中的第一个错误信息
     *
     * @return 错误信息
     */
    public Optional<RegistrationError> getOneError() {
        if (success || errorList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(errorList.get(0));
        }
    }
}


