package com.ob.modelexample.business.repository.impl;

import com.ob.modelexample.business.domain.Teacher;
import com.ob.modelexample.business.repository.TeacherRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


/**
 * @Author: oubin
 * @Date: 2019/4/11 11:15
 * @Description:
 */
public class TeacherRepositoryImpl implements TeacherRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addCount(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.inc("count_teacher", 1);
        mongoTemplate.findAndModify(query, update, Teacher.class, "t_teacher");
    }
}
