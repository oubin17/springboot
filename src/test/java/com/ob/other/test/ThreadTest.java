package com.ob.other.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: oubin
 * @Date: 2019/5/14 09:38
 * @Description:
 */
public class ThreadTest {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        //开启多线程
        ExecutorService exs = Executors.newFixedThreadPool(10);
        try {
            //结果集
            List<Integer> list = new ArrayList<Integer>();
            List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
            //1.高速提交10个任务，每个任务返回一个Future入list
            for(int i=0;i<10;i++){
                futureList.add(exs.submit(new CallableTask(i+1)));
            }

            Long getResultStart = System.currentTimeMillis();
            System.out.println("结果归集开始时间="+new Date());
            //2.结果归集，遍历futureList,高速轮询（模拟实现了并发）获取future状态成功完成后获取结果，退出当前循环
            for (Future<Integer> future : futureList) {
                //CPU高速轮询：每个future都并发轮循，判断完成状态然后获取结果，这一行，是本实现方案的精髓所在。即有10个future在高速轮询，完成一个future的获取结果，就关闭一个轮询
                while (true) {
                    //获取future成功完成状态，如果想要限制每个任务的超时时间，取消本行的状态判断+future.get(1000*1, TimeUnit.MILLISECONDS)+catch超时异常使用即可。
                    if (future.isDone()&& !future.isCancelled()) {
                        Integer i = future.get();//获取结果
                        System.out.println("任务i="+i+"获取完成!"+new Date());
                        list.add(i);
                        break;//当前future获取结果完毕，跳出while
                    } else {
                        //每次轮询休息1毫秒（CPU纳秒级），避免CPU高速轮循耗空CPU---》新手别忘记这个
                        Thread.sleep(1);
                    }
                }
            }

            System.out.println("list="+list);

            System.out.println("总耗时="+(System.currentTimeMillis()-start)+",取结果归集耗时="+(System.currentTimeMillis()-getResultStart));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exs.shutdown();
        }
    }

    static class CallableTask implements Callable<Integer> {
        Integer i;

        public CallableTask(Integer i) {
            super();
            this.i=i;
        }

        @Override
        public Integer call() throws Exception {
            if(i==1){
                Thread.sleep(3000);//任务1耗时3秒
            }else if(i==5){
                Thread.sleep(5000);//任务5耗时5秒
            }else{
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程："+Thread.currentThread().getName()+"任务i="+i+",完成！");
            return i;
        }
    }
}
