package com.sankuai.wangzizhou.patterns.strategy;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2020/1/13
 * Time: 7:32 下午
 */
public class ChineseDuck extends Duck {
    @Override
    public void display() {
        System.out.println("This is a Chinese duck!");
    }
}
