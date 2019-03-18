package com.xha.mangotv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.xha.mangotv.MainActivity;
import com.xha.mangotv.R;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;

/**
 * Created by ysq on 2018/8/30.
 */

public class FlickerSreenActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyContants.windows(this);
        setContentView(R.layout.activity_flicker_sreen);
//        Config.SIGN = "F3379180-F4A2-4AA9-A03D-91A21ADED873";
        Config.SIGN=MACUtil.getMacAddress();
        Log.e("TAG","SIGN==="+Config.SIGN);
        handler = new Handler();
        final boolean aBoolean = PreUtil.getInstance().getBoolean(PreContact.isLogin, false);
        Log.e("TAG","aBoolean=="+aBoolean);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (aBoolean){
                    Intent intent = new Intent(FlickerSreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(FlickerSreenActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
