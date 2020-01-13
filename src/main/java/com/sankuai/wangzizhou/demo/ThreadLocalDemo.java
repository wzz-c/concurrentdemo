package com.sankuai.wangzizhou.demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/31
 * Time: 上午10:57
 */
public class ThreadLocalDemo {
    private static class C {
        int i;
        String s;
    }
    private static void playThreadLocal(C myC) {
        ThreadLocal<C> tc = new ThreadLocal<>();
        tc.set(myC);
        System.out.println(tc.get());
    }


    public static void main(String[] args) throws InterruptedException {
        C myC = new C();
        Runnable r = () -> {
            playThreadLocal(myC);
            System.gc();
            var t = Thread.currentThread();
            try {
                Field map = Thread.class.getDeclaredField("threadLocals");
                map.setAccessible(true);
                var realMap = map.get(t);
                Field entry = realMap.getClass().getDeclaredField("table");
                entry.setAccessible(true);
                var en = entry.get(realMap);
                System.out.println();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            System.out.println();

        };
        Thread t = new Thread(r);
        Thread t2 = new Thread(r);
        t.start();
        t2.start();
        t.join();
        t2.join();

        C tempC = new C();
        ReferenceQueue<C> qc = new ReferenceQueue<>();
        WeakReference<C> wr = new WeakReference<>(tempC,qc);
        tempC = null;
        System.gc();
        System.out.println();

    }
}
