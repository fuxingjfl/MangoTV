package com.xha.mangotv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.fragment.InflowOutflowLSFragment;
import com.xha.mangotv.fragment.UserTrendFragment;
import com.xha.mangotv.fragment.ViewingTrendFragment;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.PreUtil;

/**
 * 历史详情
 * Created by ysq on 2018/9/5.
 */
public class HistoryActivity extends BaseActivity {
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private TextView title_content;
    private RadioGroup rg_content;
    private RadioButton rb_pdsszs,rb_pdzx,rb_lrlc_hh;
    private ViewingTrendFragment viewingTrendFragment;
    private InflowOutflowLSFragment inflowOutflowLSFragment;
    private UserTrendFragment userTrendFragment;
    private BaseFragment currentf;
    private LiShiInfo liShiInfo;
    private boolean selected_pdsszs,userOnline,inout_pdssph;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_history);
        rl_bt= (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui= (ImageView) findViewById(R.id.iv_fanhui);
        title_content= (TextView) findViewById(R.id.title_content);
        rb_pdsszs= (RadioButton) findViewById(R.id.rb_pdsszs);
        rb_lrlc_hh= (RadioButton) findViewById(R.id.rb_lrlc_hh);
        rb_pdzx= (RadioButton) findViewById(R.id.rb_pdzx);
        rg_content = (RadioGroup) findViewById(R.id.rg_content);
        liShiInfo = (LiShiInfo) getIntent().getSerializableExtra("liShiInfo");
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.fanhui);
        title_content.setText(liShiInfo.channelName);
        title_content.setVisibility(View.VISIBLE);
        selected_pdsszs= PreUtil.getInstance().getBoolean(ConstantValues.selected_pdsszs,false);
        userOnline=PreUtil.getInstance().getBoolean(ConstantValues.userOnline,false);
        inout_pdssph=PreUtil.getInstance().getBoolean(ConstantValues.inout_pdssph,false);
        if (!selected_pdsszs){
            rb_pdsszs.setEnabled(false);
            rb_pdsszs.setFocusable(false);
            rb_pdsszs.setClickable(false);
        }
        if (!userOnline){
            rb_pdzx.setChecked(false);
            rb_pdzx.setEnabled(false);
            rb_pdzx.setFocusable(false);
            rb_pdzx.setClickable(false);
        }
        if (!inout_pdssph){
            rb_lrlc_hh.setChecked(false);
            rb_lrlc_hh.setEnabled(true);
            rb_lrlc_hh.setFocusable(false);
            rb_lrlc_hh.setClickable(false);
        }
        if (selected_pdsszs){
            if(viewingTrendFragment==null){
                viewingTrendFragment = new ViewingTrendFragment();
                Bundle bundle = new Bundle();
                bundle.putString("channelGroupCode",liShiInfo.channelGroupCode);
                bundle.putString("channelCode",liShiInfo.channelCode);
                bundle.putString("channelName",liShiInfo.channelName);
                viewingTrendFragment.setArguments(bundle);
            }
            addFragments(viewingTrendFragment,"ViewingTrendFragment");
        }
        iv_fanhui.setOnClickListener(onClickListener);
        rg_content.setOnCheckedChangeListener(onCheckedChangeListener);
    }
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_pdsszs:
                    if (selected_pdsszs){
                        if(viewingTrendFragment == null){
                            viewingTrendFragment  =new ViewingTrendFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("channelGroupCode",liShiInfo.channelGroupCode);
                            bundle.putString("channelCode",liShiInfo.channelCode);
                            bundle.putString("channelName",liShiInfo.channelName);
                            viewingTrendFragment.setArguments(bundle);
                        }
                        addFragments(viewingTrendFragment,"ViewingTrendFragment");
                        setHide();
                    }
                    break;
                case R.id.rb_lrlc_hh:
                    if(inflowOutflowLSFragment == null){
                        inflowOutflowLSFragment  =new InflowOutflowLSFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("channelGroupCode",liShiInfo.channelGroupCode);
                        bundle.putString("channelCode",liShiInfo.channelCode);
                        bundle.putString("areaCode",liShiInfo.areaCode);
                        bundle.putString("channelName",liShiInfo.channelName);
                        inflowOutflowLSFragment.setArguments(bundle);
                    }
                    addFragments(inflowOutflowLSFragment,"InflowOutflowLSFragment");
                    setHide();
                    break;
                case R.id.rb_pdzx:
                    if (userOnline){
                        if(userTrendFragment == null){
                            userTrendFragment  =new UserTrendFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("channelCode",liShiInfo.channelCode);
                            userTrendFragment.setArguments(bundle);
                        }
                        addFragments(userTrendFragment,"UserTrendFragment");
                        setHide();
                    }
                    break;
            }
        }
    };

    public void setHide(){
        if (viewingTrendFragment!=null){
            viewingTrendFragment.setHide();
        }
        if (inflowOutflowLSFragment!=null){
            inflowOutflowLSFragment.setHide();
        }
        if (userTrendFragment!=null){
            userTrendFragment.setHide();
        }
    }

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
    private void addFragments(BaseFragment f,String tag) {
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
            transaction.add(R.id.fl_history_content, f,tag);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commitAllowingStateLoss();
        currentf = f;
    }
}
