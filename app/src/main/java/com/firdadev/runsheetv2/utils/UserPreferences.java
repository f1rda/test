package com.firdadev.runsheetv2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {private static SharedPreferences preferences;

    public static boolean isLogin(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("login", false);
    }

    public static void hasLogin(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean("login", true).apply();
    }

    public static void logout(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().apply();
    }

    public static String getUserId(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("user_id", "");
    }

    public static void setUserId(Context context, String userId) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("user_id", userId).apply();
    }
}
