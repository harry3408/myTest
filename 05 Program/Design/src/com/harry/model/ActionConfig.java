package com.harry.model;

public class ActionConfig {
    private String className;
    private String methodName;

    public ActionConfig(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}
