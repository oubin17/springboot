package com.ob.other.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2020/10/28 11:23
 * @Description:
 */
public class Guava {

    public static void main(String[] args) {
        test3();

    }

    /**
     * 判空
     */
    public static void test1() {
        String param = "未读代码";
        String name = Preconditions.checkNotNull(param);

    }

    /**
     * 预期值判断
     */
    public static void test2() {
        String param = "abc";
        String wdbyte = "abcd";
        Preconditions.checkArgument(wdbyte.equals(param), "[%s] NOT EQUAL", param);
    }

    /**
     * 是否越界
     */
    public static void test3() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d");
        int index = Preconditions.checkElementIndex(5, list.size());
    }

    /**
     * 创建不可变集合：不可变集合中元素不可为null
     */
    public static void test4() {

        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
        immutableSet.forEach(System.out::println);

        ImmutableSet<String> immutableSet2 = ImmutableSet.<String>builder()
                .add("hello")
                .add("未读代码")
                .build();
        immutableSet2.forEach(System.out::println);

        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add("b");
        ImmutableSet<String> immutableSet3 = ImmutableSet.copyOf(arrayList);
        immutableSet3.forEach(System.out::println);

    }

    /**
     * 集合交集并集差集
     */
    public static void test5() {
        Set<String> newHashSet1 = Sets.newHashSet("a", "a", "b", "c");
        Set<String> newHashSet2 = Sets.newHashSet("b", "b", "c", "d");

        // 交集：[b, c]
        Sets.SetView<String> intersectionSet = Sets.intersection(newHashSet1, newHashSet2);
        System.out.println(intersectionSet);

        // 并集：[a, b, c, d]
        Sets.SetView<String> unionSet = Sets.union(newHashSet1, newHashSet2);
        System.out.println(unionSet);

        // newHashSet1 中存在，newHashSet2 中不存在 // [a]
        Sets.SetView<String> setView = Sets.difference(newHashSet1, newHashSet2);
        System.out.println(setView);
    }

    /**
     * 计数的集合
     * result:
     * a:2
     * b:1
     * c:2
     * d:1
     */
    public static void test6() {
        ArrayList<String> arrayList = Lists.newArrayList("a", "b", "c", "d", "a", "c");
        HashMultiset<String> multiset = HashMultiset.create(arrayList);
        multiset.elementSet().forEach(s -> System.out.println(s + ":" + multiset.count(s)));

        // [加菲, 汤姆]
        HashMultimap<String, String> multimap = HashMultimap.create();
        multimap.put("狗", "大黄");
        multimap.put("狗", "旺财");
        multimap.put("猫", "加菲");
        multimap.put("猫", "汤姆");
        System.out.println(multimap.get("猫"));
    }

    /**
     * 字符拼接：useFornull(String):为空值设置自定义显示文本
     */
    public static void test7() {
        // a,b,c
        ArrayList<String> list = Lists.newArrayList("a", "b", "c", null);
        String join = Joiner.on(",").skipNulls().join(list);
        System.out.println(join);

        // 旺财,汤姆,杰瑞,空值
        String join1 = Joiner.on(",").useForNull("空值").join("旺财", "汤姆", "杰瑞", null);
        System.out.println(join1);

    }

    /**
     * 字符串切割
     */
    public static void test8() {
        //原生：最后一个空直接被丢弃
        /**
         *
         * a
         *
         * b
         * ------
         */
        String str = ",a,,b,";
        String[] splitArr = str.split(",");
        Arrays.stream(splitArr).forEach(System.out::println);
        System.out.println("------");

        //Guava
        /**
         * a
         * b
         */
        String strGuava = ",a ,,b ,";
        Iterable<String> split = Splitter.on(",")
                .omitEmptyStrings() // 忽略空值
                .trimResults() // 过滤结果中的空白
                .split(strGuava);
        split.forEach(System.out::println);
    }

    /**
     * 缓存
     */
    public static void test9() {
        CacheLoader cacheLoader = new CacheLoader<String, String>() {
            // 如果找不到元素，会调用这里
            @Override
            public String load(String s) {
                return null;
            }
        };
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000) // 容量
                .expireAfterWrite(3, TimeUnit.SECONDS) // 过期时间
//                .removalListener(new MyRemovalListener()) // 失效监听器，如果元素失效，会调用该方法
                .build(cacheLoader); //

    }
}


