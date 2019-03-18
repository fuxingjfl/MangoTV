package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddContrastChannelImpl;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class AddContrastChannelPresenter {
	private Handler handler;

	private AddContrastChannelView addContrastChannelView;

	private DataModel dataModel;

	public AddContrastChannelPresenter(AddContrastChannelView addContrastChannelView){
		this.addContrastChannelView = addContrastChannelView;
		handler = new Handler();
		dataModel = new AddContrastChannelImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(addContrastChannelView.getCCUrl(), addContrastChannelView.getCCCode(), addContrastChannelView.getCCBody(), new DataModel.onDataListener() {
			
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

							addContrastChannelView.getCCData(info);
							
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
						addContrastChannelView.getCCDataFailureMsg(log);
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
						addContrastChannelView.getCCDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
