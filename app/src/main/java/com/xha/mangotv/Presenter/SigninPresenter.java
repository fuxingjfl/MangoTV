package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.IsSigninImpl;
import com.xha.mangotv.mode.SigninImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class SigninPresenter {
	private Handler handler;

	private SigninView signinView;

	private DataModel dataModel;

	public SigninPresenter(SigninView signinView){
		this.signinView = signinView;
		handler = new Handler();
		dataModel = new SigninImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(signinView.getdlUrl(), signinView.getdlCode(), signinView.getdlBody(), new DataModel.onDataListener() {
			
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

							signinView.getdlData(info);
							
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
						signinView.getdlDataFailureMsg(log);
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
						signinView.getdlDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
