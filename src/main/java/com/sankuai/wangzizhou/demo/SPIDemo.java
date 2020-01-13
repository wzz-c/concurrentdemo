package com.sankuai.wangzizhou.demo;

import java.util.ServiceLoader;
/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/18
 * Time: 下午3:54
 */
public class SPIDemo {
    public static void test() {
        ServiceLoader<DemoService> demoServices = ServiceLoader.load(DemoService.class);
        for(DemoService demoService : demoServices) {
            demoService.hello();
        }
    }
}
