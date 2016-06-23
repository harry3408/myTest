package com.harry.service;

public class HelloServiceImpl implements IHelloService{

    @Override
    public void sayHello() {
        System.out.println("say hello without the param");
    }

    @Override
    public void sayHello(String msg) {
        System.out.println("say hello with the param --> " + msg);
    }

    @Override
    public void sayHello(String msg, String msgTwo) {
        System.out.println("say hello with the param --> " + msg + ", " + msgTwo);
    }
}
