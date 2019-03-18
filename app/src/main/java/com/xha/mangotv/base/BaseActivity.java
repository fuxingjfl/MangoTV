package com.xha.mangotv.base;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xha.mangotv.config.Config;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.RSAUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by lxk on 2017/6/10.
 */

public class BaseActivity extends AppCompatActivity {
    private static List<Activity> activityList = new ArrayList<>();
    private static int MY_PERMISSION_REQUEST_CODE=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里判断是否token是否存在、是否过期之类的
        if (activityList != null)
            activityList.add(this);
//        boolean isPermission = checkSelfPermissionAll(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION});
//        if (isPermission) {
////            Toast.makeText(MainActivity.this, "正在查看!", Toast.LENGTH_SHORT).show();
//            return;
//        }
////        请求权限
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
    }



//    /** 判断触摸时间派发间隔 */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (MyUtils.isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    //获取状态栏的高度
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isPermission = true;
            for (int grant : grantResults) {
                // 判断是否所有的权限都已经授予了
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isPermission = false;
                    break;
                }
            }
            if (isPermission) {
//                Toast.makeText(BaseActivity.this, "我看看", Toast.LENGTH_SHORT).show();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("部分权限需要开启")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("退出",null);
                builder.show();
            }
        }
    }

    //    检查是否拥有指定的所有权限
    private boolean checkSelfPermissionAll(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //        overridePendingTransition(R.anim.start_activity_in, R.anim.start_activity_out);
    }

    @Override
    public void finish() {
        super.finish();
        //        overridePendingTransition(R.anim.finish_activity_in, R.anim.finish_activity_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null)
            activityList.remove(this);
    }

    /*
    * 必须调用 MobclickAgent.onResume() 和 MobclickAgent.onPause()方法，
    * 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
    * 这两个方法是用来统计应用时长的(也就是Session时长,当然还包括一些其他功能)
    * MobclickAgent.onPageStart() 和 MobclickAgent.onPageEnd() 方法是用来统计页面跳转的
    * 在仅有Activity的应用中，SDK 自动帮助开发者调用了上面的方法，并把Activity 类名作为页面名称统计。
    * 但是在包含fragment的程序中我们希望统计更详细的页面，所以需要自己调用方法做更详细的统计。
    * */
    @Override
    protected void onResume() {
        super.onResume();
       // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  MobclickAgent.onPause(this);
    }

    public static List<Activity> getAllActivitys() {
        return activityList;
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            //            activityList = null;
        }
    }

    public static void realBack() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            activityList = null;
        }
    }
//    private void UMPush() {
//        UmengMessageHandler umengMessageHandler=new UmengMessageHandler(){
//            @Override
//            public Notification getNotification(Context context, UMessage uMessage) {
//                EventMessage eventMessage=new EventMessage("tuisong");
//                EventBus.getDefault().postSticky(eventMessage);
//                return super.getNotification(context, uMessage);
//
//            }
//        };
//
//
//
//    }

    public void setShowPop(PopupWindow popupWindow, View view){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            setWindowTranslucence(0.3);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }
    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }


    protected String getSecret() {
        String RSASign = PreUtil.getInstance().getString(PreContact.SIGN);
        String data=null;
        try {
            data=new String(RSAUtil.decrypt(RSAUtil.loadPrivateKey(Config.PRIVATEKEY), Base64.decode(RSASign, Base64.DEFAULT)));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     * @param year
     * @param monthOfYear
     * @return
     */ public Date getSupportEndDayofMonth(int year, int monthOfYear) { Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year); cal.set(Calendar.MONTH, monthOfYear); cal.set(Calendar.DAY_OF_MONTH, 1); cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59); cal.set(Calendar.SECOND, 59); cal.add(Calendar.DAY_OF_MONTH, -1); Date lastDate = cal.getTime(); cal.set(Calendar.DAY_OF_MONTH, 1); Date firstDate = cal.getTime(); return lastDate; }

}
