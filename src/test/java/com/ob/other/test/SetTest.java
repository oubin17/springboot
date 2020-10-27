package com.ob.other.test;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @Author: oubin
 * @Date: 2020/10/14 14:33
 * @Description:
 */
public class SetTest {

    public static void main(String[] args) {
        Set<Integer> sets = Sets.newHashSet(1, 2, 3, 4, 5, 6);
        Set<Integer> sets2 = Sets.newHashSet(3, 4, 5, 6, 7, 8, 9);
        // 交集
        System.out.println("交集为：");
        Sets.SetView<Integer> intersection = Sets.intersection(sets, sets2);
        for (Integer temp : intersection) {
            System.out.println(temp);
        }
        // 差集
        System.out.println("差集为：");
        Sets.SetView<Integer> diff = Sets.difference(sets, sets2);
        for (Integer temp : diff) {
            System.out.println(temp);
        }
        // 并集
        System.out.println("并集为：");
        Sets.SetView<Integer> union = Sets.union(sets, sets2);
        for (Integer temp : union) {
            System.out.println(temp);
        }

    }
}
