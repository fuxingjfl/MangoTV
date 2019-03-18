package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.PhoneYZImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class PhoneYZPresenter {
	private Handler handler;
	
	private PhoneYZView phoneYZView;
	
	private DataModel dataModel;
	
	public PhoneYZPresenter(PhoneYZView phoneYZView){
		this.phoneYZView = phoneYZView;
		handler = new Handler();
		dataModel = new PhoneYZImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(phoneYZView.getPhoneYZUrl(), phoneYZView.getPhoneYZCode(), phoneYZView.getPhoneYZBody(), new DataModel.onDataListener() {
			
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
							
							phoneYZView.getPhoneYZData(info);
							
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
						phoneYZView.getPhoneYZDataFailureMsg(log);
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
						phoneYZView.getPhoneYZDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
