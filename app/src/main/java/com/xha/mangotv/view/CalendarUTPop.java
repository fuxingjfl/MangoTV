package com.xha.mangotv.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.views.GridCalendarView;
import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.fragment.DayDuanFragment;
import com.xha.mangotv.fragment.SingledayFragment;
import com.xha.mangotv.fragment.TimeDuanFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author yaguang.wang
 * 
 */
public class CalendarUTPop extends PopupWindow {
	private int resId;
	private Context context;
	private LayoutInflater inflater;
	public View defaultView;
	private LinearLayout single_day,day_duan;
	private View single_day_view,day_duan_view;
	private BaseFragment currentf;
	private FragmentManager supportFragmentManager;
	private SingledayFragment singledayFragment;
	private DayDuanFragment dayDuanFragment;
	private TimeDuanFragment timeDuanFragment;
	private GridCalendarView gridCalendarView;

	private TextView tv_qx_pop,tv_qr_pop,tv_tiem;
	private SelectorListener selectorListener;
	private int isd=0;
	private LinearLayout ll_sg;
	private String dayRange,js;
	private boolean isqh=true;
	private TextView tv_dq;
	private String time;
	public CalendarUTPop(Context context, int resId
					, SelectorListener selectorListener , String time  ) {
		super(context);
		this.context = context;
		this.resId = resId;
		this.supportFragmentManager=supportFragmentManager;
		this.selectorListener=selectorListener;
		this.time=time;
		initPopupWindow();
	}

	public void initPopupWindow() {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		defaultView = inflater.inflate(this.resId, null);
		defaultView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setContentView(defaultView);
		single_day = defaultView.findViewById(R.id.single_day);
		day_duan=defaultView.findViewById(R.id.day_duan);
		tv_tiem=defaultView.findViewById(R.id.tv_tiem);
		ll_sg = defaultView.findViewById(R.id.ll_sg);
		tv_dq=defaultView.findViewById(R.id.tv_dq);
		single_day_view = defaultView.findViewById(R.id.single_day_view);
		day_duan_view=defaultView.findViewById(R.id.day_duan_view);

		tv_qx_pop=defaultView.findViewById(R.id.tv_qx_pop);
		tv_qr_pop=defaultView.findViewById(R.id.tv_qr_pop);
		tv_tiem.setText(time);

//		if(singledayFragment==null){
//			singledayFragment = new SingledayFragment();
//		}
//		addFragments(singledayFragment);

		gridCalendarView = (GridCalendarView )defaultView. findViewById(R.id.gridMonthView);
		gridCalendarView.setVisibility(View.VISIBLE);
		gridCalendarView.setDateClick(new MonthView.IDateClick(){
			@Override
			public void onClickOnDate(int year, int month, int day) {
				String r=String.valueOf(day);
				String y=String.valueOf(month);
				if (r.length()<=1){
					r="0"+r;
				}
				if (y.length()<=1){
					y="0"+y;
				}

				dayRange=year+"-"+y+"-"+r;
			}

			@Override
			public void onEndClickOnDate(int year, int month, int day) {

			}
		},false);

		List<CalendarInfo> list = new ArrayList<CalendarInfo>();
		list.add(new CalendarInfo(2016,7,4,"￥1200"));
		list.add(new CalendarInfo(2016,7,6,"￥1200"));
		list.add(new CalendarInfo(2016,7,12,"￥1200"));
		list.add(new CalendarInfo(2016,7,16,"￥1200"));
		list.add(new CalendarInfo(2016,7,28,"￥1200"));
		list.add(new CalendarInfo(2016,7,1,"￥1200",1));
		list.add(new CalendarInfo(2016,7,11,"￥1200",1));
		list.add(new CalendarInfo(2016,7,19,"￥1200",2));
		list.add(new CalendarInfo(2016,7,21,"￥1200",1));
		gridCalendarView.setCalendarInfos(list);
		single_day.setOnClickListener(onClickListener);
		day_duan.setOnClickListener(onClickListener);
		tv_qx_pop.setOnClickListener(onClickListener);
		tv_qr_pop.setOnClickListener(onClickListener);
		ll_sg.setOnClickListener(onClickListener);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
//		 setAnimationStyle(R.style.popwin_anim_style);
//		setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
		setFocusable(false);
		setOutsideTouchable(false);
		update();

	}


	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.single_day:
					isqh=true;
					single_day_view.setVisibility(View.VISIBLE);
					day_duan_view.setVisibility(View.INVISIBLE);
					gridCalendarView.setVisibility(View.VISIBLE);
//					if(singledayFragment==null){
//						singledayFragment = new SingledayFragment();
//					}
//					addFragments(singledayFragment);
					isd=0;
					gridCalendarView.setDateClick(new MonthView.IDateClick(){
						@Override
						public void onClickOnDate(int year, int month, int day) {
							String r=String.valueOf(day);
							String y=String.valueOf(month);
							if (r.length()<=1){
								r="0"+r;
							}
							if (y.length()<=1){
								y="0"+y;
							}
							dayRange=year+"-"+y+"-"+r;
						}
						@Override
						public void onEndClickOnDate(int year, int month, int day) {

						}
					},false);
					break;
				case R.id.day_duan:
					isqh=false;
					single_day_view.setVisibility(View.INVISIBLE);
					day_duan_view.setVisibility(View.VISIBLE);
					gridCalendarView.setVisibility(View.VISIBLE);
//					if(dayDuanFragment==null){
//						dayDuanFragment = new DayDuanFragment();
//					}
//					addFragments(dayDuanFragment);
					isd=1;
					gridCalendarView.setDateClick(new MonthView.IDateClick(){
						@Override
						public void onClickOnDate(int year, int month, int day) {

							String r=String.valueOf(day);
							String y=String.valueOf(month);
							if (r.length()<=1){
								r="0"+r;
							}
							if (y.length()<=1){
								y="0"+y;
							}

							dayRange=year+"-"+y+"-"+r;
						}
						@Override
						public void onEndClickOnDate(int year, int month, int day) {

							String r=String.valueOf(day);
							String y=String.valueOf(month);
							if (r.length()<=1){
								r="0"+r;
							}
							if (y.length()<=1){
								y="0"+y;
							}
							js=year+"-"+y+"-"+r;
						}
					},true);
					break;

