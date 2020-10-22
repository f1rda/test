package com.firdadev.runsheetv2.utils;

import android.util.Base64;

public class Utils {
    public static String base64Encode(String username, String apiKey, String user, String password) {
        String auth = username + ":" +apiKey + ":" +user + ":" + password;
        return Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
    }
}
