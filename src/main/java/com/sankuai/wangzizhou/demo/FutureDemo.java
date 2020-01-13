package com.sankuai.wangzizhou.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/11/13
 * Time: 12:01 下午
 */
public class FutureDemo {
    private static final ThreadPoolExecutor e = new ThreadPoolExecutor(
            128,128,0L, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2048),
            new ThreadPoolExecutor.AbortPolicy()
    );
    private static int s()  {
        try {
            TimeUnit.SECONDS.sleep(1);
            ThreadLocalRandom tr = ThreadLocalRandom.current();
            if(tr.nextInt() % 7 == 0)
                throw new RuntimeException("simulating failed!");
            return 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static int giveMinusWhenEx(Throwable e) {
        return -1;
    }
    public static void main(String[] args) {
        Map<String, CompletableFuture<Integer>> resC = new HashMap<>();
        for (int i = 0; i < 1048; i++) {
            String name = String.valueOf(i);
            CompletableFuture<Integer> c;
            try {
                 c = CompletableFuture.supplyAsync(FutureDemo::s, e).exceptionally(
                        FutureDemo::giveMinusWhenEx);
            } catch (RejectedExecutionException e) {
                c = CompletableFuture.completedFuture(0);
            }
            resC.put(name, c);
        }
        resC.forEach((k,v) -> System.out.println(k + "  " + v.join()));
        e.shutdown();
    }
}
