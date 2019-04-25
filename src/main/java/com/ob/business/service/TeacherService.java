package com.ob.business.service;

import com.ob.business.domain.Teacher;
import com.ob.business.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/4/10 11:41
 * @Description:
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String id) {
//        Teacher teacher = teacherRepository.findOne(id);
//        teacher.setCountTeacher(teacher.getCountTeacher() + 1);
//        teacherRepository.save(teacher);
        teacherRepository.addCount(id);
    }

}
