package com.aote.netty.rpc.publicinterface;

/**
 * @author: Weicf
 * @date: 2020-05-20 21:01
 * @description: 这个是接口，是服务提供方和 服务消费方都需要
 */
public interface HelloService {

    String hello(String msg);

}
