package com.xha.mangotv.util;

import android.content.Context;
import android.content.SharedPreferences;

import static com.xha.mangotv.util.PreContact.*;


/**
 * Created by sunsiyuan on 16/3/9.
 */
public class PreUtil {

    private static PreUtil instance;
    private SharedPreferences reader;
    private SharedPreferences.Editor editor;
//    private Context context;

    /**
     * 单例
     *
     * @return
     */
    public static PreUtil getInstance() {
        if (instance == null) {
            instance = new PreUtil();
        }
//        if (instance.editor == null || instance.reader == null) {
//            instance.init(App.getInstance().getApplicationContext());
//        }
        return instance;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        instance.editor = context.getSharedPreferences(PreContact.APP_NAME, Context.MODE_PRIVATE).edit();
        instance.reader = context.getSharedPreferences(PreContact.APP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 设置string内容
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获得string数据
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return reader.getString(key, "");
    }

    /**
     * 设置boolean数据
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 设置int数据
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置long数据
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return reader.getLong(key, defaultValue);
    }

    /**
     * 获取int数据
     *
     * @param key
     * @return
     */
    public int getInt(String key, int defaultValue) {
        return reader.getInt(key, defaultValue);
    }

    /**
     * 获取boolean 数据
     *
     * @param key
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return reader.getBoolean(key, defaultValue);
    }


}
