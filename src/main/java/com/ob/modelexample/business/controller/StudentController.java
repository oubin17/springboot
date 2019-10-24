package com.ob.modelexample.business.controller;

import com.ob.modelexample.business.domain.Student;
import com.ob.modelexample.business.dto.StudentDto;
import com.ob.modelexample.business.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: oubin
 * @date: 2019/3/28 14:15
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam(name = "name") String name) {
        Student student = new Student();
        student.setName(name);
        studentService.save(student);

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student get(@PathVariable(name = "id") String id) {
        return studentService.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(name = "id") String id) {
        studentService.remove(id);
    }

    @RequestMapping(value = "/actions/update/{id}", method = RequestMethod.PUT)
    public void updateName(@PathVariable("id") String id,
                           @RequestBody StudentDto dto) {
        studentService.updateName(id, dto);
    }


    /**
     * 并发测试
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String id) {
        studentService.update(id);
    }

    @RequestMapping(value = "/version/{id}", method = RequestMethod.PUT)
    public void updateWithVersion(@PathVariable("id") String id) {
        studentService.updateWithVersion(id);
    }

    /**
     * 编程式事务管理测试
     * @param id
     */
    @RequestMapping(value = "/transactional/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        studentService.deleteById(id);
    }

}
