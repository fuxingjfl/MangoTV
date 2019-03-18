package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.ChannelShareRatioImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class ChannelShareRatioPresenter {
	private Handler handler;

	private ChannelShareRatioView channelShareRatioView;

	private DataModel dataModel;

	public ChannelShareRatioPresenter(ChannelShareRatioView channelShareRatioView){
		this.channelShareRatioView = channelShareRatioView;
		handler = new Handler();
		dataModel = new ChannelShareRatioImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(channelShareRatioView.getCSRUrl(), channelShareRatioView.getCSRCode(), channelShareRatioView.getCSRBody(), new DataModel.onDataListener() {
			
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

							channelShareRatioView.getCSRData(info);
							
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
						channelShareRatioView.getCSRDataFailureMsg(log);
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
						channelShareRatioView.getCSRDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
