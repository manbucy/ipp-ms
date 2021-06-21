package net.manbucy.ipp.boot.core.security;

import java.io.Serializable;

/**
 * @author ManBu
 */
public abstract class AbstractUserId implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PREFIX = "{";
    public static final String USER_ID_TYPE_NONE = "none";
    public static final String SUFFIX = "}";

    protected final String userIdType;
    protected final Object id;

    public AbstractUserId(String userIdType, Object id) {
        this.userIdType = userIdType;
        this.id = id;
    }

    public String getFormattingId() {
        return PREFIX + this.userIdType + SUFFIX + id;
    }

    public String getUserIdType() {
        return userIdType;
    }

    public static String getFormattingNoneId() {
        return AbstractUserId.PREFIX + AbstractUserId.USER_ID_TYPE_NONE + AbstractUserId.SUFFIX;
    }

}
