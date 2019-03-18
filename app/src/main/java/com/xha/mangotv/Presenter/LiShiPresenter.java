package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.LiShiImpl;
import com.xha.mangotv.mode.RealTimeDataImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class LiShiPresenter {
	private Handler handler;

	private LiShiView liShiView;

	private DataModel dataModel;

	public LiShiPresenter(LiShiView liShiView){
		this.liShiView = liShiView;
		handler = new Handler();
		dataModel = new LiShiImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(liShiView.getLSUrl(), liShiView.getLSCode(), liShiView.getLSBody(), new DataModel.onDataListener() {
			
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

							liShiView.getLSData(info);
							
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
						liShiView.getLSDataFailureMsg(log);
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
						liShiView.getLSDataFailureMsg(log);
					}
				});
			}
		});
	}
}
