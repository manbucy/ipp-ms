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
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return result(null, ApiCode.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return result(data, ApiCode.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String message) {
        return result(data, ApiCode.SUCCESS, message);
    }

    public static <T> R<T> failed() {
        return result(null, ApiCode.FAILED, null);
    }

    public static <T> R<T> failed(T data) {
        return result(data, ApiCode.FAILED, null);
    }

    public static <T> R<T> failed(String message) {
        return result(null, ApiCode.FAILED, message);
    }

    public static <T> R<T> failed(T data, String message) {
        return result(data, ApiCode.FAILED, message);
    }


    private static <T> R<T> result(T data, int code, String message) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(code);
        r.setMsg(message);
        return r;
    }


}
