package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface AddContrastChannelView {

	String getCCUrl();
	
	int getCCCode();
	
	String getCCBody();
	
	//数据返回的错误码
	void getCCDataFailureMsg(String msg);
	
	void getCCData(Info info);
	
}
