package com.xha.mangotv.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xha.mangotv.Presenter.RegisterPresenter;
import com.xha.mangotv.Presenter.RegisterView;
import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.RSAUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**\
 *
 * 注册页面
 * Created by ysq on 2018/9/23.
 */

public class RegisterActivity extends BaseActivity implements RegisterView{


    private EditText et_phone_zc;
    private EditText et_password_zc;
    private Button btn_dl_zc;
    private String phone, invitationCode;
    private RegisterPresenter registerPresenter;
    private BaseDialog dialog;
    private TextView tv_del;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBarFullTransparent();
        et_phone_zc= (EditText) findViewById(R.id.et_phone_zc);
        et_password_zc= (EditText) findViewById(R.id.et_password_zc);
        btn_dl_zc= (Button) findViewById(R.id.btn_dl_zc);
        tv_del= (TextView) findViewById(R.id.tv_del);
        registerPresenter = new RegisterPresenter(this);
        btn_dl_zc.setOnClickListener(onClickListener);
        tv_del.setOnClickListener(onClickListener);

//        String sign="%XnIeorAO8ika62KfHG*TIyYRJGbvGiS";
//        byte[] RSASign = null;
//        try {
//            RSASign = RSAUtil.encrypt(RSAUtil.loadPublicKey(Config.PUBLICKEY), sign.getBytes("UTF-8"));
//            PreUtil.getInstance().putString(PreContact.SIGN, Base64.encodeToString(RSASign, Base64.DEFAULT));
//            finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_dl_zc:
                    phone=et_phone_zc.getText().toString().trim();
                    invitationCode=et_password_zc.getText().toString().trim();
                    if(phone==null||phone.length()<=0){
                        Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(invitationCode==null||invitationCode.length()<=0){
                        Toast.makeText(RegisterActivity.this,"请输入邀请码",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    shumaDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                    getRegister();
                    break;
                case R.id.tv_del:
                    et_phone_zc.setText("");
                    break;
            }
        }
    };

    /*
    * 对话框
    * */
    private void shumaDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog  = builder.setViewId(R.layout.view_tips_loading)
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
        TextView tips_loading_msg = dialog.findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText("注册中...");
    }

    @Override
    public String getRUrl() {
        return ConstantValues.AUTH_SITE+"register";
    }

    @Override
    public int getRCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getRBody() {
        long time=System.currentTimeMillis();
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("imei", Config.SIGN);
        map.put("registerCode",invitationCode);
        map.put("currTime",time+"");
        String sign= Sign.getSign(map, invitationCode);
        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("phone",phone);
            jsonObject.put("imei", Config.SIGN);
            jsonObject.put("registerCode",invitationCode);
            jsonObject.put("currTime",time+"");
            jsonObject.put("sign",sign);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }

    @Override
    public void getRDataFailureMsg(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void getRData(Info info) {
        String sign=info.msg;
        byte[] RSASign = null;
        try {
            RSASign = RSAUtil.encrypt(RSAUtil.loadPublicKey(Config.PUBLICKEY), sign.getBytes("UTF-8"));
            PreUtil.getInstance().putString(PreContact.SIGN, Base64.encodeToString(RSASign, Base64.DEFAULT));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dialog.dismiss();
        }
    }

    private void getRegister(){
        if(NetUtil.checkNet(RegisterActivity.this)){
            registerPresenter.getDisposeData();
        }else {
            Toast.makeText(RegisterActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
}
