package com.xha.mangotv.base;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import android.util.Log;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xha.mangotv.R;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.ImageLoder;
import com.xha.mangotv.util.PreUtil;

import java.util.Iterator;
import java.util.List;


/**
 * Created by ASUS on 2018/3/19.
 */

public class Myapplication extends Application {

    public static Myapplication application;


    public static Myapplication getInstance() {
        return application;
    }

    public static Myapplication getAppContext() {
        return application;
    }
    public static Context getGloableContext()    {
        return  application.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        GlobalParams.isTest=false;
        ImageLoder.info(this);
        PreUtil.getInstance().init(this);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.ic_launcher) //
                .showImageOnFail(R.drawable.ic_launcher) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
    }



    public static Myapplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
