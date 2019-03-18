package com.xha.mangotv.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xha.mangotv.Presenter.AddressPresenter;
import com.xha.mangotv.Presenter.AddressView;
import com.xha.mangotv.Presenter.CRTPresenter;
import com.xha.mangotv.Presenter.ChannelRatingsTrendView;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.AddContrastChannelActivity;
import com.xha.mangotv.activity.ChannelRatingsActivity;
import com.xha.mangotv.adapter.RealTimeAdapter;
import com.xha.mangotv.adapter.ViewingTrendAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.entity.LishiPD;
import com.xha.mangotv.entity.Series;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ChoiceDelListener;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.util.StrKit;
import com.xha.mangotv.view.CalendarLSPop;
import com.xha.mangotv.view.CalendarPop;
import com.xha.mangotv.view.IndexPop;
import com.xha.mangotv.view.LineChartView;
import com.xha.mangotv.view.MyListView;
import com.xha.mangotv.view.RegionLSPop;
import com.xha.mangotv.view.RegionPop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 频道收视走势
 * Created by ysq on 2018/9/5.
 */

public class ViewingTrendFragment extends BaseFragment implements ChannelRatingsTrendView{

    private MyListView mylv;
    private ViewingTrendAdapter viewingTrendAdapter;
    private List<LiShiInfo> liShiInfos;
    private CalendarLSPop calendarLSPop;
    private RegionLSPop regionLSPop;
    private LinearLayout ll_time,ll_dq,ll_zb;
    private TextView tv_tiem,tv_dq,tv_zb;
    private ImageView iv_time,iv_dq,iv_zb;
    private RelativeLayout rl_bt;
    private String channelGroupCode,areaCode,channelCode,channelName;
    private String dayRange,timeRange="00:00:00-23:59:59",dayInterval="NONE",timeInterval="TEN_MINUTE";
    private Handler handler;
    private WebView wv_qxt;
    private List<Series> seriesList= new ArrayList<Series>();
    private CRTPresenter crtPresenter;
    private List<String> startTimeList;
    private List<String> audienceRatingList = new ArrayList<>();
    private List<String> channelCodeList = new ArrayList<>();
    private MyWebViewClient myWebViewClient;
    private LinearLayout ll_sj_content;
    private Info info;
    private HashMap<String,List<String>> hashMap;
    private List<LishiPD> lishiPDs;
    private List<String> keys;
    private LinearLayout ll_pddb;
    private IndexPop indexPop;
    private String index;
    @Override
    protected int setContentView() {
        return R.layout.fragment_viewing_trend;
    }

    @Override
    protected void lazyLoad() {
        View contentView=getContentView();
        Bundle arguments = getArguments();
        channelGroupCode=arguments.getString("channelGroupCode");
        channelCode=arguments.getString("channelCode");
        channelName=arguments.getString("channelName");
//        lineChartView=contentView.findViewById(R.id.line_chart_view);
        handler = new Handler();
        mylv=contentView.findViewById(R.id.mylv);
        wv_qxt=contentView.findViewById(R.id.wv_qxt);
        ll_time=contentView.findViewById(R.id.ll_time);
        tv_tiem=contentView.findViewById(R.id.tv_tiem);
        iv_time=contentView.findViewById(R.id.iv_time);
        tv_zb=contentView.findViewById(R.id.tv_zb);
        iv_zb=contentView.findViewById(R.id.iv_zb);
        ll_zb=contentView.findViewById(R.id.ll_zb);
        ll_sj_content=contentView.findViewById(R.id.ll_sj_content);
        ll_pddb=contentView.findViewById(R.id.ll_pddb);
        ll_dq=contentView.findViewById(R.id.ll_dq);
        tv_dq=contentView.findViewById(R.id.tv_dq);
        iv_dq=contentView.findViewById(R.id.iv_dq);
        rl_bt=contentView.findViewById(R.id.rl_bt);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.ADD_PD);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        hashMap = new HashMap<>();

