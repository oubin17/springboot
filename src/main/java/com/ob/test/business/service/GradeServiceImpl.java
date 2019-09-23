package com.ob.test.business.service;

import com.ob.test.business.dto.GradeSaveDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/4/26 10:01
 * @Description:
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(GradeSaveDto gradeSaveDto) {
        System.out.println("保存年级");
    }

}
