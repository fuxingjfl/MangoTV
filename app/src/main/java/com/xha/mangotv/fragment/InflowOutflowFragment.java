package com.xha.mangotv.fragment;

import android.annotation.SuppressLint;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.adapter.InflowflowAdapter;
import com.xha.mangotv.adapter.OutflowAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.InflowList;
import com.xha.mangotv.entity.OutflowList;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.entity.StbNumList;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.view.LineChartView;
import com.xha.mangotv.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 流入流出
 * Created by ysq on 2018/9/4.
 */

public class InflowOutflowFragment extends BaseFragment {

//    private LineChartView lineChartView;
//    private int[] dataArr = new int[]{200, 100, 300, -20, 50, -80, 200,
//    };
//
//    private String[] xlist = new String[]{"15:36","15:36","15:36","15:36","15:36","15:36","15:36"};

    private MyListView mylv_lr,mylv_lc;

    private InflowflowAdapter inflowOutflowAdapter1;

    private OutflowAdapter inflowOutflowAdapter2;

    private List<InflowList> inflowLists;
    private List<OutflowList> outflowLists;
    private TextView tv_lrlc_name,tv_lrlc_ssl;
    private String channelName,marketShare;
    private WebView wv_lrlc;
    private StbNumList stbNumList;
    private List<String> startTimeList= new ArrayList<>();
    private List<String> audienceRatingList = new ArrayList<>();
    private String inRating,outRating;
    private TextView tv_lr_time,tv_lr_bf,tv_lc_time,tv_lc_bf;
    private Handler handler = new Handler();
    private MyWebViewClient myWebViewClient;
    private RealTimeData realTimeData;

//    public InflowOutflowFragment(){
//
//    }
//    @SuppressLint({"NewApi", "ValidFragment"})
//    public InflowOutflowFragment(List<InflowList> inflowLists, List<OutflowList> outflowLists,String channelName,String marketShare,StbNumList stbNumList,String inRating,String outRating){
//
//        this.inflowLists=inflowLists;
//        this.outflowLists=outflowLists;
//        this.channelName=channelName;
//        this.marketShare=marketShare;
//        this.stbNumList=stbNumList;
//        this.inRating=inRating;
//        this.outRating=outRating;
//        Log.e("TAG","流入流出数量:::"+inflowLists.size()+",数量2==="+outflowLists.size());
//
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_inflow_outflow;
    }

    @Override
    protected void lazyLoad() {
        View contentView = getContentView();
        inflowLists = new ArrayList<>();
        outflowLists = new ArrayList<>();
        Bundle arguments = getArguments();
        realTimeData = (RealTimeData) arguments.getSerializable("RealTimeData");
        inflowLists.addAll(realTimeData.inflowLists);
        outflowLists.addAll(realTimeData.outflowLists);
        channelName=realTimeData.channelName;
        marketShare=realTimeData.marketShare;
        stbNumList=realTimeData.stbNumLists.get(0);
        inRating=realTimeData.inRating;
        outRating=realTimeData.outRating;

//        lineChartView=contentView.findViewById(R.id.line_chart_view);
        mylv_lr=contentView.findViewById(R.id.mylv_lr);
        mylv_lc=contentView.findViewById(R.id.mylv_lc);
        tv_lrlc_name=contentView.findViewById(R.id.tv_lrlc_name);
        tv_lrlc_ssl=contentView.findViewById(R.id.tv_lrlc_ssl);
        wv_lrlc=contentView.findViewById(R.id.wv_lrlc);
        tv_lr_time=contentView.findViewById(R.id.tv_lr_time);
        tv_lr_bf=contentView.findViewById(R.id.tv_lr_bf);
        tv_lc_time=contentView.findViewById(R.id.tv_lc_time);
        tv_lc_bf=contentView.findViewById(R.id.tv_lc_bf);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.SX_INOUT);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);

        tv_lr_time.setText("("+stbNumList.timeRange+")");
        tv_lc_time.setText("("+stbNumList.timeRange+")");
        tv_lr_bf.setText(inRating+"%");
        tv_lc_bf.setText(outRating+"%");
        tv_lrlc_name.setText(channelName);
        tv_lrlc_ssl.setText(marketShare+"%");
