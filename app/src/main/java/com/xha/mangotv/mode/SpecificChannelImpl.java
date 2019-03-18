package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Channel;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ysq on 2018/10/23.
 */

public class SpecificChannelImpl implements DataModel,OkHttpClientManager.HttpCallback {
    private onDataListener onDataListener;
    @Override
    public void onSuccess(String result, int code) {
        // TODO Auto-generated method stub

        Log.e("TAG", "频道具体接口=="+result);

        try {
            switch(code){
                case HttpConstants.search_news01:

                    JSONObject jo1 = new JSONObject(result);

                    if(jo1.getBoolean("success")){
                        Info info = new Info();
                        JSONArray data = jo1.getJSONArray("data");
                        info.channels = new ArrayList<>();
                        for (int i=0;i<data.length();i++){
                            JSONObject jo2 = data.getJSONObject(i);
                            Channel channel = new Channel();
                            channel.areaCode=jo2.getString("areaCode");
                            channel.channelCode=jo2.getString("channelCode");
                            channel.channelName=jo2.getString("channelName");
                            channel.orderNo=jo2.getString("orderNo");
                            info.channels.add(channel);
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
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
