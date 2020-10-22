package com.firdadev.runsheetv2.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.firdadev.runsheetv2.activity.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public static  final String KEY_NAME ="username";
    public static final String BRANCH="branch";

    public void logoutUser() {

       editor.clear();
       editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(BRANCH, pref.getString(BRANCH, null));
        return user;
    }
}
