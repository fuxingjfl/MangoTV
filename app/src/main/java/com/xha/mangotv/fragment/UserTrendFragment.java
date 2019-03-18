package com.xha.mangotv.fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xha.mangotv.Presenter.AddContrastChannelPresenter;
import com.xha.mangotv.Presenter.AddContrastChannelView;
import com.xha.mangotv.Presenter.AddressPresenter;
import com.xha.mangotv.Presenter.AddressView;
import com.xha.mangotv.Presenter.UserTrendPresenter;
import com.xha.mangotv.Presenter.UserTrendView;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.LiveTVviewingActivity;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Dian;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.UserTrend;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.AppMenu;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.CalendarUTPop;
import com.xha.mangotv.view.ChannelTVPop;
import com.xha.mangotv.view.ChannelUserTrendPop;
import com.xha.mangotv.view.LineChartView;
import com.xha.mangotv.view.RegionLSPop;
import com.xha.mangotv.view.RegionTVPop;

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
import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class UserTrendFragment extends BaseFragment implements AddContrastChannelView,UserTrendView{

    private RegionLSPop regionLSPop;
    private LinearLayout ll_dq;
    private TextView tv_dq;
    private ImageView iv_dq;
    private RelativeLayout rl_bt;
    private String areaCode,dayRange;
    private TextView tv_tiem;
    private ImageView iv_time;
    private AddContrastChannelPresenter addContrastChannelPresenter;
    private ChannelUserTrendPop channelUserTrendPop;
    private CalendarUTPop calendarUTPop;
    private LinearLayout ll_pddb,ll_time;
    private TextView tv_pddb;
    private ImageView iv_pddb;
    private  List<String> mlcode = new ArrayList<>();
    private UserTrendPresenter userTrendPresenter;
    private String time;
    private String channelCode;
    private WebView wv_qxt;
    private MyWebViewClient myWebViewClient;
    private List<UserTrend> list;
    private Handler handler;
    private List<String> startTimeList= new ArrayList<>();
    private List<String> audienceRatingList = new ArrayList<>();
    private boolean isdx=true;
    private String name;
    @Override
    protected int setContentView() {
        return R.layout.fragment_user_trend;
    }

    @Override
    protected void lazyLoad() {
        View contentView=getContentView();
        Bundle arguments = getArguments();
        channelCode= arguments.getString("channelCode");
        mlcode.add(channelCode);
        ll_dq=contentView.findViewById(R.id.ll_dq);
        tv_dq=contentView.findViewById(R.id.tv_dq);
        iv_dq=contentView.findViewById(R.id.iv_dq);
        rl_bt=contentView.findViewById(R.id.rl_bt);
        wv_qxt=contentView.findViewById(R.id.wv_qxt);
        tv_tiem=contentView.findViewById(R.id.tv_tiem);
        ll_pddb=contentView.findViewById(R.id.ll_pddb);
        tv_pddb=contentView.findViewById(R.id.tv_pddb);
        iv_pddb=contentView.findViewById(R.id.iv_pddb);
        ll_time=contentView.findViewById(R.id.ll_time);
        tv_tiem=contentView.findViewById(R.id.tv_tiem);
        iv_time=contentView.findViewById(R.id.iv_time);
        list = new ArrayList<>();
        handler = new Handler();
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
        addContrastChannelPresenter = new AddContrastChannelPresenter(this);
        userTrendPresenter = new UserTrendPresenter(this);
        calendarUTPop = new CalendarUTPop(getActivity(),R.layout.pop_calendar_ut,selectorListener,tv_tiem.getText().toString());
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
//        getAddressData();
        getPDData();
        String areaList = PreUtil.getInstance().getString(ConstantValues.areaList_pdssph);
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
            name=addressmlist.get(0).areaName;
            regionLSPop = new RegionLSPop(getActivity(),R.layout.pop_region_ls,regionSelectorListener,addressmlist,"频道对比",tv_tiem.getText().toString());
            regionLSPop.setOnDismissListener(onDismissListener);
            calendarUTPop.setOnDismissListener(onDismissListener);
            calendarUTPop.setNameText(addressmlist.get(0).areaName);
            regionLSPop.setNameText(addressmlist.get(0).areaName);
            tv_dq.setText(addressmlist.get(0).areaName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
        ll_dq.setOnClickListener(onClickListener);
        ll_pddb.setOnClickListener(onClickListener);
        ll_time.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
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
                    if (isdx){
                        tv_pddb.setTextColor(getResources().getColor(R.color.persimmon));
                        iv_pddb.setImageResource(R.drawable.xia_xz);
                        if (channelUserTrendPop != null) {
                            if (channelUserTrendPop.isShowing()) {
                                channelUserTrendPop.dismiss();
                                // StateChanged();
                            } else {
                                setWindowTranslucence(0.3);
                                channelUserTrendPop.showAsDropDown(rl_bt);
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(),"多日无法选择多频道",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ll_time:
                    tv_tiem.setTextColor(getResources().getColor(R.color.persimmon));
                    iv_time.setImageResource(R.drawable.xia_xz);
                    if (calendarUTPop != null) {
                        if (calendarUTPop.isShowing()) {
                            calendarUTPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarUTPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
            }
        }
    };

    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }

    private RegionLSPop.RegionSelectorListener regionSelectorListener = new RegionLSPop.RegionSelectorListener() {
        @Override
        public void onCancelListener() {
            regionLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onRegionSelectorListener(String data,String name) {

            calendarUTPop.setNameText(name);
            regionLSPop.setNameText(name);
            channelUserTrendPop.setNameText(name);
            tv_dq.setText(name);
            areaCode=data;
            getListData();
            regionLSPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };

    private void initState(){
        iv_dq.setImageResource(R.drawable.xia);
        tv_dq.setTextColor(getResources().getColor(R.color.black));
        iv_pddb.setImageResource(R.drawable.xia);
        tv_pddb.setTextColor(getResources().getColor(R.color.black));
        iv_time.setImageResource(R.drawable.xia);
        tv_tiem.setTextColor(getResources().getColor(R.color.black));
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub

            initState();
            setWindowTranslucence(1.0f);

        }
    };
    private void getPDData(){
        if(NetUtil.checkNet(getActivity())){
            addContrastChannelPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
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
            map_yc.put("appMenu", "CHANNELRANKING");
            JSONObject jsonObject = new JSONObject(map_yc);
            return jsonObject.toString();
        }else{
            long time=System.currentTimeMillis();
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
                listHashMap.put("appMenu", "CHANNELRANKING");
                jsonObject= new JSONObject(listHashMap);
            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }

    }

    @Override
    public void getCCDataFailureMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCCData(Info info) {

        final List<AddContrastChannel> addContrastChannels = info.addContrastChannels;
        channelUserTrendPop = new ChannelUserTrendPop(getActivity(),R.layout.pop_channel_ut,channelUserTrendSelectorListener,addContrastChannels,tv_tiem.getText().toString(),channelCode);
        channelUserTrendPop.setNameText(name);
        channelUserTrendPop.setOnDismissListener(onDismissListener);
        getListData();

    }

    private ChannelUserTrendPop.ChannelUserTrendSelectorListener channelUserTrendSelectorListener = new ChannelUserTrendPop.ChannelUserTrendSelectorListener() {
        @Override
        public void onCancelListener() {
            channelUserTrendPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
        @Override
        public void onChannelUserTrendSelectorListener(List<String> ml) {

            mlcode.clear();
            mlcode.add(channelCode);
            mlcode.addAll(ml);
            getListData();
            channelUserTrendPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();

        }
    };
    private void getListData(){
        if(NetUtil.checkNet(getActivity())){
            userTrendPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getUTUrl() {

        if (GlobalParams.isTest){
            return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryUserTrance";
        }else{
            return ConstantValues.AUTH_SITE+"interface";
        }
    }

    @Override
    public int getUTCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getUTBody() {

        if (GlobalParams.isTest){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
            hashMap.put("dayRange",dayRange);
            hashMap.put("areaCode",areaCode);
            if (isdx){
                hashMap.put("channelCodeList",mlcode);

            }else{
                ArrayList<String> objects = new ArrayList<>();
                objects.add(channelCode);
                hashMap.put("channelCodeList",objects);


            }
            JSONObject jo = new JSONObject(hashMap);

            Log.e("TAG","频道用户走势参数:::"+jo.toString());
            return jo.toString();
        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryUserTrance");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {

                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryUserTrance");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("areaCode",areaCode);
                if (isdx){
                    listHashMap.put("channelCodeList",mlcode);
                }else{
                    ArrayList<String> objects = new ArrayList<>();
                    objects.add(channelCode);
                    listHashMap.put("channelCodeList",objects);
                }
                jsonObject= new JSONObject(listHashMap);
            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }
    }

    @Override
    public void getUTDataFailureMsg(String msg) {


    }

    @Override
    public void getUTData(Info info) {

        list.clear();
        list.addAll(info.userTrends);
        startTimeList.clear();
        audienceRatingList.clear();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myWebViewClient==null){
                    if (wv_qxt!=null){
                        wv_qxt.loadUrl("file:///android_asset/DateLine/index3.html");
                        myWebViewClient=new MyWebViewClient();
                        wv_qxt.setWebViewClient(myWebViewClient);

                    }

//                    wv_qxt.reload();
                }else{
                    if(wv_qxt!=null){
//                        wv_qxt.loadUrl("javascript:clearData();");
                        wv_qxt.reload();
                    }
                }
            }
        },1000);
    }
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    wv_qxt.loadUrl("javascript:clearData();");
                    if (list.size()>0){
                        for (int j=0;j<list.size();j++){
                            UserTrend userTrend = list.get(j);
                            startTimeList.clear();
                            audienceRatingList.clear();
                            for (int z=0;z<userTrend.dianList.size();z++){
                                Dian dian = userTrend.dianList.get(z);
                                if (j==0){
                                    startTimeList.add(dian.time);
                                }
                                audienceRatingList.add((String) dian.stbNum);
                            }
                            if (j==0){
                                for (int i=0;i<startTimeList.size();i++){

                                    final int y=i;
                                    wv_qxt.loadUrl("javascript:setTime('"+startTimeList.get(y)+"');");

                                }
                            }
                            showLineChart(startTimeList,audienceRatingList,j);
                        }
                        wv_qxt.loadUrl("javascript:setOption();");
                    }
                }
            });
            super.onPageFinished(view, url);
            Log.e("TAG","h5地址加载完成::::");
//            wv_qxt.stopLoading();
//            showLineChart(startTimeList,audienceRatingList);
        }
    }
    private void showLineChart(final List<String> startTimeList, final List<String> audienceRatingList, final int pos){
        for (int i=0;i<audienceRatingList.size();i++){
            final int y=i;
            wv_qxt.loadUrl("javascript:setData"+pos+"('"+audienceRatingList.get(y)+"');");
        }
        Log.e("TAG","list.get(pos).name==="+list.get(pos).name);

        String[] names = list.get(pos).name.split("-");

        wv_qxt.loadUrl("javascript:setLegend('"+names[1]+"');");
        wv_qxt.loadUrl("javascript:setName('"+pos+"');");
    }
    private CalendarUTPop.SelectorListener selectorListener = new CalendarUTPop.SelectorListener() {
        @Override
        public void onSelectorDRListener(String ks,boolean dx) {
            String[] split = ks.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            if (regionLSPop!=null){
                regionLSPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            if (channelUserTrendPop!=null){
                channelUserTrendPop.setTime(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            calendarUTPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            isdx=dx;
            tv_tiem.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            dayRange=ks;
            getListData();
            calendarUTPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onCancelListener() {

            calendarUTPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();

        }
    };
    @Override
    public void onDetach() {
        super.onDetach();
        GlobalParams.count=0;
        if (wv_qxt != null) {
            wv_qxt.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wv_qxt.clearHistory();
            ((ViewGroup) wv_qxt.getParent()).removeView(wv_qxt);
            wv_qxt.destroy();
            wv_qxt = null;
        }
    }

    public void setHide(){
        if (regionLSPop!=null){
            if (regionLSPop.isShowing()){
                regionLSPop.dismiss();
            }
        }
        if (channelUserTrendPop!=null){
            if (channelUserTrendPop.isShowing()){
                channelUserTrendPop.dismiss();
            }
        }
        if (calendarUTPop!=null){
            if (calendarUTPop.isShowing()){
                calendarUTPop.dismiss();
            }
        }
    }

}
