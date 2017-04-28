/*
    Ryan Shee (rshee)
    Homework 2
*/
package com.dealfaro.luca.backandforthstudio;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luca on 18/1/2016.
 */
public class AppInfo {

    private static AppInfo instance = null;
    private static final String COLOR_NAME = "color";
    private static final String COLOR_NAME2 = "color2";
    private static final String COLOR_NAME3 = "color3";

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    public String sharedString;

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            instance.sharedString = settings.getString(COLOR_NAME, null);
        }
        return instance;
    }

    public void setColor(String c) {
        instance.sharedString = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME, c);
        editor.commit();
    }

    public void setColor2(String c) {
        instance.sharedString = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS2, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME2, c);
        editor.commit();
    }

    public void setColor3(String c) {
        instance.sharedString = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS3, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME3, c);
        editor.commit();
    }

}
