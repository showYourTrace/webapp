package com.showyourtrace;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class Util {

    public static final SimpleDateFormat shortDate = new SimpleDateFormat("dd.MM.yy");

    public  static String safeString(Object object) {
        String result = null;

        if(object == null) {
            return "";
        }

        if(object instanceof String) {
            result = (String) object;
            if(result.trim().equalsIgnoreCase("null")) {
                result = "";
            }
        }
        else if(object.equals(JSONObject.NULL)) {
            return "";
        }
        else {
            result = object.toString();
        }

        return result;
    }

    public  static String safeNotNullString(Object object) {
        String result = safeString(object);
        return result == null ? "" : result;
    }
}
