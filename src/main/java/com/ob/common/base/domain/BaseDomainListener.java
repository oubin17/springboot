package com.ob.common.base.domain;

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
        baseDomain.setCreatedAt(System.currentTimeMillis());
        baseDomain.setCreatedBy("oubin");
        baseDomain.setLastModifiedAt(System.currentTimeMillis());
        baseDomain.setLastModifiedBy("oubin");

    }

    /**
     * 更新前操作
     *
     * @param baseDomain
     */
    @PreUpdate
    public void preUpdate(BaseDomain baseDomain) {
        baseDomain.setLastModifiedAt(System.currentTimeMillis());
        baseDomain.setLastModifiedBy("oubin");
    }
}
