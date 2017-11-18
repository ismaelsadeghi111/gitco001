package com.sefryek.common;

import java.util.ResourceBundle;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Feb 28, 2012
 * Time: 2:02:59 PM
 */
public class MessageUtil {

    public static String get(String key) {
        ResourceBundle bundleEN = ResourceBundle.getBundle("MessageResources", new Locale("en", "EN"));
        return bundleEN.getString(key);

    }

    public static String get(String key, String[] params) {
        String message = get(key);
        for (int i = 0; i < params.length; i++) {
            int index;
            while ((index = message.indexOf("{" + i + "}")) != -1) {
                message = message.substring(0, index) + params[i] + message.substring(index + 3);
            }
        }
        return message;
    }

    public static String get(String key, String param) {
        return get(key, new String[]{param});
    }

    public static String get(String key, String param1, String param2) {
        return get(key, new String[]{param1, param2});
    }

    public static String get(String key, String param1, String param2, String param3) {
        return get(key, new String[]{param1, param2, param3});
    }

    public static String get(String key, String param1, String param2, String param3, String param4) {
        return get(key, new String[]{param1, param2, param3, param4});
    }


    public static String get(String key, Locale loc) {
        ResourceBundle bundleEN = ResourceBundle.getBundle("MessageResources", loc);
        return bundleEN.getString(key);

    }

}
