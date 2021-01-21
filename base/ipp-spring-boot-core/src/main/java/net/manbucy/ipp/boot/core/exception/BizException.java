package net.manbucy.ipp.boot.core.exception;

/**
 * @author ManBu
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 6739734403797590421L;
    private final int code;
    private String msg;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg) {
        super(msg);
        this.code = 1;
        this.msg = msg;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = 1;
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = 1;
        this.msg = msg;
    }


}
