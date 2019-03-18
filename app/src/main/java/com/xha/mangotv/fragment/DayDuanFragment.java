package com.xha.mangotv.fragment;

import android.view.View;
import android.widget.Toast;

import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.views.GridCalendarView;
import com.xha.mangotv.R;
import com.xha.mangotv.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期段
 *
 * Created by ysq on 2018/9/4.
 */

public class DayDuanFragment extends BaseFragment {
    @Override
    protected int setContentView() {
        return R.layout.fragment_singleday;
    }

    @Override
    protected void lazyLoad() {
        /*View contentView = getContentView();
        GridCalendarView gridCalendarView = (GridCalendarView )contentView. findViewById(R.id.gridMonthView);
        gridCalendarView.setDateClick(new MonthView.IDateClick(){
            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(getActivity(),"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });

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
        gridCalendarView.setCalendarInfos(list);*/
    }
}
