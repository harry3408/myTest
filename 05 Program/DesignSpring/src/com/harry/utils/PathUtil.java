package com.harry.utils;

import com.harry.common.AppContext;
import com.harry.common.Const;

public class PathUtil {

	public static String getFullPath(String path) {
		String fullPath = "";
		fullPath = AppContext.getAppContext().getAppContextPath();
		if (!Const.URL_PREFIX.isEmpty()) {
			fullPath += Const.URL_PREFIX;
		} else {
			fullPath += "/";
		}
		return fullPath + path;
	}
}