//        List<LineChartView.Data> datas = new ArrayList<>();
//        for (int value : dataArr) {
//            LineChartView.Data data = new LineChartView.Data(value);
//            datas.add(data);
//        }
//        lineChartView.setData(datas);
//        lineChartView.setXname(xlist);
//        lineChartView.setBezierLine(true);
//        lineChartView.setShowTable(true);
//        lineChartView.setRulerYSpace(70);
//        lineChartView.setStepSpace(40);

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
        startTimeList .addAll(stbNumList.startTimeList);
        audienceRatingList.addAll(stbNumList.audienceRatingList);
//        wv_qxt.loadUrl("file:///android_asset/echart/myechart.html");
        if(myWebViewClient==null){
            myWebViewClient=new MyWebViewClient();
            wv_lrlc.setWebViewClient(myWebViewClient);
        }

        inflowOutflowAdapter1 = new InflowflowAdapter(getActivity(),inflowLists);
        mylv_lr.setAdapter(inflowOutflowAdapter1);
        inflowOutflowAdapter2 = new OutflowAdapter(getActivity(),outflowLists);
        mylv_lc.setAdapter(inflowOutflowAdapter2);

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
        Log.e("TAG","realTimeData.channelName==="+realTimeData.channelName);
        wv_lrlc.loadUrl("javascript:setLegend('"+realTimeData.channelName+"');");
        wv_lrlc.loadUrl("javascript:setName('"+realTimeData.channelName+"');");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wv_lrlc != null) {
            wv_lrlc.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wv_lrlc.clearHistory();
            ((ViewGroup) wv_lrlc.getParent()).removeView(wv_lrlc);
            wv_lrlc.destroy();
            wv_lrlc = null;
        }
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConstantValues.SX_INOUT.equals(action)){
                RealTimeData realTimeData= (RealTimeData) intent.getSerializableExtra("RealTimeData");
                inflowLists.clear();
                if(inflowOutflowAdapter1!=null){
                    inflowOutflowAdapter1.notifyDataSetChanged();
                }
                outflowLists.clear();
                if(inflowOutflowAdapter2!=null){
                    inflowOutflowAdapter2.notifyDataSetChanged();
                }
                inflowLists.addAll(realTimeData.inflowLists);
                if(inflowOutflowAdapter1!=null){
                    inflowOutflowAdapter1.notifyDataSetChanged();
                }
                outflowLists.addAll(realTimeData.outflowLists);
                if(inflowOutflowAdapter2!=null){
                    inflowOutflowAdapter2.notifyDataSetChanged();
                }
                channelName=realTimeData.channelName;
                marketShare=realTimeData.marketShare;
                stbNumList=realTimeData.stbNumLists.get(0);
                inRating=realTimeData.inRating;
                outRating=realTimeData.outRating;
//                Log.e("TAG","新数据");
//                for (int i=0;i<inflowLists.size();i++){
//                    Log.e("TAG","数据_____________++++++"+inflowLists.get(i).rating);
//                }

                tv_lr_time.setText("("+stbNumList.timeRange+")");
                tv_lc_time.setText("("+stbNumList.timeRange+")");
                tv_lrlc_name.setText(channelName);
                tv_lr_bf.setText(inRating+"%");
                tv_lc_bf.setText(outRating+"%");
                tv_lrlc_ssl.setText(marketShare+"%");
                startTimeList.clear();
                audienceRatingList.clear();
                startTimeList .addAll(stbNumList.startTimeList);
                audienceRatingList.addAll(stbNumList.audienceRatingList);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(myWebViewClient==null){
                            wv_lrlc.loadUrl("file:///android_asset/index.html");
                            myWebViewClient=new MyWebViewClient();
                            wv_lrlc.setWebViewClient(myWebViewClient);
                        }else{
                            if(wv_lrlc!=null){
                                wv_lrlc.loadUrl("javascript:clearData();");
                                showLineChart(startTimeList,audienceRatingList);
                            }
                        }
                    }
                },1000);
            }
        }
    };

}
