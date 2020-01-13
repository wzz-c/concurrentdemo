package com.sankuai.wangzizhou.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/18
 * Time: 下午1:38
 */
class TestDo {
    long value = 0;
}
public class UnsafeDemo {
    public static void test() throws NoSuchFieldException, IllegalAccessException {
//        Object o = Unsafe.getUnsafe();
        Class<String> c = String.class;
        System.out.println(ClassLoader.getSystemClassLoader() == UnsafeDemo.class.getClassLoader());
        System.out.println(int.class.getClassLoader());
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
        System.out.println(unsafe.objectFieldOffset(TestDo.class.getDeclaredField("value")));
        TestDo testDo = new TestDo();
        for(int i = 0;i <= 10000;i++) {
            Long bareMemoryValue = unsafe.getLongVolatile(testDo,i);
            //System.out.println(Long.toHexString(bareMemoryValue));
        }
        System.out.println(testDo.value);

    }
}
