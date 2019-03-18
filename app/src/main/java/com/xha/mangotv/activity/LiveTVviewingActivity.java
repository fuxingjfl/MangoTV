package com.xha.mangotv.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xha.mangotv.Presenter.AddContrastChannelPresenter;
import com.xha.mangotv.Presenter.AddContrastChannelView;
import com.xha.mangotv.Presenter.AddressPresenter;
import com.xha.mangotv.Presenter.AddressView;
import com.xha.mangotv.Presenter.LiveTVPresenter;
import com.xha.mangotv.Presenter.LiveTVView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.CommonViewHolder;
import com.xha.mangotv.adapter.JiemuAdapter;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Index;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.AppMenu;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.CalendarTVPop;
import com.xha.mangotv.view.ChannelTVPop;
import com.xha.mangotv.view.ChannelTypeChoiceListener;
import com.xha.mangotv.view.ChannelTypePop;
import com.xha.mangotv.view.HRecyclerView;
import com.xha.mangotv.view.RegionPop;
import com.xha.mangotv.view.RegionTVPop;
import com.xha.mangotv.view.SelectClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/9/4.
 */

public class LiveTVviewingActivity extends BaseActivity implements LiveTVView,AddContrastChannelView{
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private TextView title_content;
    private HRecyclerView rv_cr;
    private List<LiShiInfo> list;
    private LinearLayout ll_dq, ll_time,ll_pdlx;
    private TextView tv_dq,tv_pdlx;
    private ImageView iv_dq, iv_time,iv_pdlx;
    private RegionTVPop regionPop;
    private CalendarTVPop calendarTVPop;
    private ChannelTVPop channelTVPop;
    private LiveTVPresenter liveTVPresenter;
    private TextView tv_time;
    private SwipeRefreshLayout srl_sx;
    private String dayRange,timeRange="00:00:00-23:59:59",areaCode,name;
    private AddContrastChannelPresenter addContrastChannelPresenter;
    private  List<String> mlcode = new ArrayList<>();
    private JiemuAdapter adapter;
    private Handler handler;
    private ImageView iv_wsj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_live_tv);
        rv_cr = (HRecyclerView) findViewById(R.id.rv_cr);
        rl_bt = (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        ll_pdlx= (LinearLayout) findViewById(R.id.ll_pdlx);
        title_content = (TextView) findViewById(R.id.title_content);
        ll_dq = (LinearLayout) findViewById(R.id.ll_dq);
        tv_dq = (TextView) findViewById(R.id.tv_dq);
        iv_dq = (ImageView) findViewById(R.id.iv_dq);
        iv_wsj= (ImageView) findViewById(R.id.iv_wsj);
        srl_sx= (SwipeRefreshLayout) findViewById(R.id.srl_sx);
        ll_time = (LinearLayout) findViewById(R.id.ll_time);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_time = (ImageView) findViewById(R.id.iv_time);
        tv_pdlx= (TextView) findViewById(R.id.tv_pdlx);
        iv_pdlx= (ImageView) findViewById(R.id.iv_pdlx);
        handler = new Handler();
        srl_sx.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        Calendar calendar= Calendar.getInstance();
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH)-1;

        int month = calendar.get(Calendar.MONTH)+1;

        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        if (day==0){
            Date date=new Date();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1);
            date = calendar.getTime();
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
            String rq = format.format(date);
            String[] split = rq.split("-");
