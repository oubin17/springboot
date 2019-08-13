package com.ob.other.test;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(-1);
        LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth()), LocalTime.of(0,0,0));
        System.out.println(localDateTime1.toEpochSecond(ZoneOffset.of("+8")));

        List<String> abc = Lists.newArrayList("a", "b", "c", "d");
        System.out.println(abc.subList(2,3));

        int size = 20;

        System.out.println(size << 2);

    }
}
