package com.sankuai.wangzizhou.patterns.strategy;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2020/1/13
 * Time: 7:33 下午
 */
public class Demo {
    public static void main(String[] args) {
        Duck chin = new ChineseDuck();
        chin.setFlyBehavior(FlyBehavior.getModernWay());
        chin.display();
        chin.performFly();
    }
}
