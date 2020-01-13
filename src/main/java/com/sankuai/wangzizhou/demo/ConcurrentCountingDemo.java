package com.sankuai.wangzizhou.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/24
 * Time: 下午2:09
 */
public class ConcurrentCountingDemo {
    private static final SimulatingAlarmMessageSender simulatingAlarmMessageSender = SimulatingAlarmMessageSender.getInstance();
    public static void sendMessage() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        String ruleId = String.valueOf(threadLocalRandom.nextInt(32));
        boolean refused = threadLocalRandom.nextBoolean();
        simulatingAlarmMessageSender.sendAlarmMessage(ruleId, refused,"test.test.test");
    }
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(64);
        scheduledExecutorService.scheduleAtFixedRate(
                ConcurrentCountingDemo::sendMessage,
                1000,
                1,
                TimeUnit.MILLISECONDS
        );
        TimeUnit.SECONDS.sleep(60);
    }
}
