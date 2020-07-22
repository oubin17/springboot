package com.ob.other.test;

/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        println("user.abc");

    }

    private static void println(String property) {
        System.out.println(System.getProperty(property));
    }
}
