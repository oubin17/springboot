package com.ob.business.repository.impl;

import com.ob.business.repository.StudentRepositoryCustom;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
 * @Author: oubin
 * @Date: 2019/5/30 13:47
 * @Description:
 */
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public void addCount(String id) {
        String sql = "select * from t_student";
        jdbcTemplate.query(sql, new ColumnMapRowMapper());

    }
}
