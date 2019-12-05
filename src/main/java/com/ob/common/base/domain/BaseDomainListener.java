package com.ob.common.base.domain;

import com.ob.common.context.SessionContext;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @Author: oubin
 * @Date: 2019/5/31 09:12
 * @Description:
 */
@Component
public class BaseDomainListener {

    /**
     * 保存前操作
     *
     * @param baseDomain
     */
    @PrePersist
    public void prePersist(BaseDomain baseDomain) {
        baseDomain.setCreateAt(System.currentTimeMillis());
        if (null != SessionContext.currentUserId()) {
            baseDomain.setCreateBy(SessionContext.currentUserId());
        }
        baseDomain.setLastModifiedAt(System.currentTimeMillis());
        if (null != SessionContext.currentUserId()) {
            baseDomain.setLastModifiedBy(SessionContext.currentUserId());
        }

    }

    /**
     * 更新前操作
     *
     * @param baseDomain
     */
    @PreUpdate
    public void preUpdate(BaseDomain baseDomain) {
        baseDomain.setLastModifiedAt(System.currentTimeMillis());
        if (null != SessionContext.currentUserId()) {
            baseDomain.setLastModifiedBy(SessionContext.currentUserId());
        }
    }
}
