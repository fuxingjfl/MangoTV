package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface LiShiView {

	String getLSUrl();
	
	int getLSCode();
	
	String getLSBody();
	
	//数据返回的错误码
	void getLSDataFailureMsg(String msg);
	
	void getLSData(Info info);
	
}
