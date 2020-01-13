package com.sankuai.wangzizhou.demo;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/18
 * Time: 下午3:46
 */
public class DemoServiceImpl implements DemoService {
    public DemoServiceImpl(){

    }
    @Override
    public void hello() {
        System.out.println("hello from demo implement!");
    }
}