				case R.id.tv_qx_pop:

					selectorListener.onCancelListener();

					break;
				case R.id.tv_qr_pop:

					if(isd==0){

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (dayRange==null){
							dayRange=sdf.format(new Date());
						}


						selectorListener.onSelectorDRListener(dayRange+":"+dayRange,true);

					}else if(isd==1) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (dayRange==null){
							dayRange = sdf.format(new Date());
						}

						if (js==null){
							js = sdf.format(new Date());
							Log.e("TAG", "不动的时间::" + js);
						}



						//将字符串形式的时间转化为Date类型的时间

						try {
							Date a = null;
							a = sdf.parse(dayRange);
							Date b = sdf.parse(js);
							//Date类的一个方法，如果a早于b返回true，否则返回false
							if (a.before(b)) {

								selectorListener.onSelectorDRListener(dayRange + ":" + js,false);

							} else {
								selectorListener.onSelectorDRListener(js + ":" + dayRange,false);

							}

						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					break;
				case R.id.ll_sg:
					selectorListener.onCancelListener();
					break;
			}
		}
	};



//	private void addFragments(BaseFragment f) {
//
//		// 第二步：开启一个事务
//		FragmentTransaction transaction = supportFragmentManager.beginTransaction();
//
//		if (currentf != null) {
//			//每次把前一个fragment给隐藏了
//			transaction.hide(currentf);
//		}
//		//isAdded:判断当前的fragment对象是否被加载过
//		if (!f.isAdded()) {
//			// 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
//			transaction.add(R.id.fl_pop, f);
//		}
//		//显示当前的fragment
//		transaction.show(f);
//		// 第四步：提交
//		transaction.commit();
//		currentf = f;
//	}

	/**
	 * 
	 * @return pop的View
	 */
	public View getDefaultView() {
		return defaultView;
	}


	public interface SelectorListener{
		void onSelectorDRListener(String ks,boolean isdx);
		void onCancelListener();
	}

	public void setText(String text){
		tv_tiem.setText(text);
	}

	public void setNameText(String text){
		tv_dq.setText(text);
	}

}
