package com.xha.mangotv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xha.mangotv.Presenter.ChannelShareRatioPresenter;
import com.xha.mangotv.Presenter.ChannelShareRatioView;
import com.xha.mangotv.Presenter.SpecificChannelPresenter;
import com.xha.mangotv.Presenter.SpecificChannelView;
import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Channel;
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
import com.xha.mangotv.view.CalendarSRPop;
import com.xha.mangotv.view.PieChartView;
import com.xha.mangotv.view.TimeSlotPop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/9/6.
 */

public class ChannelShareRatioActivity extends BaseActivity implements ChannelShareRatioView,SpecificChannelView{
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private TextView title_content;
    private PieChartView pie_chart_fl,pie_chart_sn;
    private CalendarSRPop calendarSRPop;
    private TimeSlotPop timeSlotPop;
    private ImageView iv_left,iv_right;
    private TextView tv_rl_content,tv_dq;
    private LinearLayout ll_rl_sj,ll_dq;
    private ChannelShareRatioPresenter channelShareRatioPresenter;
    private TextView tv_pdzb;
    private String dayRange,timeRange="00:00:00-23:59:59",areaCode,channelGroupCode="-1";
    private ImageView iv_dq;
    private String marker;//当前的频道百分比
    private boolean isfirst=true;
    private SpecificChannelPresenter specificChannelPresenter;
    private List<String> channelCodeList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_channel_share_ratio);
        rl_bt = (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        title_content = (TextView) findViewById(R.id.title_content);
        pie_chart_fl= (PieChartView) findViewById(R.id.pie_chart_fl);
        pie_chart_sn= (PieChartView) findViewById(R.id.pie_chart_sn);
        tv_pdzb= (TextView) findViewById(R.id.tv_pdzb);
        iv_left= (ImageView) findViewById(R.id.iv_left);
        iv_dq= (ImageView) findViewById(R.id.iv_dq);
        tv_dq= (TextView) findViewById(R.id.tv_dq);
        iv_right= (ImageView) findViewById(R.id.iv_right);
        tv_rl_content= (TextView) findViewById(R.id.tv_rl_content);
        ll_rl_sj= (LinearLayout) findViewById(R.id.ll_rl_sj);
        ll_dq= (LinearLayout) findViewById(R.id.ll_dq);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.fanhui);
        title_content.setText("频道份额占比");
        title_content.setVisibility(View.VISIBLE);
        channelShareRatioPresenter = new ChannelShareRatioPresenter(this);
        specificChannelPresenter = new SpecificChannelPresenter(this);
        Calendar calendar= Calendar.getInstance();
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH)-1;

        final int month = calendar.get(Calendar.MONTH)+1;

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
        calendarSRPop = new CalendarSRPop(ChannelShareRatioActivity.this,R.layout.pop_calendar_sr,listener,tv_rl_content.getText().toString());
