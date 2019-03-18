package com.xha.mangotv.Presenter;


import com.xha.mangotv.entity.Info;

public interface PhoneYZView {

	String getPhoneYZUrl();
	
	int getPhoneYZCode();
	
	String getPhoneYZBody();
	
	//数据返回的错误码
	void getPhoneYZDataFailureMsg(String msg);
	
	void getPhoneYZData(Info info);
	
}
