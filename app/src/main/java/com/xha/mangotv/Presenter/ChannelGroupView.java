package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface ChannelGroupView {

	String getCGUrl();
	
	int getCGCode();
	
	String getCGBody();
	
	//数据返回的错误码
	void getCGDataFailureMsg(String msg);
	
	void getCGData(Info info);
	
}
