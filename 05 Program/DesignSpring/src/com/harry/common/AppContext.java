package com.harry.common;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private static ThreadLocal<AppContext> appcontextMap = new ThreadLocal<AppContext>();

    private Map<String, Object> objects = new HashMap<String, Object>();
    private String appContextPath;

    public String getAppContextPath() {
        return appContextPath;
    }

    public void setAppContextPath(String appContextPath) {
        this.appContextPath = appContextPath;
    }

    public Object getObject(String key) {
        return objects.get(key);
    }

    public void setObjects(String key, Object value) {
        objects.put(key, value);
    }

    private AppContext() {
    }

    public static AppContext getAppContext() {
        AppContext appContext = appcontextMap.get();
        if (appContext == null) {
            appContext = new AppContext();
            appcontextMap.set(appContext);
        }
        return appContext;
    }

    public static void removeAppContext() {
        appcontextMap.remove();
    }
}
