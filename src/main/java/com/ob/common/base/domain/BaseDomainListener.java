package com.ob.common.base.domain;

import com.ob.common.context.RequestContext;
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
        baseDomain.setCreateTime(System.currentTimeMillis());
        baseDomain.setCreateBy(RequestContext.currentUserId());
        baseDomain.setLastModifiedTime(System.currentTimeMillis());
        baseDomain.setLastModifiedBy(RequestContext.currentUserId());

    }

    /**
     * 更新前操作
     *
     * @param baseDomain
     */
    @PreUpdate
    public void preUpdate(BaseDomain baseDomain) {
        baseDomain.setLastModifiedTime(System.currentTimeMillis());
        baseDomain.setLastModifiedBy(RequestContext.currentUserId());
    }
}
