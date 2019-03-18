package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface YZMView {

	String getYZMUrl();
	
	int getYZMCode();
	
	String getYZMBody();
	
	//数据返回的错误码
	void getYZMDataFailureMsg(String msg);
	
	void getYZMData(Info info);
	
}
