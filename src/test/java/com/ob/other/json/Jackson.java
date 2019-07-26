package com.ob.other.json;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/7/26 15:43
 * @Description:
 */
@JsonIgnoreProperties({"a", "b"})
@Data
public class Jackson {

    private String a;

    private String b;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String c;

    @JsonIgnore
    private String d;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("page_size")
    private int pageNum;

    private List<String> names;

}
