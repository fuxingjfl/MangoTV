package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.entity.InflowList;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.OutflowList;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.entity.StbNumList;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DuiBiDataImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "对比实时数据=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){
					JSONArray ja = jo1.getJSONArray("data");
					Info info = new Info();
					info.duiBiDatas = new ArrayList<>();
					for(int i=0;i<ja.length();i++){

						JSONObject joo = ja.getJSONObject(i);
						DuiBiData realTimeData = new DuiBiData();
						realTimeData.channelName=joo.getString("channelName");
						realTimeData.channelCode=joo.getString("channelCode");
						realTimeData.timeRange=joo.getString("timeRange");
						realTimeData.programName=joo.getString("programName");
						realTimeData.audienceRating=joo.getString("audienceRating");
						realTimeData.orderStatus=joo.getString("orderStatus");
						realTimeData.marketShare=joo.getString("marketShare");
						JSONArray startTimeList_ja = joo.getJSONArray("startTimeList");
						realTimeData.startTimeList = new ArrayList<>();
						for(int n=0;n<startTimeList_ja.length();n++){

							realTimeData.startTimeList.add(startTimeList_ja.getString(n));

						}

//						JSONArray stbNumList_ja = joo.getJSONArray("stbNumList");
//						realTimeData.stbNumList = new ArrayList<>();
//						for(int n=0;n<stbNumList_ja.length();n++){
//
//							realTimeData.stbNumList.add(stbNumList_ja.getString(n));
//
//						}
						realTimeData.audienceRatingList = new ArrayList<>();

						try {
							JSONArray audienceRatingList_ja = joo.getJSONArray("audienceRatingList");
							for(int n=0;n<audienceRatingList_ja.length();n++){
//							Log.e("TAG","解析的值收视率:::"+audienceRatingList_ja.getString(n));
								realTimeData.audienceRatingList.add(audienceRatingList_ja.getString(n));
							}
						}catch (Exception e){

						}finally {
							JSONArray marketShareList_ja = joo.getJSONArray("marketShareList");
							realTimeData.marketShareList = new ArrayList<>();
							for(int n=0;n<marketShareList_ja.length();n++){
								realTimeData.marketShareList.add(marketShareList_ja.getString(n));
							}
							info.duiBiDatas.add(realTimeData);
						}

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
