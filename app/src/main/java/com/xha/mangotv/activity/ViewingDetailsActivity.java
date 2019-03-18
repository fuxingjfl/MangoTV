package com.xha.mangotv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.entity.StbNumList;
import com.xha.mangotv.fragment.InflowOutflowFragment;
import com.xha.mangotv.fragment.LiShiFragment;
import com.xha.mangotv.fragment.RealTimeCurveFragment;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.view.HRecyclerView;

/**
 * Created by ysq on 2018/9/4.
 */

public class ViewingDetailsActivity extends BaseActivity {

    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private ImageView title_tv;
    private RadioGroup rg_content;
    private RealTimeCurveFragment realTimeCurveFragment;
    private InflowOutflowFragment inflowOutflowFragment;
    private LiShiFragment liShiFragment;
    private BaseFragment currentf;
    private String areaCode,channelCode;
    private RealTimeData info;
    private boolean inout,history,realtimeLine;
    private RadioButton rb_ssqx,rb_lrlc,rb_lsss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_viewing_details);
        rl_bt= (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui= (ImageView) findViewById(R.id.iv_fanhui);
        title_tv= (ImageView) findViewById(R.id.title_tv);
        rg_content = (RadioGroup) findViewById(R.id.rg_content);
        rb_ssqx= (RadioButton) findViewById(R.id.rb_ssqx);
        rb_lrlc= (RadioButton) findViewById(R.id.rb_lrlc);
        rb_lsss= (RadioButton) findViewById(R.id.rb_lsss);
        areaCode=getIntent().getStringExtra("areaCode");
        channelCode=getIntent().getStringExtra("channelCode");
        info= (RealTimeData) getIntent().getSerializableExtra("RealTimeData");
        Log.e("TAG","流入流出数量:::"+info.inflowLists.size()+",数量2==="+info.outflowLists.size());
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.fanhui);
        title_tv.setVisibility(View.VISIBLE);
        inout=PreUtil.getInstance().getBoolean(ConstantValues.inout,false);
        history=PreUtil.getInstance().getBoolean(ConstantValues.history,false);
        realtimeLine=PreUtil.getInstance().getBoolean(ConstantValues.realtimeLine,false);
        if (!inout){
            rb_lrlc.setEnabled(false);
            rb_lrlc.setFocusable(false);
            rb_lrlc.setClickable(false);
        }
        if (!realtimeLine){
            rb_ssqx.setChecked(false);
            rb_ssqx.setEnabled(true);
            rb_ssqx.setFocusable(false);
            rb_ssqx.setClickable(false);
        }

        if (!history){
            rb_lsss.setEnabled(true);
            rb_lsss.setFocusable(false);
            rb_lsss.setClickable(false);
        }

        if(realtimeLine){
            if(realTimeCurveFragment == null){
                realTimeCurveFragment  =new RealTimeCurveFragment();
                Bundle bundle = new Bundle();
                bundle.putString("areaCode",areaCode);
                bundle.putString("channelCode",info.channelCode);
                bundle.putString("channelName",info.channelName);
                bundle.putString("marketShare",info.marketShare);
                realTimeCurveFragment.setArguments(bundle);
            }
            addFragments(realTimeCurveFragment);
        }

        iv_fanhui.setOnClickListener(onClickListener);
        rg_content.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                case R.id.rb_ssqx:

                    if(realtimeLine){
                        if(realTimeCurveFragment == null){
                            realTimeCurveFragment  =new RealTimeCurveFragment();

                            Bundle bundle = new Bundle();
                            bundle.putString("areaCode",areaCode);
                            bundle.putString("channelCode",info.channelCode);
                            bundle.putString("channelName",info.channelName);
                            bundle.putString("marketShare",info.marketShare);
                            realTimeCurveFragment.setArguments(bundle);

                        }
                        addFragments(realTimeCurveFragment);
                    }


                    break;
                case R.id.rb_lrlc:

                    if (inout){
                        if(inflowOutflowFragment == null){
//                            inflowOutflowFragment  =new InflowOutflowFragment(info.inflowLists,info.outflowLists,info.channelName,info.marketShare,info.stbNumLists.get(0),info.inRating,info.outRating);
                            inflowOutflowFragment  =new InflowOutflowFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("RealTimeData",info);
                            inflowOutflowFragment.setArguments(bundle);
                        }
                        addFragments(inflowOutflowFragment);
                    }

                    break;
                case R.id.rb_lsss:
                    if(history){
                        if(liShiFragment == null){
//                            liShiFragment  =new LiShiFragment(areaCode,channelCode);
                            liShiFragment  =new LiShiFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("areaCode",areaCode);
                            bundle.putString("channelCode",channelCode);
                            liShiFragment.setArguments(bundle);

                        }
                        addFragments(liShiFragment);
                    }
                    break;
            }

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_fanhui:
                    finish();
                    break;
            }
        }
    };
    private void addFragments(BaseFragment f) {
        // 第一步：得到fragment管理类
        FragmentManager manager = getSupportFragmentManager();
        // 第二步：开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();

        if (currentf != null) {
            //每次把前一个fragment给隐藏了
            transaction.hide(currentf);
        }
        //isAdded:判断当前的fragment对象是否被加载过
        if (!f.isAdded()) {
            // 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
            transaction.add(R.id.fl_xq_content, f);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commitAllowingStateLoss();
        currentf = f;
    }
}
