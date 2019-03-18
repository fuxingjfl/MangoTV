package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.SpecificChannelImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class SpecificChannelPresenter {
	private Handler handler;

	private SpecificChannelView specificChannelView;

	private DataModel dataModel;

	public SpecificChannelPresenter(SpecificChannelView specificChannelView){
		this.specificChannelView = specificChannelView;
		handler = new Handler();
		dataModel = new SpecificChannelImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(specificChannelView.getSCUrl(), specificChannelView.getSCCode(), specificChannelView.getSCBody(), new DataModel.onDataListener() {
			
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

							specificChannelView.getSCData(info);
							
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
						specificChannelView.getSCDataFailureMsg(log);
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
						specificChannelView.getSCDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
