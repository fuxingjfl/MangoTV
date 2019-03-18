package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface SigninView {

	String getdlUrl();
	
	int getdlCode();
	
	String getdlBody();
	
	//数据返回的错误码
	void getdlDataFailureMsg(String msg);
	
	void getdlData(Info info);
	
}
