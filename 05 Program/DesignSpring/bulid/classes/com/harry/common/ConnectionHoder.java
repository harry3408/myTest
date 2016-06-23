package com.harry.common;

import java.sql.Connection;

public class ConnectionHoder {

    private Connection conn;
    private boolean isStartTranction = false;
    
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public boolean isStartTranction() {
        return isStartTranction;
    }
    public void setStartTranction(boolean isStartTranction) {
        this.isStartTranction = isStartTranction;
    }
}
