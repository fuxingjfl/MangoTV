package com.xha.mangotv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xha.mangotv.Presenter.ChannelRatingsPresenter;
import com.xha.mangotv.Presenter.ChannelRatingsView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.ChannelRatingsAdapter;
import com.xha.mangotv.adapter.CommonViewHolder;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.ChannelGroup;
import com.xha.mangotv.entity.Index;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.CalendarPop;
import com.xha.mangotv.view.ChannelTypePop;
import com.xha.mangotv.view.HRecyclerView;
import com.xha.mangotv.view.RegionPop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/9/3.
 */

public class ChannelRatingsActivity extends BaseActivity implements ChannelRatingsView {
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private TextView title_content,tv_dq;
    private List<LiShiInfo> list;
    private HRecyclerView rv_cr;
    private LinearLayout ll_rl_sj;
    private CalendarPop calendarPop;
    private RegionPop regionPop;
    private ChannelTypePop channelTypePop;
    private ImageView iv_left,iv_right,iv_dq,iv_pdlx;
    private TextView tv_rl_content,tv_pdlx;
    private LinearLayout ll_sg,ll_dq,ll_pdlx;
    private List<Index> tablist;
    private SwipeRefreshLayout srl_sx;
    private ChannelRatingsPresenter channelRatingsPresenter;
    private ChannelRatingsAdapter adapter;
    private Handler handler;
    private ImageView iv_wsj;
    private String dayRange,timeRange="00:00:00-23:59:59",areaCode,channelGroupCode,areaName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_channel_ratings);
        rv_cr= (HRecyclerView) findViewById(R.id.rv_cr);
        rl_bt= (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui= (ImageView) findViewById(R.id.iv_fanhui);
        iv_left= (ImageView) findViewById(R.id.iv_left);
        iv_right= (ImageView) findViewById(R.id.iv_right);
        srl_sx = (SwipeRefreshLayout) findViewById(R.id.srl_sx);
        iv_wsj= (ImageView) findViewById(R.id.iv_wsj);
        title_content= (TextView) findViewById(R.id.title_content);
        ll_rl_sj= (LinearLayout) findViewById(R.id.ll_rl_sj);
        ll_sg= (LinearLayout) findViewById(R.id.ll_sg);
        ll_dq= (LinearLayout) findViewById(R.id.ll_dq);
        iv_dq= (ImageView) findViewById(R.id.iv_dq);
        tv_dq= (TextView) findViewById(R.id.tv_dq);
        iv_pdlx= (ImageView) findViewById(R.id.iv_pdlx);
        tv_pdlx= (TextView) findViewById(R.id.tv_pdlx);
        ll_pdlx= (LinearLayout) findViewById(R.id.ll_pdlx);
        tv_rl_content= (TextView) findViewById(R.id.tv_rl_content);
        srl_sx.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        handler = new Handler();
        channelRatingsPresenter = new ChannelRatingsPresenter(this);
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
//            Date supportEndDayofMonth = getSupportEndDayofMonth(year, month - 1);
//            String rq = sdf.format(supportEndDayofMonth);
            String[] split = rq.split("-");
            dayRange=rq+":"+rq;
            tv_rl_content.setText(split[1]+"-"+split[2]+":"+split[1]+"-"+split[2]);
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
            tv_rl_content.setText(y+"-"+r+":"+y+"-"+r);
        }
//        getAddressData();
//        getTabData();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        calendarPop = new CalendarPop(ChannelRatingsActivity.this,R.layout.pop_calendar,supportFragmentManager,listener,tv_rl_content.getText().toString());
        calendarPop.setText(tv_rl_content.getText().toString().trim());
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.fanhui);
        title_content.setText("频道收视排行");
        title_content.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        String string = PreUtil.getInstance().getString(ConstantValues.indexSet_pdssph);
        String areaList = PreUtil.getInstance().getString(ConstantValues.areaList_pdssph);
        String Cg = PreUtil.getInstance().getString(ConstantValues.channelGroup_pdssph);
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(string);
            tablist = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                String tab_name=jsonArray.getString(i);
                if("audienceRating".equals(tab_name)){
                    tablist.add(new Index("收视率",0));
                }else if ("marketShare".equals(tab_name)){
                    tablist.add(new Index("收视份额",1));
                }else if ("arrivalRate".equals(tab_name)){
                    tablist.add(new Index("到达率",3));
                }else if ("playTime".equals(tab_name)){
                    tablist.add(new Index("直播次数",5));
                }else if ("viewTime".equals(tab_name)){
                    tablist.add(new Index("直播收视时长",4));
                }else if ("stbNum".equals(tab_name)){
                    tablist.add(new Index("直播用户数",2));
                }
            }

            tablist.add(new Index("同时段排名",6));
            Collections.sort(tablist, new Comparator<Index>() {
                @Override
                public int compare(Index o1, Index o2) {
                    int i = o1.getScore() - o2.getScore();
                    return i;
                }
            });
