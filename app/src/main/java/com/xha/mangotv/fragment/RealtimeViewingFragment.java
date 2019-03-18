package com.xha.mangotv.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xha.mangotv.Presenter.ChannelGroupPresenter;
import com.xha.mangotv.Presenter.ChannelGroupView;
import com.xha.mangotv.Presenter.PermissionAccessPresenter;
import com.xha.mangotv.Presenter.PermissionAccessView;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.AddressActivity;
import com.xha.mangotv.activity.SigninActivity;
import com.xha.mangotv.adapter.MyViewPageAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.ChannelGroup;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.PermissionAccess;
import com.xha.mangotv.entity.SSlvInfo;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.BaseDialog;
import com.xha.mangotv.view.NoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 实时收视
 * Created by ysq on 2018/8/30.
 */

public class RealtimeViewingFragment extends BaseFragment implements ChannelGroupView {

    private NoScrollViewPager vp_pager;
    private TabLayout tab;
    private List<BaseFragment> list;
    private MyViewPageAdapter myViewPage;
    private List<String > name;
    private RelativeLayout rl_title;
    private LinearLayout ll_rq,ll_dz;
    private BaseDialog dialog;
    private TextView tv_zdl;
    private ImageView iv_del;
    private MyThread myThread;
    private Calendar calendar ;
    private TextView tv_sy_time;
    private TextView tv_rl_day;
    private TextView tv_sj;
    private Handler handler = new Handler();
    private StringBuffer stringBuffer;
    private ChannelGroupPresenter channelGroupPresenter;
    private TextView tv_name_area;

    @Override
    protected int setContentView() {
        return R.layout.fragment_realtime_viewing;
    }

