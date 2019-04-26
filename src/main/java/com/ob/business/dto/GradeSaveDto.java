package com.ob.business.dto;

import lombok.Data;


/**
 * @Author: oubin
 * @Date: 2019/4/26 10:04
 * @Description:
 */
@Data
public class GradeSaveDto {

    /**
     * 年级
     */
    private int grade;

    /**
     * 年级名称
     */
    private String name;
}