//            Date supportEndDayofMonth = getSupportEndDayofMonth(year, month - 1);
//            String rq = sdf.format(supportEndDayofMonth);
            dayRange=rq+":"+rq;
            tv_time.setText(split[1]+"-"+split[2]+":"+split[1]+"-"+split[2]);
        }else{
            String r=String.valueOf(day);
            String y=String.valueOf(month);
            if (r.length()<=1){
                r="0"+r;
            }
            if (y.length()<=1){
                y="0"+y;
            }
            dayRange=year+"-"+y+"-"+r+":"+year+"-"+y+"-"+r;
            tv_time.setText(y+"-"+r+":"+y+"-"+r);
        }
        liveTVPresenter = new LiveTVPresenter(this);
        addContrastChannelPresenter=new AddContrastChannelPresenter(this);
        calendarTVPop = new CalendarTVPop(LiveTVviewingActivity.this,R.layout.pop_calendar_tv,selectorListener,tv_time.getText().toString());
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.fanhui);
        title_content.setText("直播节目收视统计");
        title_content.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        String[] tab = {"频道名称","收视率","收视份额","直播次数","在线用户数","到达率","直播收视时长","同时段排名"};
        String[] mLeftTextList = new String[]{"节目名称"};
        rv_cr.setHeaderListData(tab,mLeftTextList);
        String areaList = PreUtil.getInstance().getString(ConstantValues.areaList_zbtj);
        List<Address> addressmlist = new ArrayList<>();
        JSONArray jsonArray=null;
        try {
            jsonArray = new JSONArray(areaList);
            for (int i=0;i<jsonArray.length();i++){
                String tab_name=jsonArray.getString(i);
                Address address = new Address();
                if("14300".equals(tab_name)){
                    address.areaName="湖南";
                }else if ("14302".equals(tab_name)){
                    address.areaName="株洲";
                }else if ("14303".equals(tab_name)){
                    address.areaName="湘潭";
                }else if ("14307".equals(tab_name)){
                    address.areaName="常德";
                }else if ("14306".equals(tab_name)){
                    address.areaName="岳阳";
                }else if ("14309".equals(tab_name)){
                    address.areaName="益阳";
                }else if ("14313".equals(tab_name)){
                    address.areaName="娄底";
                }else if ("14331".equals(tab_name)){
                    address.areaName="吉首";
                }else if ("14308".equals(tab_name)){
                    address.areaName="张家界";
                }else if ("14312".equals(tab_name)){
                    address.areaName="怀化";
                }else if ("14305".equals(tab_name)){
                    address.areaName="邵阳";
                }else if ("14310".equals(tab_name)){
                    address.areaName="郴州";
                }else if ("14311".equals(tab_name)){
                    address.areaName="永州";
                }else if ("14304".equals(tab_name)){
                    address.areaName="衡阳";
                }else if ("14301".equals(tab_name)){
                    address.areaName="长沙";
                }
                address.areaCode=tab_name;
                addressmlist.add(address);
            }
            Collections.sort(addressmlist);
            areaCode=addressmlist.get(0).areaCode;
            name = addressmlist.get(0).areaName;
            regionPop = new RegionTVPop(LiveTVviewingActivity.this, R.layout.pop_region_tv, regionlistener, addressmlist,tv_time.getText().toString());
            calendarTVPop.setNameText(addressmlist.get(0).areaName);
            regionPop.setNameText(addressmlist.get(0).areaName);
            tv_dq.setText(addressmlist.get(0).areaName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getPDData();
        ll_dq.setOnClickListener(onClickListener);
        ll_time.setOnClickListener(onClickListener);
        ll_pdlx.setOnClickListener(onClickListener);
        iv_fanhui.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_dq:
                    tv_dq.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_dq.setImageResource(R.drawable.xia_xz);
                    if (regionPop != null) {
                        if (regionPop.isShowing()) {
                            regionPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            regionPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
                case R.id.ll_time:
                    tv_time.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_time.setImageResource(R.drawable.xia_xz);
                    if (calendarTVPop != null) {
                        if (calendarTVPop.isShowing()) {
                            calendarTVPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarTVPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
                case R.id.ll_pdlx:
                    tv_pdlx.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_pdlx.setImageResource(R.drawable.xia_xz);
                    if (channelTVPop != null) {
                        if (channelTVPop.isShowing()) {
                            channelTVPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            channelTVPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
                case R.id.iv_fanhui:
                    finish();
                    break;
            }
        }
    };
   private ChannelTVPop.ChannelTypeSelectorListener channelTypeSelectorListener = new ChannelTVPop.ChannelTypeSelectorListener() {
       @Override
       public void onCancelListener() {

           channelTVPop.dismiss();
           setWindowTranslucence(1.0f);
           initState();

       }
       @Override
       public void onChannelTypeSelectorListener(List<String> ml) {
           mlcode.clear();
           mlcode.addAll(ml);
           srl_sx.setRefreshing(true);
           handler.postDelayed(runnable,0);
           channelTVPop.dismiss();
           setWindowTranslucence(1.0f);
           initState();
       }
   };
    private RegionTVPop.RegionSelectorListener regionlistener = new RegionTVPop.RegionSelectorListener() {
        @Override
        public void onCancelListener() {
            regionPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
        @Override
        public void onRegionSelectorListener(String data,String name) {
            calendarTVPop.setNameText(name);
            channelTVPop.setNameText(name);
            regionPop.setNameText(name);
            tv_dq.setText(name);
            areaCode=data;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            regionPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };
    private CalendarTVPop.SelectorListener selectorListener = new CalendarTVPop.SelectorListener() {
        @Override
        public void onSelectorTimeListener(String sj,String time) {

//            String[] split = sj.split(":");
//            tv_time.setText(split[0]);

            String[] split = sj.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_time.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            calendarTVPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            regionPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            channelTVPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            dayRange=sj;
            timeRange=time;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            calendarTVPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onCancelListener() {

            calendarTVPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };

    private void initState() {

        tv_dq.setTextColor(getResources().getColor(R.color.black));
        iv_dq.setImageResource(R.drawable.xia);
        tv_time.setTextColor(getResources().getColor(R.color.black));
        iv_time.setImageResource(R.drawable.xia);
        tv_pdlx.setTextColor(getResources().getColor(R.color.black));
        iv_pdlx.setImageResource(R.drawable.xia);

    }

    private void getListData(){
        if(NetUtil.checkNet(LiveTVviewingActivity.this)){
            liveTVPresenter.getDisposeData();
        }else {
            Toast.makeText(LiveTVviewingActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getTVUrl() {

        if (GlobalParams.isTest){
                    return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryProgram";

        }else{
            return ConstantValues.AUTH_SITE+"interface";
        }

    }

    @Override
    public int getTVCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getTVBody() {

        if (GlobalParams.isTest){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));


            hashMap.put("dayRange",dayRange);



            hashMap.put("timeRange",timeRange);

        hashMap.put("areaCode",areaCode);

        hashMap.put("channelCodeList",mlcode);

        JSONObject jo = new JSONObject(hashMap);
        Log.e("TAG","参数:::"+jo.toString());
        return jo.toString();

        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryProgram");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {

                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryProgram");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("timeRange",timeRange);
                listHashMap.put("areaCode",areaCode);
                listHashMap.put("channelCodeList",mlcode);
                jsonObject= new JSONObject(listHashMap);

            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }
    }

    @Override
    public void getTVDataFailureMsg(String msg) {
        srl_sx.setRefreshing(false);
        srl_sx.setEnabled(false);
        Toast.makeText(LiveTVviewingActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTVData(Info info) {
        srl_sx.setRefreshing(false);
        srl_sx.setEnabled(false);
        list.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        list.addAll(info.liShiInfos);
        if (list.size()>0){
            iv_wsj.setVisibility(View.GONE);
            rv_cr.setVisibility(View.VISIBLE);
        }else{
            iv_wsj.setVisibility(View.VISIBLE);
            rv_cr.setVisibility(View.GONE);
        }
        if (adapter==null){
            if (rv_cr.getHeaderCount() == 5) {
                setLayout(R.layout.item_content5);
            } else
            if (rv_cr.getHeaderCount() == 4) {
                setLayout(R.layout.item_content4);
            } else if (rv_cr.getHeaderCount() == 3) {
                setLayout(R.layout.item_content3);
            } else if (rv_cr.getHeaderCount() == 2) {
                setLayout(R.layout.item_content2);
            } else {
                setLayout(R.layout.item_content_zz);
            }
            rv_cr.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void setLayout(int layout){
        adapter = new JiemuAdapter(LiveTVviewingActivity.this, list,layout, new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
            @Override
            public void onItemLongClickListener(int position) {

            }
        });
    }
    private void getPDData(){
        if(NetUtil.checkNet(LiveTVviewingActivity.this)){
            addContrastChannelPresenter.getDisposeData();
        }else {
            Toast.makeText(LiveTVviewingActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getCCUrl() {

        if (GlobalParams.isTest){
                    return ConstantValues.URI_YONG_TEST+"channel/getAllChannelWithChannelGroup";

        }else{
            return ConstantValues.AUTH_SITE+"interface";

        }

    }

    @Override
    public int getCCCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getCCBody() {

        if (GlobalParams.isTest){
                    HashMap<String,String> map_yc = new HashMap<>();
                    map_yc.put("phoneNum","13370133060");
                    map_yc.put("appMenu", AppMenu.LIVEPROGRAMVIEW.toString());
                    JSONObject jsonObject = new JSONObject(map_yc);
            Log.e("TAG","测试发送的数据:::"+jsonObject.toString());
                    return jsonObject.toString();
        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","channel/getAllChannelWithChannelGroup");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","channel/getAllChannelWithChannelGroup");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("appMenu", "LIVEPROGRAMVIEW");
                jsonObject = new JSONObject(listHashMap);
            }finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }

    }
    @Override
    public void getCCDataFailureMsg(String msg) {
        Toast.makeText(LiveTVviewingActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCCData(Info info) {

        final List<AddContrastChannel> addContrastChannels = info.addContrastChannels;
        mlcode.add(addContrastChannels.get(0).mlist.get(0).channelCode);
        channelTVPop = new ChannelTVPop(LiveTVviewingActivity.this,R.layout.pop_channel_tv,channelTypeSelectorListener,addContrastChannels,tv_time.getText().toString(),"");
        channelTVPop.setNameText(name);
        srl_sx.setRefreshing(true);
        handler.postDelayed(runnable,0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalParams.count=0;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getListData();
        }
    };
}
