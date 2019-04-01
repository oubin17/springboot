package com.ob.business.service;

import com.ob.business.domain.Student;
import com.ob.business.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: oubin
 * @date: 2019/3/28 14:14
 * @Description:
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void save(String name) {
        Student student = new Student();
        student.setName(name);
        studentRepository.save(student);
    }

    public Student get(String id) {
        return studentRepository.findById(id);
    }
}
