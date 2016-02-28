package com.harry.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
	private static Map<String, String> properties = new HashMap<String, String>();

	static {
		Properties pps = null;
		InputStream in = null;
		try {
			in = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
			pps = new Properties();
			pps.load(in);
			Enumeration<?> enum1 = pps.propertyNames();
			while (enum1.hasMoreElements()) {
				String strKey = (String) enum1.nextElement();
				String strValue = pps.getProperty(strKey);
				properties.put(strKey, strValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperties(String key) {
		return properties.get(key);
	}

	public static String getStaticUrl() {
		return getProperties("static_url") + ":8081";
	}
}
