package com.harry.designpattrn.adapter;

public class Test {

    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();
        
        target = new ClassAdapter();
        target.request();
        
        target = new ObjectAdapter();
        target.request();
    }
}
