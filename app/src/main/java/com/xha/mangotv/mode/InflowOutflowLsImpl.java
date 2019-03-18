package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.InflowOutflow;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.LastCount;
import com.xha.mangotv.entity.NextCount;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class InflowOutflowLsImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "历史流入流出=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){

					Info info = new Info();

					JSONObject data = jo1.getJSONObject("data");
					InflowOutflow inflowOutflow = new InflowOutflow();

					inflowOutflow.nextRate=data.getString("nextRate");
					inflowOutflow.lastRete=data.getString("lastRete");
					inflowOutflow.lastCounts = new ArrayList<>();
					JSONArray lastCount = data.getJSONArray("lastCount");

					for (int i=0;i<lastCount.length();i++){

						LastCount lc = new LastCount();
						JSONObject joo1 = lastCount.getJSONObject(i);
						lc.channelCode=joo1.getString("channelCode");
						lc.channelName=joo1.getString("channelName");
						lc.lastChanneName=joo1.getString("lastChanneName");
						lc.lastRate=joo1.getString("lastRate");
						lc.lastStbNum=joo1.getString("lastStbNum");
						lc.operator=joo1.getString("operator");
						lc.operatorName=joo1.getString("operatorName");
						lc.sourceType=joo1.getString("sourceType");
						lc.sourceTypeName=joo1.getString("sourceTypeName");
						inflowOutflow.lastCounts.add(lc);

					}

					inflowOutflow.nextCounts = new ArrayList<>();
					JSONArray nextCount = data.getJSONArray("nextCount");
					for (int i=0;i<nextCount.length();i++){
						NextCount nc = new NextCount();
						JSONObject joo2 = nextCount.getJSONObject(i);
						nc.channelCode=joo2.getString("channelCode");
						nc.channelName=joo2.getString("channelName");
						nc.nextChanneName=joo2.getString("nextChanneName");
						nc.nextRate=joo2.getString("nextRate");
						nc.nextStbNum=joo2.getString("nextStbNum");
						nc.operator=joo2.getString("operator");
						nc.operatorName=joo2.getString("operatorName");
						nc.sourceType=joo2.getString("sourceType");
						nc.sourceTypeName=joo2.getString("sourceTypeName");
						inflowOutflow.nextCounts.add(nc);
					}

					info.inflowOutflow=inflowOutflow;
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

			Log.e("TAG","解析失败历史========="+e.getMessage());
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
