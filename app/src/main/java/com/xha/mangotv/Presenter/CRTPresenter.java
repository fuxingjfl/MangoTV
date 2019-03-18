package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.ChannelRatingsTrendImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class CRTPresenter {
	private Handler handler;

	private ChannelRatingsTrendView channelRatingsTrendView;

	private DataModel dataModel;

	public CRTPresenter(ChannelRatingsTrendView channelRatingsTrendView){
		this.channelRatingsTrendView = channelRatingsTrendView;
		handler = new Handler();
		dataModel = new ChannelRatingsTrendImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(channelRatingsTrendView.getCRTUrl(), channelRatingsTrendView.getCRTCode(), channelRatingsTrendView.getCRTBody(), new DataModel.onDataListener() {
			
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

							channelRatingsTrendView.getCRTData(info);
							
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
						channelRatingsTrendView.getCRTDataFailureMsg(log);
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
						channelRatingsTrendView.getCRTDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
