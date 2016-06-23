package com.harry.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class UserService {

    private String message;
    @Resource(name = "helloServiceTwo")
    private IHelloService helloService;

    @PostConstruct
    public void init() {
        System.out.println("init method .... ");
    }

    @PreDestroy
    public void destory() {
        System.out.println("destory method ...");
    }

    public void initBySchema() {
        System.out.println("schema init method ...");
    }

    public void destoryBySchema() {
        System.out.println("destory schema method ...");
    }

    public IHelloService getHelloService() {
        return helloService;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sayHello() {
        System.out.println("UserService say hello...");
    }
}
