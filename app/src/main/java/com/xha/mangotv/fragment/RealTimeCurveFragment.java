package com.xha.mangotv.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.style.TextStyle;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.xha.mangotv.Presenter.DuiBiDataPresenter;
import com.xha.mangotv.Presenter.DuiBiDataView;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.AddContrastChannelActivity;
import com.xha.mangotv.adapter.RealTimeAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.Series;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ChoiceDelListener;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.MyThread;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.LineChartView;
import com.xha.mangotv.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Builder;
import lombok.Data;

/**
 *
 * 实时曲线
 * Created by ysq on 2018/9/4.
 */

public class RealTimeCurveFragment extends BaseFragment implements DuiBiDataView{
    private WebView wv_qxt;
    private MyListView mylv;
    private RealTimeAdapter realTimeAdapter;
    private List<DuiBiData> list;
    private TextView tv_san,tv_rs;
    private View view1,view2;
    private LinearLayout ll_pddb;
    private DuiBiDataPresenter duiBiDataPresenter;
    private String areaCode;
    private String liveRealCompareType="HOURS3";
    private String channelCode;
    private String channelName,marketShare;
    private RadioGroup rg_shuang;
    private RadioButton rb_ssl,rb_ssfe;
    private TextView tv_xq_name,tv_xq_day,tv_xq_ssl;
    private List<String> startTimeList= new ArrayList<>();
    private List<String> audienceRatingList = new ArrayList<>();
    private List<String> marketShareList = new ArrayList<>();
    private Info info;
    private MyWebViewClient myWebViewClient;
    private Handler handler;
    private List seriesList= new ArrayList<Series>();
    private boolean isqq;

    private boolean iszj=false;

    private List<String > pd_code_list;
    private TimerTask realTimetask;
    private Timer realTimetimer;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_real_time_curve;
    }

    @Override
    protected void lazyLoad() {

        View contentView = getContentView();
        Bundle arguments = getArguments();
        areaCode=arguments.getString("areaCode");
        channelCode=arguments.getString("channelCode");
        channelName=arguments.getString("channelName");
        marketShare=arguments.getString("marketShare");
        tv_san=contentView.findViewById(R.id.tv_one);
        tv_rs=contentView.findViewById(R.id.tv_two);
        view1=contentView.findViewById(R.id.view1);
        view2=contentView.findViewById(R.id.view2);
        mylv=contentView.findViewById(R.id.mylv);
        wv_qxt=contentView.findViewById(R.id.wv_qxt);
        ll_pddb=contentView.findViewById(R.id.ll_pddb);
        rg_shuang=contentView.findViewById(R.id.rg_shuang);
        tv_xq_name=contentView.findViewById(R.id.tv_xq_name);
        tv_xq_day=contentView.findViewById(R.id.tv_xq_day);
        tv_xq_ssl=contentView.findViewById(R.id.tv_xq_ssl);
        rb_ssl=contentView.findViewById(R.id.rb_ssl);
        rb_ssfe=contentView.findViewById(R.id.rb_ssfe);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.ADD_PD);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        tv_xq_name.setText(channelName);
        List<LineChartView.Data> datas = new ArrayList<>();
        handler = new Handler();
        list = new ArrayList<>();
        pd_code_list =new ArrayList<>();
        pd_code_list.add(channelCode);
        if(info!=null){
            for (int i=0;i<info.contrastChannelDatas.size();i++){
                ContrastChannelData contrastChannelData = info.contrastChannelDatas.get(i);
                pd_code_list.add(contrastChannelData.channelCode);
            }
        }
        duiBiDataPresenter = new DuiBiDataPresenter(this);
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
        stopRealTimeData();
        getRealTimeData();
        tv_san.setOnClickListener(onClickListener);
        tv_rs.setOnClickListener(onClickListener);
        ll_pddb.setOnClickListener(onClickListener);
        rg_shuang.setOnCheckedChangeListener(onCheckedChangeListener);
        mylv.setOnItemLongClickListener(OnItemLongClickListener);
    }


    @Override
    protected void stopLoad() {
        super.stopLoad();
        stopRealTimeData();
    }


    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                case R.id.rb_ssl:
