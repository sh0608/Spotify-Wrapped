package com.example.spotifywrapped.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    public static String getToken(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }
}
