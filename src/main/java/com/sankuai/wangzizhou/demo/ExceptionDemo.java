package com.sankuai.wangzizhou.demo;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/11/25
 * Time: 1:42 下午
 */
public class ExceptionDemo {
    public static void main(String[] args) {
        try {
            throw new OutOfMemoryError();
        } catch(Error e) {
            System.out.println(e.getMessage());
        }
        System.out.println("over");
    }
}
