package com.ob.common.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/2 11:56
 * @Description:
 */
@NoRepositoryBean
@MappedSuperclass
@EntityListeners(BaseDomainListener.class)
public abstract class BaseDomain<I extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 36)
    private I id;

    /**
     * 创建时间
     */
    @JsonProperty("create_at")
    @CreatedDate
    @Column(updatable = false, nullable = false, columnDefinition = "timestamp not null")
    @Field("create_time")
    private Long createTime;

    /**
     * 创建人
     */
    @JsonProperty("create_by")
    @CreatedBy
    @Column(updatable = false)
    @Field("create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @JsonProperty("last_modified_at")
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp not null")
    @Field("last_modified_time")
    private Long lastModifiedTime;

    /**
     * 修改人
     */
    @JsonProperty("last_modified_by")
    @LastModifiedBy
    @Field("last_modified_by")
    private String lastModifiedBy;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
