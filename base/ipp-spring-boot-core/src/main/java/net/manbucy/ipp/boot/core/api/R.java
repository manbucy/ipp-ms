package net.manbucy.ipp.boot.core.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ManBu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return result(BaseApiCode.SUCCESS.code, BaseApiCode.SUCCESS.msg, null);
    }

    public static <T> R<T> ok(T data) {
        return result(BaseApiCode.SUCCESS.code, BaseApiCode.SUCCESS.msg, data);
    }

    public static <T> R<T> ok(String message, T data) {
        return result(BaseApiCode.SUCCESS.code, message, data);
    }

    public static <T> R<T> failed(String code) {
        return result(null, code, null);
    }

    public static <T> R<T> failed(String code, T data) {
        return result(code, null, data);
    }

    public static <T> R<T> failed(String code, String message) {
        return result(code, message, null);
    }

    public static <T> R<T> failed(String code, String message, T data) {
        return result(code, message, data);
    }


    private static <T> R<T> result(String code, String message, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(message);
        r.setData(data);
        return r;
    }

}
