package com.aote.netty.rpc.provide;

import com.aote.netty.rpc.publicinterface.HelloService;

/**
 * @author: Weicf
 * @date: 2020-05-20 21:17
 * @description:
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        //根据mes 返回不同的结果
        if(msg != null) {
//            return "你好客户端, 我已经收到你的消息 [" + msg + "] ";
            return "你好客户端, 我已经收到你的消息 [" + msg + "] 第" + (++count) + " 次";
        } else {
            return "你好客户端, 我已经收到你的消息 ";
        }
    }
}
