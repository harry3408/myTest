package com.harry.annotation.service.impl;

import org.springframework.stereotype.Component;

import com.harry.annotation.service.IHelloService;

@Component("helloServiceImplTwo")
public class HelloServiceImplTwo implements IHelloService {
    @Override
    public void sayHello() {
        System.out.println("Two say hello without params....");
    }

    @Override
    public void sayHello(String msgOne) {
        System.out.println(String.format("Two say hello without params(%s)....", msgOne));
    }

    @Override
    public void sayHello(String msgOne, String msgTwo) {
        System.out.println(String.format("Two say hello without params(%s, %s)....", msgOne, msgTwo));
    }

}
