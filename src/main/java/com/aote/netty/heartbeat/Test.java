package com.aote.netty.heartbeat;

/**
 * @author: Weicf
 * @date: 2020-05-18 17:46
 * @description:
 */
public class Test {

    public static void main(String[] args) throws Exception {

        System.out.println(System.nanoTime()); //纳秒  10亿分之1
        Thread.sleep(1000);
        System.out.println(System.nanoTime());

    }

}
