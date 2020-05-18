package com.aote.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * @author: Weicf
 * @date: 2020-05-18 17:21
 * @description: 查看可运行的工作线程数
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
    }

}
