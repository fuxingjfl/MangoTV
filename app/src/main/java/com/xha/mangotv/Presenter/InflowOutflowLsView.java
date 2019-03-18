package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface InflowOutflowLsView {

	String getIOUrl();
	
	int getIOCode();
	
	String getIOBody();
	
	//数据返回的错误码
	void getIODataFailureMsg(String msg);
	
	void getIOData(Info info);
	
}
