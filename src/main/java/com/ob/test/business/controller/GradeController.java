package com.ob.test.business.controller;

import com.ob.test.business.dto.GradeSaveDto;
import com.ob.test.business.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: oubin
 * @Date: 2019/4/25 10:47
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;
//
//    @Autowired
//    private GradeServiceImpl gradeServiceImpl;

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody GradeSaveDto saveDto) {
        gradeService.add(saveDto);
    }

}