//            String[] tab = {"收视率","收视份额","到达率","同时段排名","直播次数","直播收视时长","直播用户数"};
            String[] tab = new String[tablist.size()];
            for (int i=0;i<tablist.size();i++){
                tab[i]=tablist.get(i).name;
            }
            String[] mLeftTextList = new String[]{"频道名称"};

            rv_cr.setHeaderListData(tab,mLeftTextList);

            List<Address> addressmlist = new ArrayList<>();
            List<Address> newlist = new ArrayList<>();
            jsonArray = new JSONArray(areaList);

            for (int i=0;i<jsonArray.length();i++){
                String tab_name=jsonArray.getString(i);
                Log.e("TAG","tab_name==="+tab_name);
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
            areaName=addressmlist.get(0).areaName;
            regionPop = new RegionPop(ChannelRatingsActivity.this,R.layout.pop_region,regionlistener,addressmlist,tv_rl_content.getText().toString());
            regionPop.setOnDismissListener(onDismissListener);

            List<ChannelGroup> cg = new ArrayList<>();
            jsonArray = new JSONArray(Cg);
            int xz=0;
            for (int i=0;i<jsonArray.length();i++){
                String tab_name=jsonArray.getString(i);
                ChannelGroup channelGroup = new ChannelGroup();
                if("SNPD".equals(tab_name)){
                    channelGroup.channelGroupName="省内频道";
                }else if ("LBPD".equals(tab_name)){
                    channelGroup.channelGroupName="轮播频道";
                }else if ("YSPD".equals(tab_name)){
                    channelGroup.channelGroupName="央视频道";
                }else if ("SWPD".equals(tab_name)){
                    channelGroup.channelGroupName="省外频道";
                }else if ("FFPD".equals(tab_name)){
                    channelGroup.channelGroupName="付费频道";
                }else if ("QTPD".equals(tab_name)){
                    channelGroup.channelGroupName="其他频道";
                }else if ("-1".equals(tab_name)){
                    xz=i;
                    channelGroup.channelGroupName="全部频道";
                }
                channelGroup.channelGroupCode=tab_name;
                cg.add(channelGroup);
            }
            channelGroupCode=cg.get(xz).channelGroupCode;
            channelTypePop = new ChannelTypePop(ChannelRatingsActivity.this,R.layout.pop_channel_type,channelTypeSelectorListener,cg,tv_rl_content.getText().toString(),xz);
            channelTypePop.setOnDismissListener(onDismissListener);
            regionPop.setPDText(cg.get(xz).channelGroupName);
            calendarPop.setPDText(cg.get(xz).channelGroupName);
            channelTypePop.setPDText(cg.get(xz).channelGroupName);
            tv_pdlx.setText(cg.get(xz).channelGroupName);
            regionPop.setNameText(addressmlist.get(0).areaName);
            calendarPop.setNameText(addressmlist.get(0).areaName);
            channelTypePop.setNameText(addressmlist.get(0).areaName);
            tv_dq.setText(addressmlist.get(0).areaName);
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        iv_fanhui.setOnClickListener(onClickListener);
        ll_rl_sj.setOnClickListener(onClickListener);
        ll_dq.setOnClickListener(onClickListener);
        ll_pdlx.setOnClickListener(onClickListener);
        calendarPop.setOnDismissListener(onDismissListener);


    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getListData();
        }
    };


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_rl_sj:
                    iv_left.setImageResource(R.drawable.left_xz);
                    iv_right.setImageResource(R.drawable.right_xz);
                    tv_rl_content.setTextColor(getResources().getColor(R.color.persimmon));
                    if (calendarPop != null) {
                        if (calendarPop.isShowing()) {
                            calendarPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
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
                case R.id.ll_pdlx:
                    tv_pdlx.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_pdlx.setImageResource(R.drawable.xia_xz);

                    if (channelTypePop != null) {
                        if (channelTypePop.isShowing()) {
                            channelTypePop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            channelTypePop.showAsDropDown(rl_bt);
                        }
                    }

                    break;
                case R.id.iv_fanhui:
                    finish();
                    break;
            }
        }
    };

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub

            initState();
            setWindowTranslucence(1.0f);

        }
    };


    private CalendarPop.SelectorListener listener=new CalendarPop.SelectorListener() {

        @Override
        public void onSelectorDRListener(String ks, String sj) {


            String[] split = ks.split(":");

            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");


            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            calendarPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            regionPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            channelTypePop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);

            dayRange=ks;
            timeRange=sj;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            calendarPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onSelectorTimeListener(String day, String sj) {
            String[] split = day.split(":");

            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            calendarPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            regionPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            channelTypePop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            dayRange=day;
            timeRange=sj;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            calendarPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onCancelListener() {

            calendarPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };


    private RegionPop.RegionSelectorListener regionlistener=new RegionPop.RegionSelectorListener() {
        @Override
        public void onCancelListener() {
            regionPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onRegionSelectorListener(String data,String name) {

            calendarPop.setNameText(name);
            regionPop.setNameText(name);
            channelTypePop.setNameText(name);
            tv_dq.setText(name);
            areaCode=data;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            regionPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };

    private ChannelTypePop.ChannelTypeSelectorListener channelTypeSelectorListener = new ChannelTypePop.ChannelTypeSelectorListener() {
        @Override
        public void onCancelListener() {

            channelTypePop.dismiss();
            setWindowTranslucence(1.0f);
            initState();

        }

        @Override
        public void onRegionSelectorListener(String data,String name) {


            regionPop.setPDText(name);
            calendarPop.setPDText(name);
            channelTypePop.setPDText(name);
            tv_pdlx.setText(name);
            channelGroupCode=data;
            srl_sx.setRefreshing(true);
            handler.postDelayed(runnable,0);
            channelTypePop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };


    private void initState(){
        iv_left.setImageResource(R.drawable.left);
        iv_right.setImageResource(R.drawable.right);
        tv_rl_content.setTextColor(getResources().getColor(R.color.black));
        tv_dq.setTextColor(getResources().getColor(R.color.black));
        iv_dq.setImageResource(R.drawable.xia);
        tv_pdlx.setTextColor(getResources().getColor(R.color.black));
        iv_pdlx.setImageResource(R.drawable.xia);
    }


    /**
     *
     * 数据列表请求
     *
     */
    private void getListData(){
            if(NetUtil.checkNet(ChannelRatingsActivity.this)){
                channelRatingsPresenter.getDisposeData();
            }else {
                Toast.makeText(ChannelRatingsActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
            }

    }
    @Override
    public String getCRUrl() {

        if (GlobalParams.isTest){
            return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryLiveData";
        }else{
            return ConstantValues.AUTH_SITE+"interface";
        }
    }

    @Override
    public int getCRCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getCRBody() {

        if (GlobalParams.isTest){
                HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                hashMap.put("dayRange",dayRange);
                hashMap.put("timeRange",timeRange);
            hashMap.put("areaCode",areaCode);
            hashMap.put("channelGroupCode",channelGroupCode);
            JSONObject jo = new JSONObject(hashMap);
            Log.e("TAG","参数:::"+jo.toString());
            return jo.toString();
        }else{
            long time =System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryLiveData");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryLiveData");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("timeRange",timeRange);
                listHashMap.put("areaCode",areaCode);
                listHashMap.put("channelGroupCode",channelGroupCode);
                jsonObject= new JSONObject(listHashMap);
            }finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }



    }

    @Override
    public void getCRDataFailureMsg(String msg) {

        srl_sx.setRefreshing(false);
        srl_sx.setEnabled(false);

        Log.e("TAG","msg==="+msg);
//        getListData();
    }

    @Override
    public void getCRData(Info info) {
        srl_sx.setRefreshing(false);
        srl_sx.setEnabled(false);
        list.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }

        list.addAll(info.liShiInfos);

        Log.e("TAG","tablist=====长度:::"+tablist.size());

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
                setLayout(R.layout.item_content_ll);
            }
            rv_cr.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    public void setLayout(int layout){
        adapter = new ChannelRatingsAdapter(ChannelRatingsActivity.this, list,tablist,layout, new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(ChannelRatingsActivity.this,HistoryActivity.class);
                LiShiInfo liShiInfo = list.get(position);
                intent.putExtra("liShiInfo",liShiInfo);
                startActivity(intent);
            }
            @Override
            public void onItemLongClickListener(int position) {

            }
        });
    }
}
