package com.ob.test.business.domain;

import com.ob.common.base.domain.BaseDomain;
import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @Author: oubin
 * @Date: 2019/4/24 08:14
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_grade")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class Grade extends BaseDomain<String> {

    @Column(name = "grade")
    private int grade;

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private int version;
}
