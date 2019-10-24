package com.ob.modelexample.business.service;

import com.ob.common.base.service.CustomService;
import com.ob.modelexample.business.domain.Student;
import com.ob.modelexample.business.dto.StudentDto;
import com.ob.modelexample.business.repository.StudentRepository;
import com.ob.modelexample.business.utils.AsyncUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author: oubin
 * @date: 2019/3/28 14:14
 * @Description:
 */
@Service
@Slf4j
public class StudentService extends CustomService<Student, String> {

    private final StudentRepository studentRepository;

    @Autowired
    private AsyncUtils asyncUtils;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

    @Transactional(rollbackFor = Exception.class)
//    @Cacheable(value = "student", key = "#id")
    public Student get(String id) {
        asyncUtils.asyncFunc();
        log.info("调用线程继续执行");
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

    public void updateWithVersion(String id) {
        Student student = super.strictFind(id);
        student.setCountStudent(student.getCountStudent() + 1);
        studentRepository.save(student);
    }

    /**
     * 编程式事务管理测试
     * @param id
     */
    public void deleteById(String id) {
        transactionTemplate.execute(status -> {
            try {
                Student student = new Student();
                student.setName("编程式事务管理");
                student.setVersion(1);
                studentRepository.save(student);
                //执行该delete后手动删除数据库的数据，将不会添加新的学生
                studentRepository.deleteById(id);
            } catch (Exception e) {
                status.setRollbackOnly();
            }
            return null;
        });
    }
}
