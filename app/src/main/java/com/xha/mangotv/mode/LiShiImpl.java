package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LiShiImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "频道历史数据=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){
					JSONArray ja = jo1.getJSONArray("data");
					Info info = new Info();
					info.liShiInfos = new ArrayList<>();
					for(int i=0;i<ja.length();i++){

						JSONObject joo = ja.getJSONObject(i);
						LiShiInfo liShiInfo = new LiShiInfo();
						liShiInfo.dayRange=joo.getString("dayRange");
						liShiInfo.timeRange=joo.getString("timeRange");
						liShiInfo.areaName=joo.getString("areaName");
						liShiInfo.operatorName=joo.getString("operatorName");
						liShiInfo.sourceTypeName=joo.getString("sourceTypeName");
						liShiInfo.channelCode=joo.getString("channelCode");
						liShiInfo.channelName=joo.getString("channelName");
						liShiInfo.channelGroupCode=joo.getString("channelGroupCode");
						liShiInfo.channelGroupName=joo.getString("channelGroupName");
						liShiInfo.audienceRating=joo.getString("audienceRating");
						liShiInfo.marketShare=joo.getString("marketShare");
						liShiInfo.stbNum=joo.getString("stbNum");
						liShiInfo.arrivalRate=joo.getString("arrivalRate");
						liShiInfo.viewTime=joo.getString("viewTime");
						liShiInfo.playTime=joo.getString("playTime");
						liShiInfo.operator=joo.getString("operator");
						liShiInfo.sourceType=joo.getString("sourceType");
						liShiInfo.startTime=joo.getString("startTime");
						liShiInfo.stopTime=joo.getString("stopTime");
						liShiInfo.areaCode=joo.getString("areaCode");
						liShiInfo.currentRanking=joo.getString("currentRanking");
						liShiInfo.startDay=joo.getString("startDay");
						liShiInfo.stopDay=joo.getString("stopDay");
						liShiInfo.programName= joo.getString("programName");
						liShiInfo.stbNumErrorMessageSet=joo.getString("stbNumErrorMessageSet");
						info.liShiInfos.add(liShiInfo);
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
			Log.e("TAG","执行了错误1111111");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("TAG","执行了错误22222222");
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
