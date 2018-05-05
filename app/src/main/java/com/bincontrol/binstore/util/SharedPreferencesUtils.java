package com.bincontrol.binstore.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bincontrol.binstore.common.AppConstant;

public class SharedPreferencesUtils {

    public static final String PERFERENCE_NAME = AppConstant.SHARE_PREFERENCE_PATH;

    /**
     * put String to SharedPreference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    /**
     * get String from SharedPreferences
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }


    /**
     * put int to SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    /**
     * get int from SharedPreferences
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }


    /**
     * put long to SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        return editor.commit();
    }


    /**
     * get long from SharedPreferences
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }


    /**
     * put float to SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }


    /**
     * get float from SharedPreferences
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }


    /**
     * put boolean to SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    /**
     * get boolean from SharedPreferences
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }


    /**
     * if SharedPreferences have the key
     * @param context
     * @param key
     * @return
     */
    public static boolean isKeyExist(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PERFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

}
