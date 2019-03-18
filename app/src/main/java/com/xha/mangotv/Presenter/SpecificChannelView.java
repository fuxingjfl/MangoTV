package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface SpecificChannelView {

	String getSCUrl();
	
	int getSCCode();
	
	String getSCBody();
	
	//数据返回的错误码
	void getSCDataFailureMsg(String msg);
	
	void getSCData(Info info);
	
}
