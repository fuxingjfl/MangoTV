package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.DuiBiDataImpl;
import com.xha.mangotv.mode.RealTimeDataImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class DuiBiDataPresenter {
	private Handler handler;

	private DuiBiDataView duiBiDataView;

	private DataModel dataModel;

	public DuiBiDataPresenter(DuiBiDataView duiBiDataView){
		this.duiBiDataView = duiBiDataView;
		handler = new Handler();
		dataModel = new DuiBiDataImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(duiBiDataView.getDBUrl(), duiBiDataView.getDBCode(), duiBiDataView.getDBBody(), new DataModel.onDataListener() {
			
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

							duiBiDataView.getDBData(info);
							
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
						duiBiDataView.getDBDataFailureMsg(log);
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
						duiBiDataView.getDBDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
