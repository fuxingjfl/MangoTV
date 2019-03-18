package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.StbNumList;
import com.xha.mangotv.entity.InflowList;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.OutflowList;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RealTimeDataImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "实时列表数据=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:

				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){
					JSONArray ja = jo1.getJSONArray("data");
					Info info = new Info();
					info.realTimeDatas = new ArrayList<>();
					for(int i=0;i<ja.length();i++){

						JSONObject joo = ja.getJSONObject(i);
						RealTimeData realTimeData = new RealTimeData();
						realTimeData.channelName=joo.getString("channelName");
						realTimeData.channelCode=joo.getString("channelCode");
						realTimeData.stbNum=joo.getString("stbNum");
						realTimeData.inflowStbNum=joo.getString("inflowStbNum");
						realTimeData.outflowStbNum=joo.getString("outflowStbNum");
						realTimeData.programName=joo.getString("programName");
						realTimeData.audienceRating=joo.getString("audienceRating");
						realTimeData.day=joo.getString("day");
						realTimeData.time=joo.getString("time");
						realTimeData.operator=joo.getString("operator");
						realTimeData.operatorName=joo.getString("operatorName");
						realTimeData.areaName=joo.getString("areaName");
						realTimeData.orderNo=joo.getString("orderNo");
						realTimeData.marketShare=joo.getString("marketShare");
						realTimeData.orderStatus=joo.getString("orderStatus");
						realTimeData.inRating=joo.getString("inRating");
						realTimeData.outRating=joo.getString("outRating");

						JSONArray ja1 = joo.getJSONArray("inflowList");
						realTimeData.inflowLists = new ArrayList<>();
						for(int j=0;j<ja1.length();j++){

							JSONObject joo1 = ja1.getJSONObject(j);
							InflowList inflowList = new InflowList();
							inflowList.channelId=joo1.getString("channelId");
							inflowList.channelName=joo1.getString("channelName");
							inflowList.count=joo1.getString("count");
							inflowList.programName=joo1.getString("programName");
							inflowList.rating=joo1.getString("rating");
							realTimeData.inflowLists.add(inflowList);

						}

						JSONArray ja2 = joo.getJSONArray("outflowList");
						realTimeData.outflowLists = new ArrayList<>();
						for(int z=0;z<ja2.length();z++){

							JSONObject joo2 = ja2.getJSONObject(z);
							OutflowList outflowList = new OutflowList();
							outflowList.channelId=joo2.getString("channelId");
							outflowList.channelName=joo2.getString("channelName");
							outflowList.count=joo2.getString("count");
							outflowList.programName=joo2.getString("programName");
							outflowList.rating=joo2.getString("rating");
							realTimeData.outflowLists.add(outflowList);
						}

						JSONArray ja3 = joo.getJSONArray("stbNumList");
						realTimeData.stbNumLists  = new ArrayList<>();
						for (int x=0;x<ja3.length();x++){

							JSONObject joo3 = ja3.getJSONObject(x);
							StbNumList ddata= new StbNumList();
							ddata.channelCode=joo3.getString("channelCode");
							ddata.channelName=joo3.getString("channelName");
							ddata.timeRange=joo3.getString("timeRange");

							JSONArray startTimeList_ja = joo3.getJSONArray("startTimeList");
							ddata.startTimeList = new ArrayList<>();
							for(int n=0;n<startTimeList_ja.length();n++){

								ddata.startTimeList.add(startTimeList_ja.getString(n));

							}

//							JSONArray stbNumList_ja = joo3.getJSONArray("stbNumList");
//							ddata.stbNumList = new ArrayList<>();
//							for(int n=0;n<stbNumList_ja.length();n++){
//
//								ddata.stbNumList.add(stbNumList_ja.getInt(n));
//
//							}

							ddata.audienceRatingList = new ArrayList<>();
							try {
								JSONArray audienceRatingList_ja = joo3.getJSONArray("audienceRatingList");
								for(int n=0;n<audienceRatingList_ja.length();n++){

									ddata.audienceRatingList.add(audienceRatingList_ja.getString(n));

								}
							}catch (Exception e){

							}finally {
								realTimeData.stbNumLists.add(ddata);
							}
						}
						info.realTimeDatas.add(realTimeData);
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
			Log.e("TAG","列表解析失败1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("TAG","列表解析失败2");
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
