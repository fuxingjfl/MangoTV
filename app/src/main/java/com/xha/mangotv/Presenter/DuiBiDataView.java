package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface DuiBiDataView {

	String getDBUrl();
	
	int getDBCode();
	
	String getDBBody();
	
	//数据返回的错误码
	void getDBDataFailureMsg(String msg);
	
	void getDBData(Info info);
	
}
