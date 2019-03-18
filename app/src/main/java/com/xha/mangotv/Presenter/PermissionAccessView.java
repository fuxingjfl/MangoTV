package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface PermissionAccessView {

	String getPAUrl();
	
	int getPACode();
	
	String getPABody();
	
	//数据返回的错误码
	void getPADataFailureMsg(String msg);
	
	void getPAData(Info info);
	
}
