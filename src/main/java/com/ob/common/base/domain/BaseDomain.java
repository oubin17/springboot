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
    @Field("create_at")
    private Long createAt;

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
    @Field("last_modified_at")
    private Long lastModifiedAt;

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

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
