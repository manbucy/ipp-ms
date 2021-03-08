package net.manbucy.ipp.boot.data.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.manbucy.ipp.boot.core.security.AbstractUserId;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.boot.core.utils.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author ManBu
 */
public class AuditEntityMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);

        this.strictInsertFill(metaObject, "createUser",
                () -> getFormattingUserId(SecurityUtil.getUser().orElse(null)), String.class);
        this.strictInsertFill(metaObject, "updateUser",
                () -> getFormattingUserId(SecurityUtil.getUser().orElse(null)), String.class);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);

        this.strictUpdateFill(metaObject, "updateUser",
                () -> getFormattingUserId(SecurityUtil.getUser().orElse(null)), String.class);
    }

    /**
     * 获取格式化的用户ID
     *
     * @param userDetail 用户详细信息
     * @return 格式化的用户ID
     */
    private String getFormattingUserId(UserDetail userDetail) {
        if (userDetail != null) {
            return userDetail.getUserId().getFormattingId();
        }
        return AbstractUserId.getFormattingNoneId();
    }
}
