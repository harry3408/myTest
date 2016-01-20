package com.harry.exception;

public class DBException extends RuntimeException {
	private static final long serialVersionUID = -5172969711299341029L;

    private String key;
    private String errorMsg;

    public DBException(String key, String errorMsg) {
        this.key = key;
        this.errorMsg = errorMsg;
    }

    public DBException(String errorMsg, Throwable e) {
        super(errorMsg, e);
    }
}
