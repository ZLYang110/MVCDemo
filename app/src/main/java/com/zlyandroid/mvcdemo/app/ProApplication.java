package com.zlyandroid.mvcdemo.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 应用, 主要用来做一下初始化的操作
 *
 * @author gc
 * @since 1.0
 */
public class ProApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * @return 全局的上下文
     */
    public static Context getContext() {
        return mContext;
    }





    public static boolean isAutoDarkMode() {
        SharedPreferences sp = mContext.getSharedPreferences(MY_SP_NAME, MODE_PRIVATE);
        return sp.getBoolean(AUTO_DARK_MODE_KEY, true);
    }
    public static int getCustomTheme() {
        SharedPreferences sp = mContext.getSharedPreferences(MY_SP_NAME, MODE_PRIVATE);
        return sp.getInt(THEME_KEY, 0);
    }

    public static void setCustomTheme(int theme) {
        SharedPreferences sp = mContext.getSharedPreferences(MY_SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(THEME_KEY, theme);
        editor.apply();
    }

    public static void setDarkModeTime(boolean isStart, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(MY_SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (isStart) {
            editor.putInt(START_DARK_TIME_KEY, value);
        } else {
            editor.putInt(END_DARK_TIME_KEY, value);
        }
        editor.apply();
    }


    public static int[] getDarkModeTime() {
        SharedPreferences sp = mContext.getSharedPreferences(MY_SP_NAME, MODE_PRIVATE);
        int[] ret = new int[2];
        ret[0] = sp.getInt(START_DARK_TIME_KEY, 21);
        ret[1] = sp.getInt(END_DARK_TIME_KEY, 6);
        return ret;
    }

    public static final String MY_SP_NAME = "ZLY";
    public static final String THEME_KEY = "theme";
    public static final String AUTO_DARK_MODE_KEY = "auto_dark_mode";
    public static final String START_DARK_TIME_KEY = "start_dart_time";
    public static final String END_DARK_TIME_KEY = "end_dark_time";
}
