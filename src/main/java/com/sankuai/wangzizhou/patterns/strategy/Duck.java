package com.sankuai.wangzizhou.patterns.strategy;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2020/1/13
 * Time: 7:30 下午
 */
public  abstract class Duck {
    public abstract void display();

    FlyBehavior flyBehavior;

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
    public void performFly() {
        flyBehavior.fly();
    }
}