//                    wv_qxt.loadUrl("file:///android_asset/index3.html");
                    wv_qxt.reload();
                    tv_xq_ssl.setText(list.get(0).audienceRating+"%");
                    break;
                case R.id.rb_ssfe:
                    wv_qxt.reload();
                    tv_xq_ssl.setText(list.get(0).marketShare+"%");
                    break;
            }

        }
    };

    /**
     * 开启定时任务轮询信息更新
     */
    private void getRealTimeData() {
        if (realTimetimer == null) {
            realTimetimer = new Timer();
        }
        if (realTimetask == null) {
            realTimetask = new TimerTask() {
                @Override
                public void run() {
                    Log.e("TAG","定时任务没有开启==="+isqq);
//                    if(isqq){w
                        getListData();
//                    }
                }
            };
        }
        if (realTimetask != null && realTimetimer != null) {
            realTimetimer.schedule(realTimetask, 0, 10 * 1000);
        }
    }

    //关闭实时获取的资源
    public void stopRealTimeData() {
        if (realTimetimer != null) {
            realTimetimer.cancel();
            realTimetimer = null;
        }
        if (realTimetask != null) {
            realTimetask.cancel();
            realTimetask = null;
        }
    }

    private AdapterView.OnItemLongClickListener OnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


            if(position!=0){
                realTimeAdapter.setPos(position);
                realTimeAdapter.notifyDataSetChanged();
            }
            return true;

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_one:
                    isqq=true;
                    tv_san.setTextColor(getActivity().getResources().getColor(R.color.persimmon));
                    view1.setVisibility(View.VISIBLE);
                    tv_rs.setTextColor(getActivity().getResources().getColor(R.color.text_hui));
                    view2.setVisibility(View.INVISIBLE);
                    liveRealCompareType="HOURS3";
                    iszj=true;

                    break;
                case R.id.tv_two:
                    isqq=true;
                    tv_san.setTextColor(getActivity().getResources().getColor(R.color.text_hui));
                    view1.setVisibility(View.INVISIBLE);
                    tv_rs.setTextColor(getActivity().getResources().getColor(R.color.persimmon));
                    view2.setVisibility(View.VISIBLE);
                    liveRealCompareType="HOURS24";
                    iszj=true;
                    break;
                case R.id.ll_pddb:
                    Intent intent = new Intent(getActivity(), AddContrastChannelActivity.class);
                    Info  info = new Info();
                    info.pd_code_list=new ArrayList<>();
                    info.pd_code_list.addAll(pd_code_list);
                    intent.putExtra("Info",info);
                    intent.putExtra("type","实时");
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public String getDBUrl() {
//        return ConstantValues.URI_YONG_TEST+"appReal/getChannelCompare";
        return ConstantValues.AUTH_SITE+"interface";

    }

    @Override
    public int getDBCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getDBBody() {
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("channelCodeList",pd_code_list);
//        map.put("liveRealCompareType",liveRealCompareType);
//        map.put("areaCode",areaCode);
//        map.put("phoneNum","13370133060");
//        JSONObject jo = new JSONObject(map);
//        Log.e("TAG","参数:::"+jo.toString());
//        return jo.toString();

        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","appReal/getChannelCompare");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("sign",sign);
            jsonObject.put("liveRealCompareType",liveRealCompareType);
            jsonObject.put("areaCode",areaCode);
            jsonObject.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));

            Map<String ,List<String>> stringListMap = new HashMap<>();
            stringListMap.put("channelCodeList",pd_code_list);
            JSONObject jsonObject1 = new JSONObject(stringListMap);
            jsonObject.put("channelCodeList",jsonObject1.getJSONArray("channelCodeList"));

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }

    }

    @Override
    public void getDBDataFailureMsg(String msg) {
        if(!ConstantValues.MSG_NAME.equals(msg)){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
        isqq=false;
    }

    @Override
    public void getDBData(Info info) {
        isqq=false;
        list.clear();
        startTimeList.clear();
        audienceRatingList.clear();
        marketShareList.clear();
        list.addAll(info.duiBiDatas);
        if (list.size()>0){
            final DuiBiData duiBiData = list.get(0);

            Calendar calendar= Calendar.getInstance();

            int month = calendar.get(Calendar.MONTH) + 1;

            //日
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            tv_xq_day.setText(month+"-"+day+" "+sdf.format(new Date()));
            if (rb_ssl.isChecked()){
                if(duiBiData.audienceRating==null||"null".equals(duiBiData.audienceRating)){
                    tv_xq_ssl.setText("0%");
                }else{
                    tv_xq_ssl.setText(duiBiData.audienceRating+"%");
                }
            }else{
                if(duiBiData.marketShare==null||"null".equals(duiBiData.marketShare)){
                    tv_xq_ssl.setText("0%");
                }else{
                    tv_xq_ssl.setText(duiBiData.marketShare+"%");
                }
            }
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myWebViewClient==null){

                    if (wv_qxt!=null){

                        wv_qxt.loadUrl("file:///android_asset/index3.html");
                        myWebViewClient=new MyWebViewClient();
                        wv_qxt.setWebViewClient(myWebViewClient);

                    }
                }else{
                    if(wv_qxt!=null){
                        if (iszj){
                            wv_qxt.reload();
                            iszj=false;
                        }else{
                            seriesList.clear();
                            if(rb_ssl.isChecked()){
                                wv_qxt.loadUrl("javascript:clearData();");
                                for (int j=0;j<list.size();j++){
                                    final DuiBiData duiBiData = list.get(j);
                                    startTimeList = new ArrayList<String>();
                                    audienceRatingList = new ArrayList<String>();
                                    startTimeList .addAll(duiBiData.startTimeList);
                                    audienceRatingList.addAll(duiBiData.audienceRatingList);
                                    if(j==0){
                                            for (int i=0;i<startTimeList.size();i++){
                                                final int y=i;
                                                wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");
                                            }
                                    }
                                    showLineChart(startTimeList,audienceRatingList,j);
                                }
                                //记录最后一个时间值
//                                line1=startTimeList.get(startTimeList.size()-1);
                            }else{
                                wv_qxt.loadUrl("javascript:clearData();");

                                for (int j=0;j<list.size();j++){
                                    final DuiBiData duiBiData = list.get(j);
                                    startTimeList = new ArrayList<String>();
                                    marketShareList = new ArrayList<String>();
                                    startTimeList .addAll(duiBiData.startTimeList);
                                    marketShareList.addAll(duiBiData.marketShareList);
                                    if(j==0){
                                        for (int i=0;i<startTimeList.size();i++){
                                            final int y=i;
                                            wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");
                                        }
                                    }
                                    showLineChart(startTimeList,marketShareList,j);
                                }
                                //记录最后一个时间值
//                                line1=startTimeList.get(startTimeList.size()-1);
                            }
                            Gson gson = new Gson();
                            String resJson = gson.toJson(seriesList);
                            Log.e("TAG","resJson==="+resJson);
                            wv_qxt.loadUrl("javascript:setSeriesData('"+resJson+"');");
                            wv_qxt.loadUrl("javascript:setOption();");
                            seriesList.clear();
                        }
                    }
                }
            }
        },0);
        if(realTimeAdapter==null){
            realTimeAdapter = new RealTimeAdapter(getActivity(),list,choiceDelListener);
            mylv.setAdapter(realTimeAdapter);
        }else{
            realTimeAdapter.setPos(-1);
            realTimeAdapter.notifyDataSetChanged();
        }
    }

    private void getListData(){
        if(NetUtil.checkNet(context)){
            duiBiDataPresenter.getDisposeData();
        }else {
            Toast.makeText(context, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(rb_ssl.isChecked()){
                        for (int j=0;j<list.size();j++){
                            final DuiBiData duiBiData = list.get(j);
                            startTimeList = new ArrayList<String>();
                            audienceRatingList = new ArrayList<String>();
                            startTimeList .addAll(duiBiData.startTimeList);
                            audienceRatingList.addAll(duiBiData.audienceRatingList);
                            if(j==0){
                                for (int i=0;i<startTimeList.size();i++){
                                    final int y=i;
                                    wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");
                                }
                            }
                            showLineChart(startTimeList,audienceRatingList,j);
                        }
                    }else{
                        for (int j=0;j<list.size();j++){
                            final DuiBiData duiBiData = list.get(j);
                            startTimeList = new ArrayList<String>();
                            marketShareList = new ArrayList<String>();

                            startTimeList .addAll(duiBiData.startTimeList);
                            marketShareList.addAll(duiBiData.marketShareList);

                            if(j==0){
                                for (int i=0;i<startTimeList.size();i++){

                                    final int y=i;

                                    wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");

                                }
                            }

                            showLineChart(startTimeList,marketShareList,j);

                        }


                    }
                    Gson gson = new Gson();
                    String resJson = gson.toJson(seriesList);
                    Log.e("TAG","resJson==="+resJson);
                    wv_qxt.loadUrl("javascript:setSeriesData('"+resJson+"');");
                    wv_qxt.loadUrl("javascript:setOption();");
                    seriesList.clear();
                    //记录最后一个时间值
//                    line1=startTimeList.get(startTimeList.size()-1);

                }
            });

        }
    }

    private void showLineChart(final List<String> startTimeList, final List<String> audienceRatingList, final int pos){

        for (int i=0;i<startTimeList.size();i++){
            final int y=i;
            wv_qxt.loadUrl("javascript:setData"+pos+"('"+audienceRatingList.get(y)+"');");
        }
        seriesList.add(new Series(list.get(pos).channelName,"line",audienceRatingList));
        Log.e("TAG","channelName=="+list.get(pos).channelName);
        wv_qxt.loadUrl("javascript:setLegend('"+list.get(pos).channelName+"');");
        wv_qxt.loadUrl("javascript:setName('"+pos+"');");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConstantValues.ADD_PD.equals(action)){
                isqq=true;
                info = (Info) intent.getSerializableExtra("pd");
//                if(isqq){
                    pd_code_list.clear();
                    pd_code_list.add(channelCode);
                    if(info!=null){
                        for (int i=0;i<info.contrastChannelDatas.size();i++){
                            ContrastChannelData contrastChannelData = info.contrastChannelDatas.get(i);
                            Log.e("TAG","实时书架========="+contrastChannelData.channelName);
                            pd_code_list.add(contrastChannelData.channelCode);
                        }
                    }
                wv_qxt.reload();
                stopRealTimeData();
                getRealTimeData();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        if (wv_qxt != null) {
            wv_qxt.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wv_qxt.clearHistory();
            ((ViewGroup) wv_qxt.getParent()).removeView(wv_qxt);
            wv_qxt.destroy();
            wv_qxt = null;
        }
        stopRealTimeData();
    }

    private ChoiceDelListener choiceDelListener = new ChoiceDelListener() {
        @Override
        public void OnChoiceDelListener(int pos) {
            pd_code_list.remove(pos);
            isqq=true;
            iszj=true;
            stopRealTimeData();
            getRealTimeData();
        }

        @Override
        public void OnRecoveryListener(int pos) {
            if(pos>0){
                realTimeAdapter.setPos(-1);
                realTimeAdapter.notifyDataSetChanged();
            }
        }
    };
}
