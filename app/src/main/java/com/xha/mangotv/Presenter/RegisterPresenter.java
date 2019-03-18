package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.RegisterImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class RegisterPresenter {
	private Handler handler;

	private RegisterView registerView;

	private DataModel dataModel;

	public RegisterPresenter(RegisterView registerView){
		this.registerView = registerView;
		handler = new Handler();
		dataModel = new RegisterImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(registerView.getRUrl(), registerView.getRCode(), registerView.getRBody(), new DataModel.onDataListener() {
			
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

							registerView.getRData(info);
							
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
						registerView.getRDataFailureMsg(log);
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
						registerView.getRDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
