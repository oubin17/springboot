package com.ob.base.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/2 13:43
 * @Description:
 */
@MappedSuperclass
public class BaseTenantDomain<I extends Serializable> extends BaseDomain<I> {

    /**
     * 用于租户隔离
     */
    @Column(updatable = false)
    private Long tenant;

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }
}
