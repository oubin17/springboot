package com.ob.business.controller;

import com.ob.business.domain.Teacher;
import com.ob.business.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: oubin
 * @Date: 2019/4/10 11:36
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam(name = "name") String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacherService.save(teacher);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String id) {
        teacherService.update(id);
    }

}
