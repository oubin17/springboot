package com.ob.common.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/2 11:56
 * @Description:
 */
@MappedSuperclass
@EntityListeners(BaseDomainListener.class)
public abstract class BaseDomain<I extends Serializable> {

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
    private Long createdAt;

    /**
     * 创建人
     */
    @JsonProperty("create_by")
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * 修改时间
     */
    @JsonProperty("last_modified_at")
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp not null")
    private Long lastModifiedAt;

    /**
     * 修改人
     */
    @JsonProperty("last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
