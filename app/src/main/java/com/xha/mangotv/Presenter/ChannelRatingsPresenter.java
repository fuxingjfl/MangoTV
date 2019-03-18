package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.ChannelRatingsImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class ChannelRatingsPresenter {
	private Handler handler;

	private ChannelRatingsView channelRatingsView;

	private DataModel dataModel;

	public ChannelRatingsPresenter(ChannelRatingsView channelRatingsView){
		this.channelRatingsView = channelRatingsView;
		handler = new Handler();
		dataModel = new ChannelRatingsImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(channelRatingsView.getCRUrl(), channelRatingsView.getCRCode(), channelRatingsView.getCRBody(), new DataModel.onDataListener() {
			
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

							channelRatingsView.getCRData(info);
							
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
						channelRatingsView.getCRDataFailureMsg(log);
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
						channelRatingsView.getCRDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
