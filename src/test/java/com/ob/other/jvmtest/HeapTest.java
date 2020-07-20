package com.ob.other.jvmtest;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2020/7/20 14:06
 * @Description:
 */
@Data
public class HeapTest {

    public static void main(String[] args) {
        List<HeapClass> list = Lists.newArrayList();
        int i = 0;
        while (true && i < 100) {
            list.add(new HeapClass("a name"));
            i++;
        }
    }
}

class HeapClass {
    private String name;

    public HeapClass(String name) {
        this.name = name;
    }
}
