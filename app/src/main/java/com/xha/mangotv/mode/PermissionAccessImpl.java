package com.xha.mangotv.mode;

import android.util.Log;

import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.PDssInfo;
import com.xha.mangotv.entity.PDtypeInfo;
import com.xha.mangotv.entity.PDzsInfo;
import com.xha.mangotv.entity.PermissionAccess;
import com.xha.mangotv.entity.SSlvInfo;
import com.xha.mangotv.entity.ZBjiemInfo;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.okhttpUtils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PermissionAccessImpl implements DataModel,OkHttpClientManager.HttpCallback {
	private onDataListener onDataListener;
	@Override
	public void onSuccess(String result, int code) {
		// TODO Auto-generated method stub
		
		Log.e("TAG", "权限=="+result);
		
		try {
			switch(code){
			case HttpConstants.search_news01:
				
				JSONObject jo1 = new JSONObject(result);

				if(jo1.getBoolean("success")){
					JSONObject jo_data = jo1.getJSONObject("data");
					JSONObject jo_permissionMap = jo_data.getJSONObject("permissionMap");
					Info info = new Info();
					info.permissionAccess = new PermissionAccess();
					JSONObject joo1 = jo_permissionMap.getJSONObject("10000");
					SSlvInfo ss = new SSlvInfo();
					ss.menuId=joo1.getString("menuId");
					ss.menuName=joo1.getString("menuName");
					ss.inout=joo1.getBoolean("inout");
					ss.history=joo1.getBoolean("history");
					ss.realtimeLine=joo1.getBoolean("realtimeLine");
					ss.selected=joo1.getBoolean("selected");



					JSONArray ja1 = joo1.getJSONArray("indexSet");
					ss.indexSet = new ArrayList<>();
					for(int i=0;i<ja1.length();i++){

						ss.indexSet.add(ja1.getString(i));

					}

					JSONArray ja2 = joo1.getJSONArray("areaList");
					ss.areaList = new ArrayList<>();
					for(int i=0;i<ja2.length();i++){

						ss.areaList.add(ja2.getString(i));

					}

					JSONArray ja3 = joo1.getJSONArray("channelGroup");
					ss.channelGroup = new ArrayList<>();
					for(int i=0;i<ja3.length();i++){

						ss.channelGroup.add(ja3.getString(i));
					}


					PDssInfo pDssInfo = new PDssInfo();
					JSONObject joo2 = jo_permissionMap.getJSONObject("21000");
					pDssInfo.menuId=joo2.getString("menuId");
					pDssInfo.menuName=joo2.getString("menuName");
					pDssInfo.inout=joo2.getBoolean("inout");
					pDssInfo.realtimeLine=joo2.getBoolean("realtimeLine");
					pDssInfo.selected=joo2.getBoolean("selected");
					pDssInfo.userOnline=joo2.getBoolean("userOnline");
					pDssInfo.inoutStbNum=joo2.getBoolean("inoutStbNum");
					JSONArray ja4 = joo2.getJSONArray("areaList");
					pDssInfo.areaList = new ArrayList<>();
					for(int i=0;i<ja4.length();i++){

						pDssInfo.areaList.add(ja4.getString(i));

					}

					JSONArray ja5 = joo2.getJSONArray("indexSet");
					pDssInfo.indexSet = new ArrayList<>();
					for(int i=0;i<ja5.length();i++){

						pDssInfo.indexSet.add(ja5.getString(i));

					}

					JSONArray ja6 = joo2.getJSONArray("channelGroup");
					pDssInfo.channelGroup = new ArrayList<>();
					for(int i=0;i<ja6.length();i++){

						pDssInfo.channelGroup.add(ja6.getString(i));

					}


					PDzsInfo pDzsInfo = new PDzsInfo();
					JSONObject joo3 = jo_permissionMap.getJSONObject("21100");
					pDzsInfo.menuId=joo3.getString("menuId");
					pDzsInfo.menuName=joo3.getString("menuName");
					pDzsInfo.selected=joo3.getBoolean("selected");
					JSONArray ja7 = joo3.getJSONArray("indexSet");
					pDzsInfo.indexSet = new ArrayList<>();
					for(int i=0;i<ja7.length();i++){

						pDzsInfo.indexSet.add(ja7.getString(i));

					}
					JSONArray ja8 = joo3.getJSONArray("areaList");
					pDzsInfo.areaList = new ArrayList<>();
					for(int i=0;i<ja8.length();i++){

						pDzsInfo.areaList.add(ja8.getString(i));

					}

					JSONArray ja9 = joo3.getJSONArray("channelGroup");
					pDzsInfo.channelGroup = new ArrayList<>();
					for(int i=0;i<ja9.length();i++){

						pDzsInfo.channelGroup.add(ja9.getString(i));

					}


					ZBjiemInfo zBjiemInfo = new ZBjiemInfo();
					JSONObject joo4 = jo_permissionMap.getJSONObject("22000");
					zBjiemInfo.menuId=joo4.getString("menuId");
					zBjiemInfo.menuName=joo4.getString("menuName");
					zBjiemInfo.selected=joo4.getBoolean("selected");
					JSONArray ja10 = joo4.getJSONArray("areaList");
					zBjiemInfo.areaList = new ArrayList<>();
					for(int i=0;i<ja10.length();i++){

						zBjiemInfo.areaList.add(ja10.getString(i));

					}
					JSONArray ja11 = joo4.getJSONArray("channelGroup");
					zBjiemInfo.channelGroup = new ArrayList<>();
					for(int i=0;i<ja11.length();i++){

						zBjiemInfo.channelGroup.add(ja11.getString(i));

					}

					PDtypeInfo pDtypeInfo = new PDtypeInfo();
					JSONObject joo5 = jo_permissionMap.getJSONObject("23000");
					pDtypeInfo.menuId=joo5.getString("menuId");
					pDtypeInfo.menuName=joo5.getString("menuName");
					pDtypeInfo.selected=joo5.getBoolean("selected");
					JSONArray ja13 = joo5.getJSONArray("areaList");
					pDtypeInfo.areaList = new ArrayList<>();
					for(int i=0;i<ja13.length();i++){

						pDtypeInfo.areaList.add(ja13.getString(i));

					}
					JSONArray ja12 = joo5.getJSONArray("channelGroup");
					pDtypeInfo.channelGroup = new ArrayList<>();
					for(int i=0;i<ja12.length();i++){
						pDtypeInfo.channelGroup.add(ja12.getString(i));
					}

					info.permissionAccess.sSlvInfo=ss;
					info.permissionAccess.pDssInfo=pDssInfo;
					info.permissionAccess.pDzsInfo=pDzsInfo;
					info.permissionAccess.zBjiemInfo=zBjiemInfo;
					info.permissionAccess.pDtypeInfo=pDtypeInfo;

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
