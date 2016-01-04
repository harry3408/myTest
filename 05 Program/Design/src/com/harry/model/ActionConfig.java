package com.harry.model;

import java.util.ArrayList;
import java.util.List;

public class ActionConfig {
    private String className;
    private String methodName;
    private String[] httpMethod;
    private List<ResultConfig> resultConfigs = new ArrayList<ResultConfig>();

    public ActionConfig(String className, String methodName, String[] httpMethod) {
        this.className = className;
        this.methodName = methodName;
        this.httpMethod = httpMethod;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getHttpMethod() {
        return httpMethod;
    }

    public List<ResultConfig> getResultConfigs() {
        return resultConfigs;
    }

    public void setResultConfigs(List<ResultConfig> resultConfigs) {
        this.resultConfigs = resultConfigs;
    }
}
