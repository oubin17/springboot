package com.ob.business.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/3/28 14:12
 * @Description:
 */
@Data
@Entity
@Table(name = "t_student")
@GenericGenerator(name = "id", strategy = "uuid2")
public class Student implements Serializable {

    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 36)
    private String id;

    @Column(name = "name")
    private String name;
}
