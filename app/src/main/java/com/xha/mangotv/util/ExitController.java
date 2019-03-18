package com.xha.mangotv.util;

import android.os.Handler;

public class ExitController {

	private boolean isflag;
	
	
	public void ChangState(){
		
		isflag=true;
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				isflag=false;
				
			}
		}, 2000);
		
		
	}
	
	
	public boolean isFlag(){
		return isflag;
	}
	

}
