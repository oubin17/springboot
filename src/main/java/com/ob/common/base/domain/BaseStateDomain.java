package com.ob.common.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:12
 * @Description:
 */
@MappedSuperclass
public abstract class BaseStateDomain<I extends Serializable> extends BaseDomain<I> {

    /**
     * 用于记录状态
     */
    @JsonProperty("state")
    @Field("state")
    @Column
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
