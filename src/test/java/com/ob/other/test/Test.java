package com.ob.other.test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static final String abc = "a";

    public static void main(String[] args) {

        println("user.abc");

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("a", 1);
        linkedHashMap.put("b", 2);
        linkedHashMap.put("c", 3);
        linkedHashMap.get("b");
        Set<Map.Entry<String, Integer>> entries = linkedHashMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            System.out.println(next.getKey() + next.getValue());
        }

        try {

        } finally {

        }

    }

    private static void println(String property) {
        System.out.println(System.getProperty(property));
    }
}
