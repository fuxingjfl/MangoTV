package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.InflowOutflowLsImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class InflowOutflowLsPresenter {
	private Handler handler;

	private InflowOutflowLsView inflowOutflowLsView;

	private DataModel dataModel;

	public InflowOutflowLsPresenter(InflowOutflowLsView inflowOutflowLsView){
		this.inflowOutflowLsView = inflowOutflowLsView;
		handler = new Handler();
		dataModel = new InflowOutflowLsImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(inflowOutflowLsView.getIOUrl(), inflowOutflowLsView.getIOCode(), inflowOutflowLsView.getIOBody(), new DataModel.onDataListener() {
			
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

							inflowOutflowLsView.getIOData(info);
							
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
						inflowOutflowLsView.getIODataFailureMsg(log);
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
						inflowOutflowLsView.getIODataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
