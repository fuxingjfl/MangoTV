package com.xha.mangotv.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xha.mangotv.Presenter.CRTPresenter;
import com.xha.mangotv.Presenter.ChannelRatingsTrendView;
import com.xha.mangotv.Presenter.InflowOutflowLsPresenter;
import com.xha.mangotv.Presenter.InflowOutflowLsView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.InflowLsAdapter;
import com.xha.mangotv.adapter.OutflowLsAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.InflowOutflow;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LastCount;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.entity.NextCount;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.CalendarLsInOutPop;
import com.xha.mangotv.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class InflowOutflowLSFragment extends BaseFragment implements InflowOutflowLsView,ChannelRatingsTrendView {
    private MyListView mylv_lr,mylv_lc;
    private WebView wv_lrlc;
    private InflowLsAdapter inflowOutflowAdapter1;
    private OutflowLsAdapter inflowOutflowAdapter4;
    private List<LastCount> inflowLists;
    private List<NextCount> outflowLists;
    private CalendarLsInOutPop calendarLsInOutPop;
    private RelativeLayout rl_bt;
    private TextView tv_rl_content;
    private String dayRange,timeRange="00:00:00-23:59:59";
    private ImageView iv_time;
    private LinearLayout ll_time;
    private InflowOutflowLsPresenter inflowOutflowLsPresenter;
    private List<String> channelCodeList;
    private String areaCode;
    private TextView tv_lr_time,tv_lc_time,tv_lc_lv,tv_lr_lv;
    private List<String> startTimeList;
    private List<String> audienceRatingList;
    private String channelName,channelGroupCode;
    private MyWebViewClient myWebViewClient;
    private CRTPresenter crtPresenter;
    private String channelCode,dayInterval="NONE",timeInterval="TEN_MINUTE";
    @Override
    protected int setContentView() {
        return R.layout.fragment_inflow_outflow_ls;
    }
    @Override
    protected void lazyLoad() {
        View contentView = getContentView();
        Bundle arguments = getArguments();
        channelGroupCode=arguments.getString("channelGroupCode");
        areaCode=arguments.getString("areaCode");
        channelCode = arguments.getString("channelCode");
        channelName=arguments.getString("channelName");
        mylv_lr=contentView.findViewById(R.id.mylv_lr);
        mylv_lc=contentView.findViewById(R.id.mylv_lc);
        tv_lr_time=contentView.findViewById(R.id.tv_lr_time);
        tv_lc_time=contentView.findViewById(R.id.tv_lc_time);
        tv_lc_lv=contentView.findViewById(R.id.tv_lc_lv);
        tv_lr_lv=contentView.findViewById(R.id.tv_lr_lv);
        rl_bt=contentView.findViewById(R.id.rl_bt);
        wv_lrlc=contentView.findViewById(R.id.wv_lrlc);
        tv_rl_content=contentView.findViewById(R.id.tv_rl_content);
        iv_time=contentView.findViewById(R.id.iv_time);
        ll_time=contentView.findViewById(R.id.ll_time);
        startTimeList = new ArrayList<>();
        audienceRatingList = new ArrayList<>();
        channelCodeList = new ArrayList<>();
        channelCodeList.add(channelCode);
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
            tv_rl_content.setText(split[1]+"-"+split[2]+":"+split[1]+"-"+split[2]+" "+timeRange);
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
            tv_rl_content.setText(y+"-"+r+":"+y+"-"+r+" "+timeRange);
        }
        //进行webwiev的一堆设置
        //开启本地文件读取（默认为true，不设置也可以）
        wv_lrlc.getSettings().setAllowFileAccess(true);
        wv_lrlc.setHorizontalScrollBarEnabled(false);//水平不显示
        wv_lrlc.setVerticalScrollBarEnabled(false); //垂直不显示
        wv_lrlc.setVerticalScrollbarOverlay(true);
        wv_lrlc.getSettings().setDefaultTextEncodingName("utf-8");
        wv_lrlc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 21) {
            wv_lrlc.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //开启脚本支持
        wv_lrlc.getSettings().setJavaScriptEnabled(true);
        wv_lrlc.loadUrl("file:///android_asset/index.html");
        if(myWebViewClient==null){
            myWebViewClient=new MyWebViewClient();
            wv_lrlc.setWebViewClient(myWebViewClient);
        }
        inflowOutflowLsPresenter = new InflowOutflowLsPresenter(this);
        crtPresenter = new CRTPresenter(this);
        inflowLists = new ArrayList<>();
        outflowLists=new ArrayList<>();
        calendarLsInOutPop = new CalendarLsInOutPop(getActivity(),R.layout.pop_calendar_lsinout,listener,tv_rl_content.getText().toString());
        tv_rl_content.setOnClickListener(clicklistener);
        calendarLsInOutPop.setOnDismissListener(onDismissListener);
        getData();
        getListData();
    }
    private CalendarLsInOutPop.SelectorListener listener=new CalendarLsInOutPop.SelectorListener() {
        @Override
        public void onSelectorDRListener(String ks, String sj) {
            String[] split = ks.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]+" "+sj);
            calendarLsInOutPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]+" "+sj);
            dayRange=ks;
            timeRange=sj;
            calendarLsInOutPop.dismiss();
            setWindowTranslucence(1.0f);
            getData();
            getListData();
            initState();
        }
        @Override
        public void onSelectorTimeListener(String day, String sj) {
            String[] split = day.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]+" "+sj);
            calendarLsInOutPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]+" "+sj);
            dayRange=day;
            timeRange=sj;
            calendarLsInOutPop.dismiss();
            setWindowTranslucence(1.0f);
            getData();
            getListData();
            initState();
        }
        @Override
        public void onCancelListener() {
            calendarLsInOutPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };
    private View.OnClickListener clicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_rl_content:
                    iv_time.setImageResource(R.drawable.xia_xz);
                    tv_rl_content.setTextColor(getResources().getColor(R.color.persimmon));
                    if (calendarLsInOutPop != null) {
                        if (calendarLsInOutPop.isShowing()) {
                            calendarLsInOutPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarLsInOutPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
            }
        }
    };
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
        List<LiShiInfo> liShiInfos = new ArrayList<>();
        liShiInfos.addAll(info.liShiInfos);
        startTimeList.clear();
        audienceRatingList.clear();
        StringBuffer stringBuffer = new StringBuffer();
        boolean isdy=false;
            if (!isdy){
                for (int i=0;i<liShiInfos.size();i++){
                    stringBuffer.delete(0,stringBuffer.length());//删除所有的数据
                    LiShiInfo liShiInfo = liShiInfos.get(i);
                    if (channelCode.equals(liShiInfo.channelCode)){
                        stringBuffer.append(liShiInfo.startTime);
                        stringBuffer.delete(4,liShiInfo.startTime.length());
                        stringBuffer.insert(2,":");

                        isdy=true;
                        startTimeList.add(stringBuffer.toString());
                    }
                }
            }
            for (int i=0;i<liShiInfos.size();i++){
                stringBuffer.delete(0,stringBuffer.length());//删除所有的数据
                LiShiInfo liShiInfo = liShiInfos.get(i);
                if (channelCode.equals(liShiInfo.channelCode)){
                    audienceRatingList.add(liShiInfo.audienceRating);
                }
            }
        if (wv_lrlc!=null){
//                        wv_qxt.loadUrl("javascript:clearData();");
            wv_lrlc.reload();
        }
    }
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showLineChart(startTimeList,audienceRatingList);
        }
    }
    private void showLineChart(List<String> startTimeList,List<String> audienceRatingList){
        for (int i=0;i<startTimeList.size();i++){
            final int y=i;
            wv_lrlc.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");
            wv_lrlc.loadUrl("javascript:setData('"+audienceRatingList.get(y)+"');");
            wv_lrlc.loadUrl("javascript:setOption();");
        }
        wv_lrlc.loadUrl("javascript:setLegend('"+channelName+"');");
        wv_lrlc.loadUrl("javascript:setName('"+channelName+"');");
    }
    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);
    }
    private void initState(){
        iv_time.setImageResource(R.drawable.xia);
        tv_rl_content.setTextColor(getResources().getColor(R.color.black));
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub

            initState();
            setWindowTranslucence(1.0f);

        }
    };

    /**
     *
     * 数据列表请求
     *
     */
    private void getData(){
        if(NetUtil.checkNet(getActivity())){
            inflowOutflowLsPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getIOUrl() {

        if (GlobalParams.isTest){
            return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryAndflow";
        }else{
            return ConstantValues.AUTH_SITE+"interface";
        }

    }
    @Override
    public int getIOCode() {
        return HttpConstants.search_news01;
    }
    @Override
    public String getIOBody() {

        if (GlobalParams.isTest){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
            hashMap.put("dayRange",dayRange);
            hashMap.put("timeRange",timeRange);
            hashMap.put("areaCode",areaCode);
            hashMap.put("channelCodeList",channelCodeList);
            JSONObject jo = new JSONObject(hashMap);
            Log.e("TAG","参数:::"+jo.toString());
            return jo.toString();
        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryAndflow");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryAndflow");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("timeRange",timeRange);
                listHashMap.put("areaCode",areaCode);
                listHashMap.put("channelCodeList",channelCodeList);
                jsonObject= new JSONObject(listHashMap);
            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }

    }
    @Override
    public void getIODataFailureMsg(String msg) {


    }
    @Override
    public void getIOData(Info info) {
        inflowLists.clear();
        outflowLists.clear();
        InflowOutflow inflowOutflow = info.inflowOutflow;
        tv_lr_time.setText("("+timeRange+")");
        tv_lc_time.setText("("+timeRange+")");
        tv_lr_lv.setText(inflowOutflow.lastRete);
        tv_lc_lv.setText(inflowOutflow.nextRate);
        Log.e("TAG","数据::::::sssss="+inflowOutflow.lastCounts.size());
        if (inflowOutflow.lastCounts.size()>0){
            if (inflowOutflow.lastCounts.size()>5){
                for (int i=0;i<5;i++){

                    inflowLists.add(inflowOutflow.lastCounts.get(i));

                }
            }else{
                inflowLists.addAll(inflowOutflow.lastCounts);
            }
        }
        if (inflowOutflow.nextCounts.size()>0){
            if (inflowOutflow.nextCounts.size()>5){
                for (int i=0;i<5;i++){
                    outflowLists.add(inflowOutflow.nextCounts.get(i));
                }
            }else{
                outflowLists.addAll(inflowOutflow.nextCounts);
            }
        }
        if (inflowOutflowAdapter1==null){
            inflowOutflowAdapter1 = new InflowLsAdapter(getActivity(),inflowLists);
            mylv_lr.setAdapter(inflowOutflowAdapter1);
        }else{
            inflowOutflowAdapter1.notifyDataSetChanged();
        }
        if (inflowOutflowAdapter4==null){
            inflowOutflowAdapter4 = new OutflowLsAdapter(getActivity(),outflowLists);
            mylv_lc.setAdapter(inflowOutflowAdapter4);
        }else{
            inflowOutflowAdapter4.notifyDataSetChanged();
        }
    }

    public void setHide(){
        if (calendarLsInOutPop!=null){
            if (calendarLsInOutPop.isShowing()){
                calendarLsInOutPop.dismiss();
            }
        }
    }

}
