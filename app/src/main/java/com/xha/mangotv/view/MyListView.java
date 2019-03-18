package com.xha.mangotv.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public MyListView(Context context, AttributeSet attrs, int defStyleAttr,
					  int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		
		super.onMeasure(widthMeasureSpec, makeMeasureSpec);
	}
}
