package com.sankuai.wangzizhou.demo;

import java.util.concurrent.*;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/28
 * Time: 上午10:38
 */
public class ConcurrentToolsDemo {
    public static void test() throws InterruptedException {
        ExecutorService service  = new ThreadPoolExecutor(
                8,
                32,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        Runnable r  = () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finish");
        };
        for (int i = 0; i < 100; i++) {
            service.submit(r);
        }
        service.shutdown();

    }
    public static void test2() {

    }
    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
