package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface LiveTVView {

	String getTVUrl();
	
	int getTVCode();
	
	String getTVBody();
	
	//数据返回的错误码
	void getTVDataFailureMsg(String msg);
	
	void getTVData(Info info);
	
}
