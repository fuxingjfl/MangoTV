package com.xha.mangotv.Presenter;

import android.os.Handler;

import com.xha.mangotv.entity.Info;
import com.xha.mangotv.mode.AddressImpl;
import com.xha.mangotv.mode.DataModel;
import com.xha.mangotv.mode.PhoneYZImpl;
import com.xha.mangotv.okhttpUtils.HttpConstants;


public class AddressPresenter {
	private Handler handler;

	private AddressView addressView;

	private DataModel dataModel;

	public AddressPresenter(AddressView addressView){
		this.addressView = addressView;
		handler = new Handler();
		dataModel = new AddressImpl();
	}
	
	public void getDisposeData(){
		dataModel.getData(addressView.getAUrl(), addressView.getACode(), addressView.getABody(), new DataModel.onDataListener() {
			
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

							addressView.getAData(info);
							
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
						addressView.getADataFailureMsg(log);
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
						addressView.getADataFailureMsg(log);
					}
				});
			}
		});
	}
	
}
