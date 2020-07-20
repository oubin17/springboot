package com.ob.other.test;

/**
 * @Author: oubin
 * @Date: 2020/7/3 16:12
 * @Description:
 */
public class StaticClassMain {

    public static void main(String[] args) {
        StaticClassTest.StaticClass class1 = new StaticClassTest.StaticClass("a");
        System.out.println(class1.getStaticStr());
        StaticClassTest.StaticClass class2 = new StaticClassTest.StaticClass("b");
        System.out.println(class2.getStaticStr());
        System.out.println(class1.getStaticStr());
        String b = "x";
        for (String a = b;;) {
            System.out.println(a);
        }
    }
}
