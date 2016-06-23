package com.harry.common;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig {
    private String className;
    private String methodName;
    private String[] httpMethod;
    private Map<String, ResultConfig> resultConfigs = new HashMap<String, ResultConfig>();

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

    public Map<String, ResultConfig> getResultConfigs() {
        return resultConfigs;
    }

    public void addResultConfig(String resultName, ResultConfig resultConfig) {
        this.resultConfigs.put(resultName, resultConfig);
    }

    public ResultConfig getResultConfig(String resultName) {
        return resultConfigs.get(resultName);
    }
}
