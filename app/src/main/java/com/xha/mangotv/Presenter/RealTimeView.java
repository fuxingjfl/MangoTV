package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface RealTimeView {

	String getRTUrl();
	
	int getRTCode();
	
	String getRTBody();
	
	//数据返回的错误码
	void getRTDataFailureMsg(String msg);
	
	void getRTData(Info info);
	
}
