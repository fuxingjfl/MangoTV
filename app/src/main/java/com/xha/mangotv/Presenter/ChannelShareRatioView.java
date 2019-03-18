package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface ChannelShareRatioView {

	String getCSRUrl();
	
	int getCSRCode();
	
	String getCSRBody();
	
	//数据返回的错误码
	void getCSRDataFailureMsg(String msg);
	
	void getCSRData(Info info);
	
}
