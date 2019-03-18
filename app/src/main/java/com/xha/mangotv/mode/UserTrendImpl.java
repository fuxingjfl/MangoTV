package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Dian;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.UserTrend;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ysq on 2018/10/25.
 */

public class UserTrendImpl implements DataModel,OkHttpClientManager.HttpCallback {
    private onDataListener onDataListener;
    @Override
    public void onSuccess(String result, int code) {
        // TODO Auto-generated method stub

        Log.e("TAG", "===用户在线走势==="+result);

        try {
            switch(code){
                case HttpConstants.search_news01:

                        JSONObject jo1 = new JSONObject(result);
                        if(jo1.getBoolean("success")){
                            JSONArray ja = jo1.getJSONArray("data");
                            Info info = new Info();
                            info.userTrends = new ArrayList<>();
                            for (int i=0;i<ja.length();i++){

                                JSONObject jo2 = ja.getJSONObject(i);

                                UserTrend userTrend = new UserTrend();
                                userTrend.name=jo2.getString("name");
                                userTrend.dianList = new ArrayList<>();
                                JSONArray value = jo2.getJSONArray("value");
                                for (int j=0;j<value.length();j++){
//
                                    Dian dian = new Dian();
                                    JSONObject jo3 = value.getJSONObject(j);
                                    dian.areaName=jo3.getString("areaName");
                                    dian.channelCode=jo3.getString("channelCode");
                                    dian.channelName=jo3.getString("channelName");
                                    dian.dayRange=jo3.getString("dayRange");
                                    dian.operatorName=jo3.getString("operatorName");
                                    dian.sourceTypeName=jo3.getString("sourceTypeName");
                                    dian.stbNum=jo3.getString("stbNum");
                                    dian.time=jo3.getString("time");
                                    userTrend.dianList.add(dian);
                                }
                                info.userTrends.add(userTrend);
                            }
                            onDataListener.onSuccessListener(info, code);
                    }else{
                        onDataListener.onFailureCodeListener(jo1.getString("errorMessage"), code);
                    }

                    break;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TAG","错误数据=====222222222222222222222222222");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TAG","错误数据=====4444444444444444444444444444");
        }

    }

    @Override
    public void onStart(int code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFailure(int code) {
        // TODO Auto-generated method stub
        onDataListener.onFailureListener("请求失败了");
    }

    @Override
    public void getData(String url, int code, String json,
                        onDataListener onDataListener) {
        // TODO Auto-generated method stub
        this.onDataListener=onDataListener;
        OkHttp()._getOkHttp(url, code, json, this,5000);
    }
    public OkHttpClientManager OkHttp() {
        return OkHttpClientManager.getInstance();
    }
}
