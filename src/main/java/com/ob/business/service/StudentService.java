package com.ob.business.service;

import com.ob.base.service.CustomService;
import com.ob.business.domain.Student;
import com.ob.business.dto.StudentDto;
import com.ob.business.repository.StudentRepository;
import com.ob.common.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: oubin
 * @date: 2019/3/28 14:14
 * @Description:
 */
@Service
public class StudentService extends CustomService<Student, String> {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @CachePut(value = "student", key = "#student.id")
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @CacheEvict(value = "student")
    public void remove(String id) {
        studentRepository.delete(id);
    }

    @Cacheable(value = "student", key = "#id")
    public Student get(String id) {
        return super.strictFind(id);
    }

    public Student updateName(String id, StudentDto dto) {
        Student student = super.strictFind(id);
        student.setName(dto.getName());
        return studentRepository.save(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String id) {
        studentRepository.setCount(id);
    }
}
