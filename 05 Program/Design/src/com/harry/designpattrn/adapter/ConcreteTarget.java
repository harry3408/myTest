package com.harry.designpattrn.adapter;

public class ConcreteTarget implements Target {

    @Override
    public void request() {
        System.out.println("This is nomal request method.");
    }

}
