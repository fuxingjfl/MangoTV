package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.LiveTVImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class LiveTVPresenter {
	private Handler handler;

	private LiveTVView liveTVView;

	private DataModel dataModel;

	public LiveTVPresenter(LiveTVView liveTVView){
		this.liveTVView = liveTVView;
		handler = new Handler();
		dataModel = new LiveTVImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(liveTVView.getTVUrl(), liveTVView.getTVCode(), liveTVView.getTVBody(), new DataModel.onDataListener() {
			
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

							liveTVView.getTVData(info);
							
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
						liveTVView.getTVDataFailureMsg(log);
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
						liveTVView.getTVDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
