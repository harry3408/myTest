package com.harry.annotation.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import com.harry.annotation.service.IHelloService;

@Component("helloServiceImplOne")
public class HelloServiceImplOne  {

//    @Override
//    public void sayHello() {
//        System.out.println("say hello without params....");
//    }
//
//    @Override
//    public void sayHello(String msgOne) {
//        System.out.println(String.format("say hello without params(%s)....", msgOne));
//    }
//
//    @Override
//    public void sayHello(String msgOne, String msgTwo) {
//        System.out.println(String.format("say hello without params(%s, %s)....", msgOne, msgTwo));
//    }

    protected void sayHello(String msgOne, String msgTwo, String msgThree) {
        System.out.println(String.format("say hello without params(%s)....", msgOne));
    }
}
