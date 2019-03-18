package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface RegisterView {

	String getRUrl();
	
	int getRCode();
	
	String getRBody();
	
	//数据返回的错误码
	void getRDataFailureMsg(String msg);
	
	void getRData(Info info);
	
}