//        getAddressData();

        String areaList = PreUtil.getInstance().getString(ConstantValues.areaList_fezb);

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
            timeSlotPop = new TimeSlotPop(ChannelShareRatioActivity.this,R.layout.pop_time,regionlistener,addressmlist,tv_rl_content.getText().toString());
            timeSlotPop.setOnDismissListener(onDismissListener);
            calendarSRPop.setNameText(addressmlist.get(0).areaName);
            timeSlotPop.setNameText(addressmlist.get(0).areaName);
            tv_dq.setText(addressmlist.get(0).areaName);
            getListData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iv_fanhui.setOnClickListener(onClickListener);
        ll_rl_sj.setOnClickListener(onClickListener);
        ll_dq.setOnClickListener(onClickListener);
        calendarSRPop.setOnDismissListener(onDismissListener);
        pie_chart_fl.setOnTouchClickListener(new PieChartView.TouchClickListener() {
            @Override
            public void OnTouchClickListener(String ccc,String name,String bf) {
                isfirst=false;
                channelGroupCode=ccc;
                Log.e("TAG","bf:::::"+bf);
                marker=bf.replace("%","");
                Log.e("TAG","百分比:::::"+marker);
                tv_pdzb.setText(name+"份额占比("+marker+"%)");
                getPPZData();
            }
        });
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_fanhui:
                    finish();
                    break;
                case R.id.ll_rl_sj:
                    iv_left.setImageResource(R.drawable.left_xz);
                    iv_right.setImageResource(R.drawable.right_xz);
                    tv_rl_content.setTextColor(getResources().getColor(R.color.persimmon));
                    if (calendarSRPop != null) {
                        if (calendarSRPop.isShowing()) {
                            calendarSRPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            calendarSRPop.showAsDropDown(rl_bt);
                        }
                    }
                    break;
                case R.id.ll_dq:
                    iv_dq.setImageResource(R.drawable.xia_xz);
                    tv_dq.setTextColor(getResources().getColor(R.color.persimmon));
                    if (timeSlotPop != null) {
                        if (timeSlotPop.isShowing()) {
                            timeSlotPop.dismiss();
                            // StateChanged();
                        } else {
                            setWindowTranslucence(0.3);
                            timeSlotPop.showAsDropDown(rl_bt);
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

    private CalendarSRPop.SelectorListener listener = new CalendarSRPop.SelectorListener() {

        @Override
        public void onSelectorDRListener(String ks, String sj) {
            String[] split = ks.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            if (calendarSRPop!=null){
                calendarSRPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            if (timeSlotPop!=null){
                timeSlotPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            dayRange=ks;
            timeRange=sj;
            isfirst=true;
            channelGroupCode="-1";
            getListData();
            calendarSRPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }

        @Override
        public void onSelectorTimeListener(String day, String sj) {
            String[] split = day.split(":");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split("-");
            tv_rl_content.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            if (calendarSRPop!=null){
                calendarSRPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            if (timeSlotPop!=null){
                timeSlotPop.setText(split1[1]+"-"+split1[2]+":"+split2[1]+"-"+split2[2]);
            }
            dayRange=day;
            timeRange=sj;
            isfirst=true;
            channelGroupCode="-1";
            getListData();
            calendarSRPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
        @Override
        public void onCancelListener() {
            calendarSRPop.dismiss();
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
    }

    private TimeSlotPop.RegionSelectorListener regionlistener=new TimeSlotPop.RegionSelectorListener() {
        @Override
        public void onCancelListener() {
            timeSlotPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
        @Override
        public void onRegionSelectorListener(String data,String name) {
            calendarSRPop.setNameText(name);
            timeSlotPop.setNameText(name);
            tv_dq.setText(name);
            areaCode=data;
            isfirst=true;
            channelGroupCode="-1";
            getListData();
            timeSlotPop.dismiss();
            setWindowTranslucence(1.0f);
            initState();
        }
    };

    /**
     *
     * 数据列表请求
     *
     */
    private void getListData(){
        if(NetUtil.checkNet(ChannelShareRatioActivity.this)){
            channelShareRatioPresenter.getDisposeData();
        }else {
            Toast.makeText(ChannelShareRatioActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getCSRUrl() {

        if (GlobalParams.isTest){
                    return ConstantValues.URI_YONG_TEST+"appHistory/getHistoryMarketShare";
        }else{
            return ConstantValues.AUTH_SITE+"interface";

        }
    }
    @Override
    public int getCSRCode() {
        return HttpConstants.search_news01;
    }
    @Override
    public String getCSRBody() {

        if (GlobalParams.isTest){
            JSONObject jo = null;
            try {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));

                hashMap.put("dayRange",dayRange);

                hashMap.put("timeRange",timeRange);

                hashMap.put("areaCode",areaCode);
//            JSONArray ja = new JSONArray(PreUtil.getInstance().getString(ConstantValues.channelGroup_fezb));
//
//            Log.e("TAG","立面:::"+ja.toString());
                if (isfirst){
                    hashMap.put("channelCodeList",new ArrayList<>());
                }else{
                    hashMap.put("channelCodeList",channelCodeList);
                }
                jo  = new JSONObject(hashMap);
                jo.put("channelGroupCode",channelGroupCode);
                Log.e("TAG","占比参数:::"+jo.toString());
                return jo.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                return jo.toString();
            }
        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","appHistory/getHistoryMarketShare");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                HashMap<String ,Object> listHashMap = new HashMap<>();
                listHashMap.put("redirectUrl","appHistory/getHistoryMarketShare");
                listHashMap.put("phone", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("imei", MACUtil.getMacAddress());
                listHashMap.put("currTime",time+"");
                listHashMap.put("sign",sign);
                listHashMap.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
                listHashMap.put("dayRange",dayRange);
                listHashMap.put("timeRange",timeRange);
                listHashMap.put("areaCode",areaCode);
                if (isfirst){
                    listHashMap.put("channelCodeList",new ArrayList<>());
                }else{
                    listHashMap.put("channelCodeList",channelCodeList);
                }
                listHashMap.put("channelGroupCode",channelGroupCode);
                jsonObject= new JSONObject(listHashMap);
            } finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }


    }
    @Override
    public void getCSRDataFailureMsg(String msg) {
        Toast.makeText(ChannelShareRatioActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void getCSRData(Info info) {
        List<LiShiInfo> liShiInfos = info.liShiInfos;
        boolean isyz=false;
        List<LiShiInfo> shishi = new ArrayList<>();
        if (isfirst){//isfirst这个参数控制这个上面那个圆饼的数据是否刷新
            shishi.addAll(liShiInfos);
            for (int i=0;i<shishi.size();i++){
                LiShiInfo liShiInfo = shishi.get(i);
                if (Double.parseDouble(liShiInfo.marketShare)!=0){
                    Log.e("TAG","大圆具体数据::::========="+liShiInfo.marketShare);
                    isyz=true;
                    break;
                }
            }
            List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
            Log.e("TAG","数据量::::========="+shishi.size());
            if (isyz){
                for (int i=0;i<shishi.size();i++){
                    LiShiInfo liShiInfo = shishi.get(i);
                    PieChartView.PieceDataHolder pieceDataHolder = new PieChartView.PieceDataHolder(Double.parseDouble(liShiInfo.marketShare), i, liShiInfo.channelGroupName + "\n" + liShiInfo.marketShare + "%",liShiInfo.channelGroupCode,liShiInfo.channelGroupName,false);
                    pieceDataHolders.add(pieceDataHolder);
                }
            }else{
                for (int i=0;i<shishi.size();i++){
                    LiShiInfo liShiInfo = shishi.get(i);
                    PieChartView.PieceDataHolder pieceDataHolder = new PieChartView.PieceDataHolder((100d/shishi.size()), i, liShiInfo.channelGroupName + "\n" + liShiInfo.marketShare + "%",liShiInfo.channelGroupCode,liShiInfo.channelGroupName,true);
                    pieceDataHolders.add(pieceDataHolder);
                }
            }
            pie_chart_fl.setData(pieceDataHolders);
            channelGroupCode=shishi.get(0).channelGroupCode;
            marker=shishi.get(0).marketShare;
            tv_pdzb.setText(shishi.get(0).channelGroupName+"份额占比("+shishi.get(0).marketShare+"%)");
            getPPZData();
        }else{

            double num=0.0000;
            if (liShiInfos.size()>10){
                Log.e("TAG","marker==="+marker);
                BigDecimal bd3 = new BigDecimal(marker);
                for (int i=0;i<liShiInfos.size();i++){
                    LiShiInfo liShiInfo = liShiInfos.get(i);
                    BigDecimal bd1 = new BigDecimal(Double.toString(num));
                    Log.e("TAG","除数::::"+liShiInfo.marketShare);
                    BigDecimal bd2 = new BigDecimal(liShiInfo.marketShare);
                    BigDecimal bd4 = new BigDecimal("100");
                    if (i<10){//前十位
                        if (bd2.multiply(bd4).doubleValue()!=0){
                            if (Double.parseDouble(marker)==0){
                                liShiInfo.bfb="0";
                            }else{
                                double v = bd2.multiply(bd4).divide(bd3,4).doubleValue();
                                liShiInfo.bfb=v+"";
                            }
                        }else{
                            liShiInfo.bfb="0";
                        }
                        shishi.add(liShiInfo);
                    }else{
                        num=bd1.add(bd2).doubleValue();
                    }
                }
                for (int i=0;i<shishi.size();i++){
                    LiShiInfo liShiInfo = shishi.get(i);
                    if (Double.parseDouble(liShiInfo.marketShare)!=0){
                        isyz=true;
                        break;
                    }
                }
                Log.e("TAG","num==="+num);
                LiShiInfo liShiInfo = new LiShiInfo();
                liShiInfo.channelName="其他频道";
                double v=0;
                if (isyz){
                    BigDecimal bd1 = new BigDecimal(Double.toString(100.0000));
                    BigDecimal bd2 = new BigDecimal(Double.toString(num));
                    if (Double.parseDouble(marker)==0){
                        liShiInfo.bfb="0";
                    }else{
                        v= bd2.multiply(bd1).divide(bd3,4).doubleValue();
                        liShiInfo.bfb=v+"";
                    }
                }else{
                    v = 0;
                    liShiInfo.bfb=v+"";
                }
                Log.e("TAG","v==="+v);
                liShiInfo.marketShare=num+"";
                shishi.add(liShiInfo);
            }else{


                BigDecimal bd3 = new BigDecimal(marker);
                for (int i=0;i<liShiInfos.size();i++){
                    LiShiInfo liShiInfo = liShiInfos.get(i);
                    BigDecimal bd1 = new BigDecimal(Double.toString(num));
                    Log.e("TAG","除数::::"+liShiInfo.marketShare);
                    BigDecimal bd2 = new BigDecimal(liShiInfo.marketShare);
                    BigDecimal bd4 = new BigDecimal("100");

                        if (bd2.multiply(bd4).doubleValue()!=0){
                            double v = bd2.multiply(bd4).divide(bd3,4).doubleValue();
                            liShiInfo.bfb=v+"";
                        }else{
                            liShiInfo.bfb="0";
                        }
                        shishi.add(liShiInfo);
                }
//                shishi.addAll(liShiInfos);
            }
            List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
            if (isyz){
                for (int i=0;i<shishi.size();i++){
                    LiShiInfo liShiInfo = shishi.get(i);
                    PieChartView.PieceDataHolder pieceDataHolder = new PieChartView.PieceDataHolder(Double.parseDouble(liShiInfo.bfb), i, liShiInfo.channelName + "\n" + liShiInfo.bfb + "%",liShiInfo.channelGroupCode,liShiInfo.channelGroupName,false);
                    pieceDataHolders.add(pieceDataHolder);
                }
            }else{
                for (int i=0;i<shishi.size();i++){
                    LiShiInfo liShiInfo = shishi.get(i);
//                    Log.e("TAG","YYYYY==="+liShiInfo.bfb);
                    PieChartView.PieceDataHolder pieceDataHolder = new PieChartView.PieceDataHolder((100d/shishi.size()), i, liShiInfo.channelName + "\n" + liShiInfo.bfb + "%",liShiInfo.channelGroupCode,liShiInfo.channelGroupName,true);
                    pieceDataHolders.add(pieceDataHolder);
                }
            }
            pie_chart_sn.setData(pieceDataHolders);
        }
        isfirst=false;
    }
    private void getPPZData(){
        if(NetUtil.checkNet(ChannelShareRatioActivity.this)){
            specificChannelPresenter.getDisposeData();
        }else {
            Toast.makeText(ChannelShareRatioActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getSCUrl() {

        if (GlobalParams.isTest){
                    return ConstantValues.URI_YONG_TEST+"channel/getChannelByChannelGroupCode";
        }else{
            return ConstantValues.AUTH_SITE+"interface";
        }
    }
    @Override
    public int getSCCode() {
        return HttpConstants.search_news01;
    }
    @Override
    public String getSCBody() {

        if (GlobalParams.isTest){
            HashMap<String ,String > hashMap = new HashMap<>();
            hashMap.put("channelGroupCode",channelGroupCode);
            JSONObject jo = new JSONObject(hashMap);
            Log.e("TAG","具体频道组数据:::"+jo.toString());
            return jo.toString();
        }else{
            long time = System.currentTimeMillis();
            HashMap<String,String> map = new HashMap<>();
            map.put("redirectUrl","channel/getChannelByChannelGroupCode");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                jsonObject= JSONTools.parseMapToJson(map);
                jsonObject.put("redirectUrl","channel/getChannelByChannelGroupCode");
                jsonObject.put("phone", PreUtil.getInstance().getString(PreContact.username));
                jsonObject.put("imei", MACUtil.getMacAddress());
                jsonObject.put("currTime",time+"");
                jsonObject.put("sign",sign);
                jsonObject.put("channelGroupCode",channelGroupCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
                return jsonObject.toString();
            }
        }



    }
    @Override
    public void getSCDataFailureMsg(String msg) {

    }
    @Override
    public void getSCData(Info info) {

        List<Channel> channels = info.channels;
        channelCodeList.clear();
        for (int i=0;i<channels.size();i++){
            Channel channel = channels.get(i);
            channelCodeList.add(channel.channelCode);
        }
        getListData();

    }
}
