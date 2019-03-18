package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.ChannelGroupImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.PhoneYZImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class ChannelGroupPresenter {
	private Handler handler;

	private ChannelGroupView channelGroupView;

	private DataModel dataModel;

	public ChannelGroupPresenter(ChannelGroupView channelGroupView){
		this.channelGroupView = channelGroupView;
		handler = new Handler();
		dataModel = new ChannelGroupImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(channelGroupView.getCGUrl(), channelGroupView.getCGCode(), channelGroupView.getCGBody(), new DataModel.onDataListener() {
			
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

							channelGroupView.getCGData(info);
							
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
						channelGroupView.getCGDataFailureMsg(log);
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
						channelGroupView.getCGDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
