package com.xha.mangotv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xha.mangotv.MainActivity;
import com.xha.mangotv.Presenter.PermissionAccessPresenter;
import com.xha.mangotv.Presenter.PermissionAccessView;
import com.xha.mangotv.Presenter.PhoneYZPresenter;
import com.xha.mangotv.Presenter.PhoneYZView;
import com.xha.mangotv.Presenter.IsSigninPresenter;
import com.xha.mangotv.Presenter.IsSigninView;
import com.xha.mangotv.Presenter.SigninPresenter;
import com.xha.mangotv.Presenter.SigninView;
import com.xha.mangotv.Presenter.YZMPresenter;
import com.xha.mangotv.Presenter.YZMView;
import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.CountDownUtils;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.NumYzUtils;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.RSAUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.util.StrKit;
import com.xha.mangotv.view.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ysq on 2018/8/30.
 */

public class SigninActivity extends BaseActivity implements YZMView,SigninView {

    private Button btn_dl;
    private EditText et_phone_head,et_phone,et_password;
    private TextView tv_del;
    private String phone,yzm;
    private YZMPresenter yzmPresenter;
    private Button btn_rp_hqyzm;
    private CountDownUtils time;
    private SigninPresenter signinPresenter;
    private BaseDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        setStatusBarFullTransparent();

        btn_dl= (Button) findViewById(R.id.btn_dl);
        et_phone_head= (EditText) findViewById(R.id.et_phone_head);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_password= (EditText) findViewById(R.id.et_password);
        tv_del= (TextView) findViewById(R.id.tv_del);
        btn_rp_hqyzm= (Button) findViewById(R.id.btn_rp_hqyzm);
        yzmPresenter = new YZMPresenter(this);
        signinPresenter = new SigninPresenter(this);
        // 倒计时参数设置
        time = new CountDownUtils(1000 * 60, 1000, btn_rp_hqyzm);
        btn_dl.setOnClickListener(onClickListener);
        tv_del.setOnClickListener(onClickListener);
        btn_rp_hqyzm.setOnClickListener(onClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.SECRET = getSecret();
//        Config.SECRET="YM^*g@H.5i^982b%oLkaFDaBSdswls8m";

        Log.e("TAG","签名::"+Config.SECRET);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btn_dl:
                    phone =et_phone.getText().toString().trim();
                    yzm=et_password.getText().toString().trim();
                    if(phone!=null&&phone.length()>0){
                        if(yzm!=null&&yzm.length()>0){
                            if(NumYzUtils.isPhoneNumberValid(phone)){
                                if (StrKit.isBlank(PreUtil.getInstance().getString(PreContact.SIGN))) {
                                    Toast.makeText(SigninActivity.this, "该设备未注册，请注册设备", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(SigninActivity.this, RegisterActivity.class);
                                    startActivity(intent);
                                }else{
                                    shumaDialog(Gravity.CENTER,R.style.Alpah_aniamtion);

                                    getSignin();
                                }
                            }else {
                                Toast.makeText(SigninActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SigninActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SigninActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    }

//                    PreUtil.getInstance().putString(PreContact.username,"13370133060");
//                    Log.e("TAG","===============执行true==================");
//                    PreUtil.getInstance().putBoolean(PreContact.isLogin,true);
//                    intent = new Intent(SigninActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                    break;
                case R.id.tv_del:
                    et_phone.setText("");
                    break;
                case R.id.btn_rp_hqyzm:
                    phone =et_phone.getText().toString().trim();
                    if(phone!=null&&phone.length()>0){
                            if(StrKit.notBlank(PreUtil.getInstance().getString(PreContact.SIGN))){
//                                if(StrKit.notBlank(Config.SECRET)){
                                    getyzm();
                            }else {
                                Toast.makeText(SigninActivity.this, "该设备未注册，请注册设备", Toast.LENGTH_SHORT).show();
                                intent = new Intent(SigninActivity.this, RegisterActivity.class);
                                startActivity(intent);
                            }
                    }else{
                        Toast.makeText(SigninActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

        protected void setStatusBarFullTransparent() {
            if (Build.VERSION.SDK_INT >= 21) {//21表示5.0

                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    /*
* 对话框
* */
    private void shumaDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog = builder.setViewId(R.layout.view_tips_loading)
                //设置dialogpadding
                .setPaddingdp(0, 10, 0, 10)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();
        dialog.show();
    }

    @Override
    public String getYZMUrl() {
        return ConstantValues.AUTH_SITE+"interface";
//        return ConstantValues.URI_YONG_TEST+"phoneSystem/getCode";
    }

    @Override
    public int getYZMCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getYZMBody() {
        long time= System.currentTimeMillis();
        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","phoneSystem/getCode");
        map.put("phone",phone);
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",time +"");
        Log.e("TAG","具体:::phone=="+phone+",imei=="+MACUtil.getMacAddress()+",currTime==="+System.currentTimeMillis());
        Log.e("TAG","getSecret==="+getSecret());
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
        try {
            jsonObject=JSONTools.parseMapToJson(map);
            jsonObject = new JSONObject();
            jsonObject.put("redirectUrl","phoneSystem/getCode");
            jsonObject.put("phone",phone);
            jsonObject.put("imei", MACUtil.getMacAddress());
            jsonObject.put("currTime",time+"");
            jsonObject.put("sign",sign);
            jsonObject.put("tel",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }

    @Override
    public void getYZMDataFailureMsg(String msg) {
        Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getYZMData(Info info) {

        Toast.makeText(SigninActivity.this, "验证码已发出，请注意查收", Toast.LENGTH_SHORT).show();


    }


    private void getyzm() {
        if (phone.matches("^1(3|4|5|7|8)\\d{9}$")) {
            if (NetUtil.checkNet(this)) {
                time.start();

                yzmPresenter.getDisposeData();

            } else {
                Toast.makeText(getApplicationContext(), "请连接您的网络！",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "手机号格式错误！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //登录
    private void getSignin(){
        if(NetUtil.checkNet(SigninActivity.this)){
            signinPresenter.getDisposeData();
        }else {
            Toast.makeText(SigninActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public String getdlUrl() {
        return ConstantValues.AUTH_SITE+"interface";
    }

    @Override
    public int getdlCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getdlBody() {
        JSONObject jsonObject=null;


        try {
            JSONObject params = new JSONObject();
            params.put("tel",phone);
            params.put("code",yzm);
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","phoneSystem/newPhoneUserLogin");
            map.put("phone",phone);
            map.put("imei", Config.SIGN);
            map.put("currTime",System.currentTimeMillis()+"");
            String sign= Sign.getSign(map, getSecret());
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject=JSONTools.mergeJson(jsonObject,params.toString());
            jsonObject.put("sign",sign);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }

    @Override
    public void getdlDataFailureMsg(String msg) {

        Toast.makeText(SigninActivity.this,msg,Toast.LENGTH_SHORT).show();

        dialog.dismiss();

    }

    @Override
    public void getdlData(Info info) {
                    dialog.dismiss();
                    PreUtil.getInstance().putString(PreContact.username,phone);
                    Log.e("TAG","===============执行true==================");
                    PreUtil.getInstance().putBoolean(PreContact.isLogin,true);
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
    }
}