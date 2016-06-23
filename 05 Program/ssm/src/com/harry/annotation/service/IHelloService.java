package com.harry.annotation.service;

public interface IHelloService {
    public abstract void sayHello();
    public abstract void sayHello(String msgOne);
    public abstract void sayHello(String msgOne, String msgTwo);
}