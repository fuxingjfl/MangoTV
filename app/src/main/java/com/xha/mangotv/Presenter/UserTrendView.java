package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface UserTrendView {

	String getUTUrl();
	
	int getUTCode();
	
	String getUTBody();
	
	//数据返回的错误码
	void getUTDataFailureMsg(String msg);
	
	void getUTData(Info info);
	
}
