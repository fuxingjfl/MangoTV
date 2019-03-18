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
import android.widget.Toast;

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
public class CalendarSRPop extends PopupWindow {
	private int resId;
	private Context context;
	private LayoutInflater inflater;
	public View defaultView;
	private LinearLayout single_day,ll_time_duan,day_duan,time_duan;
	private View single_day_view,day_duan_view,time_duan_view;
	private LinearLayout ll_sjd;
	private GridCalendarView gridCalendarView;

	private TextView tv_qx_pop,tv_qr_pop,tv_rl_content;
	private SelectorListener selectorListener;
	private int isd=0;
	private LinearLayout ll_sg;
	private TextView tv_time_qt,tv_time_wj,tv_time_hj;

	private String dayRange,js;

	private String timeRange="00:00:00-23:59:59";
	private boolean isqh=true;
	private String text;
	private TextView tv_dq;
	public CalendarSRPop(Context context, int resId
					, SelectorListener selectorListener  ,String text ) {
		super(context);
		this.context = context;
		this.resId = resId;
		this.selectorListener=selectorListener;
		this.text=text;
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
		time_duan=defaultView.findViewById(R.id.time_duan);
		ll_sg = defaultView.findViewById(R.id.ll_sg);
		tv_time_qt=defaultView.findViewById(R.id.tv_time_qt);
		tv_time_wj=defaultView.findViewById(R.id.tv_time_wj);
		tv_time_hj=defaultView.findViewById(R.id.tv_time_hj);
		single_day_view = defaultView.findViewById(R.id.single_day_view);
		day_duan_view=defaultView.findViewById(R.id.day_duan_view);
		tv_dq=defaultView.findViewById(R.id.tv_dq);
		time_duan_view=defaultView.findViewById(R.id.time_duan_view);
		tv_rl_content=defaultView.findViewById(R.id.tv_rl_content);
		ll_sjd = defaultView.findViewById(R.id.ll_sjd);
		tv_qx_pop=defaultView.findViewById(R.id.tv_qx_pop);
		tv_qr_pop=defaultView.findViewById(R.id.tv_qr_pop);
		tv_rl_content.setText(text);
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
		time_duan.setOnClickListener(onClickListener);
		tv_qx_pop.setOnClickListener(onClickListener);
		tv_qr_pop.setOnClickListener(onClickListener);
		ll_sg.setOnClickListener(onClickListener);
		tv_time_qt.setOnClickListener(onClickListener);
		tv_time_hj.setOnClickListener(onClickListener);
		tv_time_wj.setOnClickListener(onClickListener);
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
				case R.id.time_duan:
					single_day_view.setVisibility(View.INVISIBLE);
					day_duan_view.setVisibility(View.INVISIBLE);
					time_duan_view.setVisibility(View.VISIBLE);
					ll_sjd.setVisibility(View.VISIBLE);
					gridCalendarView.setVisibility(View.GONE);
//					if(timeDuanFragment==null){
//						timeDuanFragment = new TimeDuanFragment();
//					}
//					addFragments(timeDuanFragment);
					isd=2;
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
						selectorListener.onSelectorDRListener(dayRange+":"+dayRange,timeRange);
					}else if(isd==1){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (dayRange==null){
							dayRange=sdf.format(new Date());
						}
						if (js==null){
							js=sdf.format(new Date());
							Log.e("TAG","不动的时间::"+js);
						}
						//将字符串形式的时间转化为Date类型的时间

						try {
							Date a= null;
							a = sdf.parse(dayRange);
							Date b=sdf.parse(js);
							//Date类的一个方法，如果a早于b返回true，否则返回false
							if(a.before(b)) {

								selectorListener.onSelectorDRListener(dayRange+":"+js,timeRange);

							}else{
								selectorListener.onSelectorDRListener(js+":"+dayRange,timeRange);
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}

					}else if(isd==2){

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (dayRange==null){
							dayRange=sdf.format(new Date());
						}
						if (js==null){
							js=sdf.format(new Date());
							Log.e("TAG","不动的时间::"+js);
						}

						try {

							if (isqh){
								Log.e("TAG","执行了单日");
								selectorListener.onSelectorTimeListener(dayRange+":"+dayRange,timeRange);

							}else{
								Log.e("TAG","执行了多日");
								Date a= null;
								a = sdf.parse(dayRange);
								Date b=sdf.parse(js);
								//Date类的一个方法，如果a早于b返回true，否则返回false
								if(a.before(b)) {

									selectorListener.onSelectorTimeListener(dayRange+":"+js,timeRange);

								}else{
									selectorListener.onSelectorTimeListener(js+":"+dayRange,timeRange);
								}


							}



						} catch (ParseException e) {
							e.printStackTrace();
						}


					}

					break;
				case R.id.ll_sg:
					selectorListener.onCancelListener();
					break;
				case R.id.tv_time_qt:

					tv_time_qt.setBackgroundResource(R.drawable.shape_rl_xz);
					tv_time_hj.setBackgroundResource(R.drawable.shape_rl_hs);
					tv_time_wj.setBackgroundResource(R.drawable.shape_rl_hs);

					timeRange="00:00:00-23:59:59";

					break;
				case R.id.tv_time_hj:

					tv_time_qt.setBackgroundResource(R.drawable.shape_rl_hs);
					tv_time_hj.setBackgroundResource(R.drawable.shape_rl_xz);
					tv_time_wj.setBackgroundResource(R.drawable.shape_rl_hs);

					timeRange="19:30:00-22:00:00";
					break;
				case R.id.tv_time_wj:

					tv_time_qt.setBackgroundResource(R.drawable.shape_rl_hs);
					tv_time_hj.setBackgroundResource(R.drawable.shape_rl_hs);
					tv_time_wj.setBackgroundResource(R.drawable.shape_rl_xz);
					timeRange="18:00:00-23:59:59";
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
		void onSelectorDRListener(String ks,String sj);

		void onSelectorTimeListener(String day,String sj);

		void onCancelListener();

	}

	public void  setText(String text){
		tv_rl_content.setText(text);
	}

	public void setNameText(String text){
		tv_dq.setText(text);
	}

}
