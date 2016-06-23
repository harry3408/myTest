package com.harry.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.harry.annotation.service.IHelloService;

@Repository("userServices")
public class UserServices {

    @Autowired(required = true)
    private @Qualifier("helloServiceImplOne")
    HelloServiceImplOne helloService;

    public HelloServiceImplOne getHelloService() {
        return helloService;
    }

}
