package com.harry.utils;

import com.harry.common.AppContext;

public class URLUtil {

    private static final String urlPrefix = "";

    public static String getFullPath(String path) {
        String fullPath = "";
        fullPath = AppContext.getAppContext().getAppContextPath() + "/";
        if (!urlPrefix.isEmpty()) {
            fullPath += urlPrefix + "/";
        }
        return fullPath + path;
    }
}