    @Override
    protected void lazyLoad() {
        View contentView = getContentView();
        vp_pager=contentView.findViewById(R.id.vp_pager);
        tab=contentView.findViewById(R.id.tab);
        rl_title= (RelativeLayout) findViewById(R.id.rl_title);
        ll_rq= (LinearLayout) findViewById(R.id.ll_rq);
        ll_dz= (LinearLayout) findViewById(R.id.ll_dz);
        tv_sy_time= (TextView) findViewById(R.id.tv_sy_time);
        tv_name_area=findViewById(R.id.tv_name_area);
        tv_rl_day=findViewById(R.id.tv_rl_day);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.REFRESH_ADDRESS);
        intentFilter.addAction(ConstantValues.SX_DATA);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        channelGroupPresenter = new ChannelGroupPresenter(this);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_title.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        list = new ArrayList<>();
        name = new ArrayList<>();
        ll_rq.setOnClickListener(onClickListener);
        ll_dz.setOnClickListener(onClickListener);
        myThread = new MyThread();
        myThread.start();

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_rq:
                    shumaDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                    break;
                case R.id.ll_dz:
                    Intent intent = new Intent(getActivity(), AddressActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

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
    public String getCGUrl() {

//        return ConstantValues.URI_YONG_TEST+"channel/getAllChannelGroup";
        return ConstantValues.AUTH_SITE+"interface";
    }

    @Override
    public int getCGCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getCGBody() {
//        return "";

        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","channel/getAllChannelGroup");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("sign",sign);
            jsonObject.put("params","");
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }

    @Override
    public void getCGDataFailureMsg(String msg) {

//        Toast.makeText(getActivity(),"正在努力的获取数据",Toast.LENGTH_SHORT).show();

            boolean aBoolean = PreUtil.getInstance().getBoolean(ConstantValues.selected, false);
            if(aBoolean){
//                getTabData();
            }
    }

    @Override
    public void getCGData(Info info) {

        try {
            List<ChannelGroup> channelGroups = info.channelGroups;
            List<ChannelGroup> cg = new ArrayList<>();
            String string = PreUtil.getInstance().getString(ConstantValues.channelGroup);
            JSONArray jsonArray = null;
            jsonArray = new JSONArray(string);
            for (int i=0;i<channelGroups.size();i++){
                ChannelGroup channelGroup = channelGroups.get(i);
                boolean iszs=false;//没有
                for (int j=0;j<jsonArray.length();j++){
                    String code=jsonArray.getString(j);
                    if(channelGroup.channelGroupCode.equals(code)){
                        iszs=true;
                        break;
                    }
                }
                if(iszs){
                    cg.add(channelGroup);
                }
            }
            for (int i=0;i<cg.size();i++){
                ChannelGroup channelGroup = cg.get(i);
                name.add(channelGroup.channelGroupName);
                boolean iszs=true;
                ContentFragment contentFragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("channelGroupCode",channelGroup.channelGroupCode);
                bundle.putBoolean("iszs",iszs);
                contentFragment.setArguments(bundle);
                list.add(contentFragment);
            }
            Log.e("TAG","里面的碎片个数:::"+list.size());
            myViewPage = new MyViewPageAdapter(getChildFragmentManager(),list,name);
            vp_pager.setAdapter(myViewPage);
            tab.setupWithViewPager(vp_pager);
            vp_pager.setOffscreenPageLimit(channelGroups.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MyThread  extends Thread{
        @Override
        public void run() {
            do{
                try {
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            calendar= Calendar.getInstance();
                            //日
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

//                            //小时
//                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                            //分钟
//                            int minute = calendar.get(Calendar.MINUTE);
                            stringBuffer = new StringBuffer();
                            stringBuffer.delete(0,stringBuffer.length());//删除所有的数据
                            stringBuffer.append(sdf.format(new Date()));
                            tv_sy_time.setText(stringBuffer.toString());
                            tv_rl_day.setText(day+"");
                            stringBuffer.insert(1," ");
                            stringBuffer.insert(3," ");
                            stringBuffer.insert(5," ");
                            stringBuffer.insert(7," ");
                            if(tv_sj!=null){
                                tv_sj.setText(stringBuffer.toString());
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("TAG","InterruptedException");
                }
            }while (true);
        }
    }


    /*
* 对话框
* */
    private void shumaDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(getActivity());
        dialog = builder.setViewId(R.layout.dialog_layout)
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
        tv_zdl = dialog.getView(R.id.tv_zdl);
        iv_del = dialog.getView(R.id.iv_del);
        dialog.show();
        TextView tv_xq = dialog.getView(R.id.tv_xq);
        TextView tv_nyr=dialog.findViewById(R.id.tv_nyr);
        tv_sj=dialog.findViewById(R.id.tv_sj);

        if(stringBuffer!=null){
            stringBuffer.insert(1,"");
            stringBuffer.insert(3,"");
            stringBuffer.insert(5,"");
            stringBuffer.insert(7,"");

            tv_sj.setText(stringBuffer.toString());
        }


        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String format = dateFm.format(new Date());
        tv_xq.setText(format);
        SimpleDateFormat nyr = new SimpleDateFormat("yyyy年MM月dd日");
        String date = nyr.format(new Date());
        tv_nyr.setText(date);

        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_zdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","RealtimeViewingFragment1111111======回收界面");
        if(myThread!=null&&!myThread.isAlive()){
            myThread.stop();
        }
        myThread=null;
        getActivity().unregisterReceiver(broadcastReceiver);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.e("TAG","RealtimeViewingFragment1111111======保存数据");
//
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        Log.e("TAG","RealtimeViewingFragment1111111======恢复数据");
//
//    }



    private void getTabData(){
        if(NetUtil.checkNet(getActivity())){
            channelGroupPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConstantValues.REFRESH_ADDRESS.equals(intent.getAction())){

                Log.e("TAG","===========执行了REFRESH_ADDRESS====");
                String areaName = intent.getStringExtra("areaName");
                if("14300".equals(intent.getStringExtra("areaCode"))){
                    tv_name_area.setText("全省");
                }else{
                    tv_name_area.setText(areaName);
                }
                for (int i=0;i<list.size();i++){

                    ContentFragment contentFragment = (ContentFragment) list.get(i);
                    contentFragment.setRefreshArea(intent.getStringExtra("areaCode"));

                }
            }else if(ConstantValues.SX_DATA.equals(intent.getAction())){
                boolean aBoolean = PreUtil.getInstance().getBoolean(ConstantValues.selected, false);
                if(aBoolean){
                    getTabData();
                }
            }
        }
    };
}