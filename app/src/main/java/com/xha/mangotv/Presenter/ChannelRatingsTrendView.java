package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface ChannelRatingsTrendView {

	String getCRTUrl();
	
	int getCRTCode();
	
	String getCRTBody();
	
	//数据返回的错误码
	void getCRTDataFailureMsg(String msg);
	
	void getCRTData(Info info);
	
}
