package com.ob.business.repository;

import com.ob.business.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: oubin
 * @date: 2019/3/28 14:13
 * @Description:
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findById(String id);
}
