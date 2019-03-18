package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.ChannelGroup;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddressImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "地址=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){
					JSONArray ja = jo1.getJSONArray("data");
					Info info = new Info();
					info.addresses = new ArrayList<>();
					for(int i=0;i<ja.length();i++){

						JSONObject joo = ja.getJSONObject(i);

						Address address = new Address();
						address.id=joo.getString("id");
						address.areaCode=joo.getString("areaCode");
						address.areaName=joo.getString("areaName");
						address.parentAreaCode=joo.getString("parentAreaCode");
						info.addresses.add(address);

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
