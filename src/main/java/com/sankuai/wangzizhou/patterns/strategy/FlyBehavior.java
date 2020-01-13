package com.sankuai.wangzizhou.patterns.strategy;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2020/1/13
 * Time: 7:30 下午
 */
public interface FlyBehavior {
    public void fly();
    FlyBehavior flyWithRings = ()-> System.out.println("Fly with real wings!");
    FlyBehavior flyWithRocket = () -> System.out.println("Fly with rocket!");
    static FlyBehavior getRealWay() {
        return flyWithRings;
    }
    static FlyBehavior getModernWay() {
        return flyWithRocket;
    }
}
