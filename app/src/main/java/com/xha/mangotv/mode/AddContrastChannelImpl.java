package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddContrastChannelImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "添加对比频道=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);
				if(jo1.getBoolean("success")){
					JSONArray ja = jo1.getJSONArray("data");
					Info info = new Info();
					info.addContrastChannels = new ArrayList<>();
					for(int i=0;i<ja.length();i++){
						JSONObject joo1 = ja.getJSONObject(i);
						AddContrastChannel addContrastChannel = new AddContrastChannel();
						addContrastChannel.name=joo1.getString("name");
						addContrastChannel.mlist = new ArrayList<>();
						JSONArray vja = joo1.getJSONArray("value");
						addContrastChannel.mlist = new ArrayList<>();
						for (int j=0;j<vja.length();j++){
							JSONObject joo2 = vja.getJSONObject(j);
							ContrastChannelData c = new ContrastChannelData();
							c.areaCode=joo2.getString("areaCode");
							c.channelCode=joo2.getString("channelCode");
							c.channelName=joo2.getString("channelName");
							c.orderNo=joo2.getString("orderNo");
							addContrastChannel.mlist.add(c);
						}
						info.addContrastChannels.add(addContrastChannel);
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
