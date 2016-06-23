package com.harry.designpattrn.wrapper;

public class Change implements TheGreatestSage {

    TheGreatestSage sage;
    
    public Change(TheGreatestSage sage) {
        this.sage = sage;
    }

    @Override
    public void move() {
        sage.move();
    }

}
