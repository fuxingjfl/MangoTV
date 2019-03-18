package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.PermissionAccessImpl;
import com.xha.mangotv.mode.RealTimeDataImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class PermissionAccessPresenter {
	private Handler handler;

	private PermissionAccessView permissionAccessView;

	private DataModel dataModel;

	public PermissionAccessPresenter(PermissionAccessView permissionAccessView){
		this.permissionAccessView = permissionAccessView;
		handler = new Handler();
		dataModel = new PermissionAccessImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(permissionAccessView.getPAUrl(), permissionAccessView.getPACode(), permissionAccessView.getPABody(), new DataModel.onDataListener() {
			
			@Override
			public void onSuccessListener(Object string, int code) {
				// TODO Auto-generated method stub
				
				switch(code){
				case HttpConstants.search_news01:
					
					final Info info =(Info) string;
					
					handler .post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub

							permissionAccessView.getPAData(info);
							
						}
					});
					break;
				}
			}
			
			@Override
			public void onFailureListener(final String log) {
				// TODO Auto-generated method stub
				handler .post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						permissionAccessView.getPADataFailureMsg(log);
					}
				});
			}
			
			@Override
			public void onFailureCodeListener(final String log, int code) {
				// TODO Auto-generated method stub 
				handler .post(new Runnable() {
	
					@Override
					public void run() {
						// TODO Auto-generated method stub
						permissionAccessView.getPADataFailureMsg(log);
					}
				});
			}
		});
	}
}
