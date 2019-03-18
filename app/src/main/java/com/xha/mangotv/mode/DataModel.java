package com.xha.mangotv.mode;

import java.util.HashMap;

public interface DataModel {

	
	void getData(String url, int code,
				 String json, onDataListener onDataListener);
	
	
	public interface onDataListener{
		
		void onSuccessListener(Object string, int code);
		
		void onFailureListener(String log);
		
		
		void onFailureCodeListener(String log, int code);
		
	}
	
	
}
