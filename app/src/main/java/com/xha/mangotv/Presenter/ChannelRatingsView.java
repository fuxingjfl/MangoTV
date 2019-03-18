package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface ChannelRatingsView {

	String getCRUrl();
	
	int getCRCode();
	
	String getCRBody();
	
	//数据返回的错误码
	void getCRDataFailureMsg(String msg);
	
	void getCRData(Info info);
	
}
