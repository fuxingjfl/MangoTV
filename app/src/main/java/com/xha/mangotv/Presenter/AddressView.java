package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface AddressView {

	String getAUrl();
	
	int getACode();
	
	String getABody();
	
	//数据返回的错误码
	void getADataFailureMsg(String msg);
	
	void getAData(Info info);
	
}
