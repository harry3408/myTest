package com.harry.designpattrn.adapter;

public class ObjectAdapter implements Target {
    Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        adaptee.NBRequest();
    }
}
