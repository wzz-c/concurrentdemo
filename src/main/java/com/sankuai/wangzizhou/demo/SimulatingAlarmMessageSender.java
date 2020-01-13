package com.sankuai.wangzizhou.demo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/24
 * Time: 下午2:10
 */
public class SimulatingAlarmMessageSender {

    private static class Holder {
        private static final SimulatingAlarmMessageSender instance = new SimulatingAlarmMessageSender();
    }
    public static SimulatingAlarmMessageSender getInstance() {return Holder.instance;}
    private ConcurrentMap<String, LongAdder> warnCounterMap = new ConcurrentHashMap<>();
    private ConcurrentMap<String, LongAdder> refuseCounterMap = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String> ruleDetailsMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService processWorkerExecutorService;
    private SimulatingAlarmMessageSender() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        processWorkerExecutorService = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(8), new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        scheduledExecutorService.scheduleAtFixedRate(
                () -> processWorkerExecutorService.submit(this::processFlowControlMessagePeriodically),
                1,
                5,
                TimeUnit.SECONDS);
    }
    public void sendAlarmMessage(String ruleId, boolean refused, String ruleDetails) {
        recordFlowControlAlarmMessage(ruleId, refused, ruleDetails);
    }
    private void recordFlowControlAlarmMessage(String ruleId, boolean refused, String ruleDetails) {
        if(refused) {
            refuseCounterMap.computeIfAbsent(ruleId, s->new LongAdder()).increment();
        } else {
            warnCounterMap.computeIfAbsent(ruleId, s->new LongAdder()).increment();
        }
        ruleDetailsMap.putIfAbsent(ruleId, ruleDetails);
    }
    private void processFlowControlMessagePeriodically() {
            Set<String> ruleSet = new HashSet<>(ruleDetailsMap.keySet());
            List<String> messageList = new ArrayList<>();
            for(String rule: ruleSet) {
                LongAdder longAdderWarn = warnCounterMap.get(rule);
                long warnCnt = longAdderWarn != null ? longAdderWarn.sumThenReset() : 0;
                LongAdder longAdderRefuse = refuseCounterMap.get(rule);
                long refuseCnt = longAdderRefuse != null ? longAdderRefuse.sumThenReset() : 0;
                if(warnCnt == 0 && refuseCnt == 0) {
                    continue;
                }
                messageList.add("fake message" + rule + " " + refuseCnt + " " + warnCnt);
            }
            for(String message: messageList) {
                uploadToMbop(message);
            }
            System.out.println("period work finished at " + LocalDateTime.now());
    }
    private void uploadToMbop(String message) {
        try {
            System.out.println("telling MBOP server  " + message);
        } catch(Exception e) {
            System.err.println("corresponding warn log: upload failed! " + e.getMessage());
        }
    }
}
