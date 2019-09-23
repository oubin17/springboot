package com.ob.test.business.domain;

import com.ob.common.base.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: oubin
 * @Date: 2019/4/10 11:37
 * @Description:
 */
@Document(collection = "t_teacher")
@Data
public class Teacher extends BaseDomain<String> {

    @Field("name")
    private String name;

    @Field("count_teacher")
    private int countTeacher;

}
