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
 * Created by ysq on 2018/9/4.
 */

public class SingledayFragment extends BaseFragment {

    @Override
    protected int setContentView() {
        return R.layout.fragment_singleday;
    }

    @Override
    protected void lazyLoad() {

    }
}
