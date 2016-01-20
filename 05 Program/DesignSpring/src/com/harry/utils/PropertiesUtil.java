package com.harry.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
    private static Map<String, String> properties = new HashMap<String, String>();

    static {
        initProperties();
    }

    private static void initProperties() {
        Properties pps = new Properties();

        try {
            pps.load(PropertiesUtil.class.getResourceAsStream("/com/harry/DataBase.properties"));
            Enumeration<?> enum1 = pps.propertyNames();
            while (enum1.hasMoreElements()) {
                String strKey = (String) enum1.nextElement();
                String strValue = pps.getProperty(strKey);
                properties.put(strKey, strValue);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        return properties.get(key);
    }
}
