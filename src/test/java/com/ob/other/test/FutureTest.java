package com.ob.other.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: oubin
 * @Date: 2020/8/5 14:02
 * @Description:
 */
public class FutureTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println((int)'a');

    }
}
