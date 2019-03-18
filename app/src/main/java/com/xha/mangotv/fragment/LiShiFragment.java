package com.xha.mangotv.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xha.mangotv.Presenter.LiShiPresenter;
import com.xha.mangotv.Presenter.LiShiView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.CoinAdapter;
import com.xha.mangotv.adapter.CommonViewHolder;
import com.xha.mangotv.adapter.LiShiAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.CoinInfo;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.HRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 历史收视率
 * Created by ysq on 2018/9/4.
 */

public class LiShiFragment extends BaseFragment implements LiShiView{

    private List<LiShiInfo> list;
    private HRecyclerView hRecyclerView;
    private LiShiPresenter liShiPresenter;
    private String areaCode,channelGroupCode;
//    @SuppressLint({"NewApi", "ValidFragment"})
//    public LiShiFragment(String areaCode,String channelGroupCode){
//        this.areaCode="14301";
//        this.channelGroupCode=channelGroupCode;
//    }
//
//    public LiShiFragment(){
//
//    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_lishi;
    }

    @Override
    protected void lazyLoad() {

        View contentView = getContentView();
        Bundle arguments = getArguments();
        areaCode=arguments.getString("areaCode");
        channelGroupCode=arguments.getString("channelCode");

        hRecyclerView= (HRecyclerView) findViewById(R.id.id_hrecyclerview);

        list = new ArrayList<>();
//        for(int i=0;i<15;i++){
//
//            CoinInfo coinInfo = new CoinInfo();
//            coinInfo.name = "USDT";
//            coinInfo.priceLast="20.0";
//            coinInfo.riseRate24="0.2";
//            coinInfo.vol24="10020";
//            coinInfo.close="22.2";
//            coinInfo.open="40.0";
//            coinInfo.bid="33.2";
//            coinInfo.ask="19.0";
//            coinInfo.amountPercent = "33.3%";
//            list.add(coinInfo);
//
//        }

        liShiPresenter = new LiShiPresenter(this);
        String[] tab = {"播出时间","收视率","收视份额","到达率"};
        String[] mLeftTextList = new String[]{"昨日历史节目"};
        hRecyclerView.setHeaderListData(tab,mLeftTextList);
        getData();

    }

    private void getData(){
        if(NetUtil.checkNet(getActivity())){
            liShiPresenter.getDisposeData();
        }else {
            Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getLSUrl() {
//        return ConstantValues.URI_YONG_TEST+"appReal/getYesterdayLiveData";
        return ConstantValues.AUTH_SITE+"interface";
    }

    @Override
    public int getLSCode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getLSBody() {
//        HashMap<String, String> map_yc = new HashMap<>();
//        map_yc.put("timeRange","00:00:00-23:59:59");
//        map_yc.put("areaCode","14300");
//        map_yc.put("channelGroupCode","YSPD");
//        map_yc.put("phoneNum","15836659008");
//        JSONObject jo = new JSONObject(map_yc);
//        Log.e("TAG","参数::::"+jo.toString());
//        return jo.toString();

        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","appReal/getYesterdayLiveData");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());

        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("sign",sign);
            jsonObject.put("timeRange","00:00:00-23:59:59");
            jsonObject.put("areaCode",areaCode);
            jsonObject.put("showProgram",true);
            List<String> stringList = new ArrayList<>();
            stringList.add(channelGroupCode);
            JSONArray ja = new JSONArray(stringList);
//            HashMap<String,List<String>> listHashMap = new HashMap<>();
//            listHashMap.put("channelGroupCode",stringList);
//            JSONObject jo = new JSONObject(listHashMap);
            jsonObject.put("channelCodeList",ja);
            jsonObject.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }

    }

    @Override
    public void getLSDataFailureMsg(String msg) {
        if(getActivity()!=null){
//            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            if("与数据平台接口交互失败".equals(msg)){
                getData();
            }
        }
    }

    @Override
    public void getLSData(Info info) {
        list.addAll(info.liShiInfos);
        LiShiAdapter adapter = new LiShiAdapter(getContext(), list, R.layout.item_content5, new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
            @Override
            public void onItemLongClickListener(int position) {

            }
        });
        hRecyclerView.setAdapter(adapter);
    }
}
