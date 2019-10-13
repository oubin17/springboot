package com.ob.modelexample.business.repository;

import com.ob.modelexample.business.domain.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: oubin
 * @Date: 2019/4/10 11:39
 * @Description:
 */
public interface TeacherRepository extends TeacherRepositoryCustom, MongoRepository<Teacher, String> {

}
