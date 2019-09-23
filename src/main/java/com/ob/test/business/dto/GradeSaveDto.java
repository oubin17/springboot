package com.ob.test.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id_gene")
    private String idGene;
}