        lishiPDs = new ArrayList<>();
        keys = new ArrayList<>();
        LishiPD lishiPD = new LishiPD();
        lishiPD.name=channelName;
        lishiPDs.add(lishiPD);
        channelCodeList.add(channelCode);
        keys.add(channelCode);
        hashMap.put(channelCode,new ArrayList<String>());
        if(info!=null){
            for (int i=0;i<info.contrastChannelDatas.size();i++){
                ContrastChannelData contrastChannelData = info.contrastChannelDatas.get(i);
                channelCodeList.add(contrastChannelData.channelCode);
                keys.add(contrastChannelData.channelCode);
                hashMap.put(contrastChannelData.channelCode,new ArrayList<String>());
                LishiPD lishiPD1 = new LishiPD();
                lishiPD1.name=contrastChannelData.channelName;
                lishiPDs.add(lishiPD1);
            }
        }

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
            tv_tiem.setText(split[1]+"-"+split[2]+":"+split[1]+"-"+split[2]);

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
            tv_tiem.setText(y+"-"+r+":"+y+"-"+r);
        }
        crtPresenter = new CRTPresenter(this);
//        getAddressData();
        liShiInfos = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            wv_qxt.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }else{
            try {
                Class<?> clazz = wv_qxt.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv_qxt.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //进行webwiev的一堆设置
        //开启本地文件读取（默认为true，不设置也可以）
        wv_qxt.getSettings().setAllowFileAccess(true);
        wv_qxt.setHorizontalScrollBarEnabled(false);//水平不显示
        wv_qxt.setVerticalScrollBarEnabled(false); //垂直不显示
        wv_qxt.setVerticalScrollbarOverlay(true);
        wv_qxt.getSettings().setDefaultTextEncodingName("utf-8");
        wv_qxt.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 21) {
            wv_qxt.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //开启脚本支持
        wv_qxt.getSettings().setJavaScriptEnabled(true);
        calendarLSPop = new CalendarLSPop(getActivity(),R.layout.pop_calendar_ls,selectorListener,tv_tiem.getText().toString());
        try {
            JSONArray ja = new JSONArray(PreUtil.getInstance().getString(ConstantValues.indexSet_pdsszs));

            List<String> mlist = new ArrayList<>();

            for (int i=0;i<ja.length();i++){

                mlist.add(ja.getString(i));

            }
            index=mlist.get(0);
            indexPop = new IndexPop(getActivity(),R.layout.pop_index,indexSelectorListener,mlist,tv_tiem.getText().toString());



            String areaList = PreUtil.getInstance().getString(ConstantValues.areaList_pdsszs);
            List<Address> addressmlist = new ArrayList<>();

            ja = new JSONArray(areaList);
            for (int i=0;i<ja.length();i++){
                String tab_name=ja.getString(i);
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
            regionLSPop = new RegionLSPop(getActivity(),R.layout.pop_region_ls,regionSelectorListener,addressmlist,"指标",tv_tiem.getText().toString());
            regionLSPop.setOnDismissListener(onDismissListener);

            String name="";
            if ("audienceRating".equals(index)){
                name="收视率";


            }else if ("marketShare".equals(index)){
                name="收视份额";

            }else if ("stbNum".equals(index)){
                name="用户数";

            }else if ("arrivalRate".equals(index)){
                name="到达率";

            }else if ("viewTime".equals(index)){
                name="直播时长";

            }else if ("playTime".equals(index)){
                name="直播次数";

            }

            calendarLSPop.setZBText(name);
            indexPop.setZBText(name);
            regionLSPop.setNameText(name);
            tv_zb.setText(name);
            calendarLSPop.setNameText(addressmlist.get(0).areaName);
            regionLSPop.setNameText(addressmlist.get(0).areaName);
            indexPop.setNameText(addressmlist.get(0).areaName);
            tv_dq.setText(addressmlist.get(0).areaName);

            getListData();

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        realTimeAdapter = new RealTimeAdapter(getActivity(),list,choiceDelListener);
//        mylv.setAdapter(realTimeAdapter);
        ll_time.setOnClickListener(onClickListener);
        ll_dq.setOnClickListener(onClickListener);
        ll_pddb.setOnClickListener(onClickListener);
        ll_zb.setOnClickListener(onClickListener);
        calendarLSPop.setOnDismissListener(onDismissListener);
        indexPop.setOnDismissListener(onDismissListener);
        mylv.setOnItemLongClickListener(OnItemLongClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_time:
                    tv_tiem.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_time.setImageResource(R.drawable.xia_xz);

                    if (calendarLSPop != null) {
                        if (calendarLSPop.isShowing()) {
                            calendarLSPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarLSPop.showAsDropDown(rl_bt);
                        }
                    }

                    break;
                case R.id.ll_dq:

                    tv_dq.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_dq.setImageResource(R.drawable.xia_xz);
                    if (regionLSPop != null) {
                        if (regionLSPop.isShowing()) {
                            regionLSPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            regionLSPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
                case R.id.ll_pddb:

                    Intent intent = new Intent(getActivity(), AddContrastChannelActivity.class);
                    Info  info = new Info();
                    info.pd_code_list=new ArrayList<>();
                    info.pd_code_list.addAll(channelCodeList);
                    intent.putExtra("Info",info);
                    intent.putExtra("type","频道收视");
                    startActivity(intent);

                    break;
                case R.id.ll_zb:
                    tv_zb.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_zb.setImageResource(R.drawable.xia_xz);
                    if (indexPop != null) {
                        if (indexPop.isShowing()) {
                            indexPop.dismiss();
                        } else {
                            setWindowTranslucence(0.3);
                            indexPop.showAsDropDown(rl_bt);
                        }
                    }
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
    private CalendarLSPop.SelectorListener selectorListener = new CalendarLSPop.SelectorListener() {


        @Override
        public void onSelectorDRListener(String ks, String sj) {
            Log.e("TAG","day===="+ks);
            Iterator it = hashMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (java.util.Map.Entry)it.next();
                String key = (String) entry.getKey();
                ((List<String>) entry.getValue()).clear();

            }
            Log.e("TAG","日期:::"+ks);
            String[] split = ks.split(":");

            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_tiem.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            calendarLSPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            regionLSPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            indexPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            dayRange=ks;
            timeRange=sj;
            getListData();
            calendarLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onSelectorTimeListener(String day, String sj) {
            Log.e("TAG","day===="+day);
            Iterator it = hashMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (java.util.Map.Entry)it.next();
                String key = (String) entry.getKey();
                ((List<String>) entry.getValue()).clear();

            }
            String[] split = day.split(":");

            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_tiem.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            calendarLSPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            regionLSPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            indexPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            dayRange=day;
            timeRange=sj;
            getListData();
            calendarLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onCancelListener() {
            calendarLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };

    private RegionLSPop.RegionSelectorListener regionSelectorListener = new RegionLSPop.RegionSelectorListener() {
        @Override
        public void onCancelListener() {
            regionLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onRegionSelectorListener(String data,String name) {
            Iterator it = hashMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (java.util.Map.Entry)it.next();
                String key = (String) entry.getKey();
                ((List<String>) entry.getValue()).clear();

            }
            calendarLSPop.setNameText(name);
            regionLSPop.setNameText(name);
            indexPop.setNameText(name);
            tv_dq.setText(name);
            areaCode=data;

            getListData();

            regionLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };
    private void initState(){
        iv_time.setImageResource(R.drawable.xia);
        tv_tiem.setTextColor(getResources().getColor(R.color.black));
        iv_dq.setImageResource(R.drawable.xia);
        tv_dq.setTextColor(getResources().getColor(R.color.black));
        tv_zb.setTextColor(getResources().getColor(R.color.black));
        iv_zb.setImageResource(R.drawable.xia);

    }

    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }

    /**
     *
     * 数据列表请求
     *
     */
    private void getListData(){
        if(NetUtil.checkNet(getActivity())){
            crtPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getCRTUrl() {
        if (GlobalParams.isTest){
            return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryLiveDataTrend";

        }else{
            return ConstantValues.AUTH_SITE+"interface";

        }
    }
    @Override
    public int getCRTCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getCRTBody() {

        if (GlobalParams.isTest){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
            hashMap.put("dayRange",dayRange);

            hashMap.put("timeRange",timeRange);

            hashMap.put("areaCode",areaCode);
            hashMap.put("channelGroupCode",channelGroupCode);
            hashMap.put("channelCodeList",channelCodeList);
            hashMap.put("dayInterval",dayInterval);
            hashMap.put("timeInterval",timeInterval);

            JSONObject jo = new JSONObject(hashMap);

            Log.e("TAG","频道走势参数:::"+jo.toString());
            return jo.toString();
        }else{
            long time=System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryLiveDataTrend");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {

                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryLiveDataTrend");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("timeRange",timeRange);
                listHashMap.put("areaCode",areaCode);
                listHashMap.put("channelGroupCode",channelGroupCode);
                listHashMap.put("channelCodeList",channelCodeList);
                listHashMap.put("dayInterval",dayInterval);
                listHashMap.put("timeInterval",timeInterval);
                jsonObject= new JSONObject(listHashMap);
            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }

    }

    @Override
    public void getCRTDataFailureMsg(String msg) {

    }

    @Override
    public void getCRTData(Info info) {

        liShiInfos.clear();
        liShiInfos.addAll(info.liShiInfos);
        startTimeList=new ArrayList<>();
        audienceRatingList.clear();

        Iterator it = hashMap.entrySet().iterator();
//        while (i.hasNext()) {
//            Object obj = i.next();
//            String key = obj.toString();
//        }
// or
        StringBuffer stringBuffer = new StringBuffer();
        boolean isdy=false;
        while (it.hasNext()) {
            Map.Entry entry = (java.util.Map.Entry)it.next();
            String key = (String) entry.getKey();
            List<String> value = (List<String>) entry.getValue();
            if (!isdy){
                for (int i=0;i<liShiInfos.size();i++){
                    stringBuffer.delete(0,stringBuffer.length());//删除所有的数据
                    LiShiInfo liShiInfo = liShiInfos.get(i);
                    if (key.equals(liShiInfo.channelCode)){
                        stringBuffer.append(liShiInfo.startTime);
                        stringBuffer.delete(4,liShiInfo.startTime.length());
                        stringBuffer.insert(2,":");
                        isdy=true;
                        startTimeList.add(stringBuffer.toString());
                    }
                }
            }
            Log.e("TAG","总数据:::==="+liShiInfos.size()+"key数量:::"+keys.size());
                for (int i=0;i<liShiInfos.size();i++){
                    stringBuffer.delete(0,stringBuffer.length());//删除所有的数据
                    LiShiInfo liShiInfo = liShiInfos.get(i);
                    if (key.equals(liShiInfo.channelCode)){
                        if ("audienceRating".equals(index)){
                            Log.i("TAG","key=="+key+",liShiInfo.channelCode=="+liShiInfo.channelCode);
                            value.add(liShiInfo.audienceRating);
                        }else if ("marketShare".equals(index)){
                            value.add(liShiInfo.marketShare);
                        }else if ("stbNum".equals(index)){
                            value.add(liShiInfo.stbNum);
                        }else if ("arrivalRate".equals(index)){
                            value.add(liShiInfo.arrivalRate);
                        }else if ("viewTime".equals(index)){
                            value.add(liShiInfo.viewTime);
                        }else if ("playTime".equals(index)){
                            value.add(liShiInfo.playTime);
                        }
                    }
                }
        }
//        audienceRatingList.addAll(hashMap.get(keys.get(0)));
        if(myWebViewClient==null){
            if (wv_qxt!=null){
                wv_qxt.loadUrl("file:///android_asset/index3.html");
                for (int i=0;i<hashMap.size();i++){
                    showLineChart(startTimeList,hashMap.get(keys.get(i)),i);
                }
                myWebViewClient=new MyWebViewClient();
                wv_qxt.setWebViewClient(myWebViewClient);
            }
        }else{
            if (wv_qxt!=null){
                for (int i=0;i<hashMap.size();i++){
                    showLineChart(startTimeList,hashMap.get(keys.get(i)),i);
                }
                    wv_qxt.reload();
            }
        }
        if(viewingTrendAdapter==null){
            viewingTrendAdapter = new ViewingTrendAdapter(getActivity(),lishiPDs,choiceDelListener);
            mylv.setAdapter(viewingTrendAdapter);
        }else{
            viewingTrendAdapter.setPos(-1);
            viewingTrendAdapter.notifyDataSetChanged();
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            wv_qxt.loadUrl("javascript:clearData();");

            for (int i=0;i<startTimeList.size();i++){
                final int y=i;
                wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");
            }

            Gson gson = new Gson();
            Log.e("TAG","长度999999999999999999::::"+seriesList.get(0).data.size());
            String resJson = gson.toJson(seriesList);
            Log.e("TAG","resJson==="+resJson);
            wv_qxt.loadUrl("javascript:setSeriesData('"+resJson+"');");
            wv_qxt.loadUrl("javascript:setOption();");
            seriesList.clear();
        }
    }

    private void showLineChart(final List<String> startTimeList, final List<String> audienceRatingList, final int pos){
        if (audienceRatingList.size()==0){
            return;
        }
        Log.e("TAG","888888888888数据============"+audienceRatingList.size());

        //防止webview上次没加载完会有重复数据
        List<String> mlist = new ArrayList<>();
        for (int i=0;i<startTimeList.size();i++){
            mlist.add(audienceRatingList.get(i));
        }
        seriesList.add(new Series(lishiPDs.get(pos).name,"line",mlist));
        wv_qxt.loadUrl("javascript:setLegend('"+lishiPDs.get(pos).name+"');");
        wv_qxt.loadUrl("javascript:setName('"+pos+"');");
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (ConstantValues.ADD_PD.equals(action)){

                info = (Info) intent.getSerializableExtra("pd");
//                if(isqq){
                channelCodeList.clear();
                liShiInfos.clear();
                lishiPDs.clear();
                hashMap.clear();
                keys.clear();
                hashMap.put(channelCode,new ArrayList<String>());
                channelCodeList.add(channelCode);
                keys.add(channelCode);
                LishiPD lishiPD = new LishiPD();
                lishiPD.name=channelName;
                lishiPDs.add(lishiPD);
                if(info!=null){
                    for (int i=0;i<info.contrastChannelDatas.size();i++){
                        ContrastChannelData contrastChannelData = info.contrastChannelDatas.get(i);
                        channelCodeList.add(contrastChannelData.channelCode);
                        keys.add(contrastChannelData.channelCode);
                        hashMap.put(contrastChannelData.channelCode,new ArrayList<String>());
                        LishiPD lishiPD1 = new LishiPD();
                        lishiPD1.name=contrastChannelData.channelName;
                        lishiPDs.add(lishiPD1);
                    }
                }
                Log.e("TAG","keys数量:::::"+keys.size());
                getListData();
            }
        }
    };

    private ChoiceDelListener choiceDelListener = new ChoiceDelListener() {
        @Override
        public void OnChoiceDelListener(int pos) {
            hashMap.remove(keys.get(pos));
            channelCodeList.remove(pos);
            lishiPDs.remove(pos);
            keys.remove(pos);
            //清除已有线的数据
            for (int i=0;i<hashMap.size();i++){

                hashMap.get(keys.get(i)).clear();

            }
            getListData();
        }
        @Override
        public void OnRecoveryListener(int pos) {
            if(pos>0){
                viewingTrendAdapter.setPos(-1);
                viewingTrendAdapter.notifyDataSetChanged();
            }
        }
    };

    private AdapterView.OnItemLongClickListener OnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


            if(position!=0){
                viewingTrendAdapter.setPos(position);
                viewingTrendAdapter.notifyDataSetChanged();
            }
            return true;

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private IndexPop.IndexSelectorListener indexSelectorListener = new IndexPop.IndexSelectorListener() {
        @Override
        public void onCancelListener() {

            indexPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onIndexSelectorListener(String data, String name) {

            calendarLSPop.setZBText(name);
            indexPop.setZBText(name);
            regionLSPop.setNameText(name);
            tv_zb.setText(name);

            index=data;

            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (java.util.Map.Entry)it.next();
                String key = (String) entry.getKey();
                ((List<String>) entry.getValue()).clear();
            }

            getListData();

            indexPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(broadcastReceiver);
        if (wv_qxt != null) {
            wv_qxt.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wv_qxt.clearHistory();
            ((ViewGroup) wv_qxt.getParent()).removeView(wv_qxt);
            wv_qxt.destroy();
            wv_qxt = null;
        }
    }

    //隐藏pop
    public void setHide(){

        if (calendarLSPop!=null){

            if (calendarLSPop.isShowing()){
                calendarLSPop.dismiss();
            }

        }
        if (regionLSPop!=null){

            if (regionLSPop.isShowing()){
                regionLSPop.dismiss();
            }

        }

        if (indexPop!=null){

            if (indexPop.isShowing()){
                indexPop.dismiss();
            }

        }

    }


}
