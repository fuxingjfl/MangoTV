package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface IsSigninView {

	String getisLoginUrl();
	
	int getisLoginCode();
	
	String getisLoginBody();
	
	//数据返回的错误码
	void getisLoginDataFailureMsg(String msg);
	
	void getisLoginData(Info info);
	
}
