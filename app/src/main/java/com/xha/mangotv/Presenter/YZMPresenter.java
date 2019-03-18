package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.YZMImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class YZMPresenter {
	private Handler handler;

	private YZMView yzmView;

	private DataModel dataModel;

	public YZMPresenter(YZMView yzmView){
		this.yzmView = yzmView;
		handler = new Handler();
		dataModel = new YZMImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(yzmView.getYZMUrl(), yzmView.getYZMCode(), yzmView.getYZMBody(), new DataModel.onDataListener() {
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

							yzmView.getYZMData(info);
							
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
						yzmView.getYZMDataFailureMsg(log);
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
						yzmView.getYZMDataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
