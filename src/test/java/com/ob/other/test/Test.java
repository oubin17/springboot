package com.ob.other.test;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        System.out.println(Lists.newArrayList(list.subList(0,0)));
        System.out.println("bbc5ea34-d852-11e9-9315-ecf4bbc4ecdc".hashCode());

    }
}
