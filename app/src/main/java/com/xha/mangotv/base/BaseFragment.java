package com.xha.mangotv.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xha.mangotv.config.Config;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.RSAUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 * Created by yuandl on 2016-11-17.
 * blog ：http://blog.csdn.net/linglongxin24/article/details/53205878
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    private View view;
    public SharedPreferences sp;
    protected Context mContext;
    protected Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG","==================创建碎片了==============");
        view = inflater.inflate(setContentView(), container, false);
        isInit = true;
        mContext = getContext();//主要是方便子类调用
        mActivity = getActivity();
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return view;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化setContentView
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;

    }

    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {

        return (T) getContentView().findViewById(id);
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    public float getpx(int dp){
        return dp*getResources().getDisplayMetrics().density;
    }


    protected String getSecret() {
        String RSASign = PreUtil.getInstance().getString(PreContact.SIGN);
        String data=null;
        try {
            data=new String(RSAUtil.decrypt(RSAUtil.loadPrivateKey(Config.PRIVATEKEY), Base64.decode(RSASign, Base64.DEFAULT)));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     * @param year
     * @param monthOfYear
     * @return
     */ public Date getSupportEndDayofMonth(int year, int monthOfYear) { Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year); cal.set(Calendar.MONTH, monthOfYear); cal.set(Calendar.DAY_OF_MONTH, 1); cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59); cal.set(Calendar.SECOND, 59); cal.add(Calendar.DAY_OF_MONTH, -1); Date lastDate = cal.getTime(); cal.set(Calendar.DAY_OF_MONTH, 1); Date firstDate = cal.getTime(); return lastDate; }


}

