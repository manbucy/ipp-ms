package net.manbucy.ipp.boot.core.exception;

import lombok.Getter;
import net.manbucy.ipp.boot.core.constants.BaseStatus;

/**
 * @author ManBu
 */
@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 6739734403797590421L;
    private final String code;
    private String msg;
    private Object data;

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    private BizException(String code, String msg, Object data) {
        this(code, msg);
        this.data = data;
    }

    public BizException(String msg) {
        this(BaseStatus.FAIL, msg);
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = BaseStatus.FAIL;
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = BaseStatus.FAIL;
        this.msg = msg;
    }
}
