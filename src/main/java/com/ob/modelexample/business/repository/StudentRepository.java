package com.ob.modelexample.business.repository;

import com.ob.common.base.repository.CustomRepository;
import com.ob.modelexample.business.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: oubin
 * @date: 2019/3/28 14:13
 * @Description:
 */
@Repository
public interface StudentRepository extends CustomRepository<Student, String>, JpaRepository<Student, String> {

    @Modifying
    @Query(value = "update Student set countStudent = countStudent + 1 where id=:id")
    int setCount(@Param("id") String id);

    @Modifying
    @Query(value = "update t_student t set t.count_student = t.count_student + 1 where id=:id", nativeQuery = true)
    int countAdd(@Param("id") String id);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);
}
