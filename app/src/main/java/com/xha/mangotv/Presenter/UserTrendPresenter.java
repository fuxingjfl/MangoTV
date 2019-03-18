package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.UserTrendImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class UserTrendPresenter {
	private Handler handler;

	private UserTrendView userTrendView;

	private DataModel dataModel;

	public UserTrendPresenter(UserTrendView userTrendView){
		this.userTrendView = userTrendView;
		handler = new Handler();
		dataModel = new UserTrendImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(userTrendView.getUTUrl(), userTrendView.getUTCode(), userTrendView.getUTBody(), new DataModel.onDataListener() {
			
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

							userTrendView.getUTData(info);
							
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
						userTrendView.getUTDataFailureMsg(log);
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
						userTrendView.getUTDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
