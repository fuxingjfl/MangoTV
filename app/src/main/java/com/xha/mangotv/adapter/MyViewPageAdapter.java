package com.xha.mangotv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.xha.mangotv.base.BaseFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ysq on 2018/8/31.
 */

public class MyViewPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;
    private List<String> name;
    private Map<Integer, Fragment> map = new HashMap<Integer, Fragment>();

    public MyViewPageAdapter(FragmentManager fm, List<BaseFragment> list,List<String> name) {
        super(fm);
        this.list=list;
        this.name=name;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return name.get(position);
    }


//    @Override
//    public int getItemPosition(Object object) {
//
//        return MyViewPageAdapter.POSITION_NONE;
//
//    }
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        //记录每个position位置最后显示的Fragment
//        map.put(position, fragment);
//        return fragment;
//    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//    }

}
