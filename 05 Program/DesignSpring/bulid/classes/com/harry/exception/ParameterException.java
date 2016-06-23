package com.harry.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {
    private static final long serialVersionUID = 7716092007014536457L;

    private Map<String, String> errorMessages = new HashMap<String, String>();

    public void putErrorMsg(String key, String value) {
        this.errorMessages.put(key, value);
    }

    public Map<String, String> getErrorMsg() {
        return this.errorMessages;
    }

    public boolean hasErrorMsg() {
        return !errorMessages.isEmpty();
    }
}
