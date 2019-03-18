package com.xha.mangotv.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.adapter.CommonAdapter;
import com.xha.mangotv.util.DensityUtil;

import java.util.ArrayList;

import static android.widget.LinearLayout.*;

/**
 * Created by ysq on 2018/8/31.
 */

public class HRecyclerView extends RelativeLayout {
    //头部title布局
    private LinearLayout mRightTitleLayout;
    //手指按下时的位置
    private float mStartX = 0;
    //滑动时和按下时的差值
    private int mMoveOffsetX = 0;
    //最大可滑动差值
    private int mFixX = 0;
    //左边标题集合
    private String[] mLeftTextList;
    //左边标题的宽度集合
    private int[] mLeftTextWidthList;
    //右边标题集合
    private String[] mRightTitleList = new String[]{};
    //右边标题的宽度集合
    private int[] mRightTitleWidthList = null;
    //展示数据时使用的RecycleView
    private RecyclerView mRecyclerView;
    //RecycleView的Adapter
    private Object mAdapter;
    //需要滑动的View集合
    private ArrayList<View> mMoveViewList = new ArrayList();
    private Context context;
    //右边可滑动的总宽度
    private int mRightTotalWidth = 0;
    //右边单个view的宽度
    private int mRightItemWidth = 70;
    //左边view的宽度
    private int mLeftViewWidth = 60;
    //左边view的高度
    private int mLeftViewHeight=40;
    //触发拦截手势的最小值
    private int mTriggerMoveDis=30;

    public HRecyclerView(Context context) {
        this(context, null);
    }

    public HRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(VERTICAL);
        LinearLayout headLayout = (LinearLayout) createHeadLayout();
        linearLayout.addView(headLayout);
        linearLayout.addView(createMoveRecyclerView());
        addView(linearLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 创建头部布局
     * @return
     */
    private View createHeadLayout() {
        LinearLayout headLayout = new LinearLayout(getContext());
        headLayout.setGravity(Gravity.CENTER);
        if (mRightTitleList.length+1>5){
            LinearLayout leftLayout = new LinearLayout(getContext());
            addListHeaderTextView(mLeftTextList[0], mLeftTextWidthList[0], leftLayout,mRightTitleList.length+1);
            leftLayout.setGravity(Gravity.CENTER);
            headLayout.addView(leftLayout, 0, new ViewGroup.LayoutParams(DensityUtil.dip2px(context, mLeftViewWidth),DensityUtil.dip2px(context, mLeftViewHeight)));
            mRightTitleLayout = new LinearLayout(getContext());
            for (int i = 0; i < mRightTitleList.length; i++) {
                addListHeaderTextView(mRightTitleList[i], mRightTitleWidthList[i], mRightTitleLayout,mRightTitleList.length+1);
            }
            headLayout.addView(mRightTitleLayout);
            headLayout.setBackgroundResource(R.color.hs);
        }else
        if(mRightTitleList.length+1==5){
            setParameter(headLayout,5);
        }else if(mRightTitleList.length+1==4){
            setParameter(headLayout,4);
        }else if(mRightTitleList.length+1==3){
            setParameter(headLayout,3);
        }else if(mRightTitleList.length+1==2){
            setParameter(headLayout,2);
        }

        return headLayout;
    }

    private void setParameter(LinearLayout headLayout,int weightSum){
        LinearLayout.LayoutParams mparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        headLayout.setLayoutParams(mparams);
        headLayout.setGravity(Gravity.CENTER_VERTICAL);
        headLayout.setWeightSum(weightSum);
        headLayout.setPadding(0,0,0,0);
        LinearLayout leftLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0,DensityUtil.dip2px(context, mLeftViewHeight),1);
        leftLayout.setLayoutParams(params1);
        Log.e("TAG","mLeftTextList[0]===="+mLeftTextList[0]);
        addListHeaderTextView(mLeftTextList[0], mLeftTextWidthList[0], leftLayout,-1);
        leftLayout.setGravity(Gravity.CENTER);
//            headLayout.addView(leftLayout, 0, new ViewGroup.LayoutParams(DensityUtil.dip2px(context, mLeftViewWidth),DensityUtil.dip2px(context, mLeftViewHeight)));
        headLayout.addView(leftLayout);
        mRightTitleLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,weightSum-1);
        mRightTitleLayout.setLayoutParams(params2);
        mRightTitleLayout.setGravity(Gravity.CENTER);
        for (int i = 0; i < mRightTitleList.length; i++) {
            addListHeaderTextView(mRightTitleList[i], mRightTitleWidthList[i], mRightTitleLayout,i);
        }
        headLayout.addView(mRightTitleLayout);
        headLayout.setBackgroundResource(R.color.hs);
    }


    /**
     * 创建数据展示布局
     * @return
     */
    private View createMoveRecyclerView() {
        RelativeLayout linearLayout = new RelativeLayout(getContext());
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
        if(null !=mAdapter){
            if (mAdapter instanceof CommonAdapter) {
                mRecyclerView.setAdapter((CommonAdapter) mAdapter);
                mMoveViewList = ((CommonAdapter) mAdapter).getMoveViewList();
            }
        }

        linearLayout.addView(mRecyclerView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return linearLayout;
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(Object adapter) {
        mAdapter = adapter;
        initView();
    }

    /**
     * 设置头部title单个布局
     * @param headerName
     * @param width
     * @param leftLayout
     * @return
     */
    private TextView addListHeaderTextView(String headerName, int width, LinearLayout leftLayout,int i) {
        MyTextView textView = new MyTextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER);
        textView.setPadding(10,0,10,0);
        textView.setTextSize(11);
        if (i==-1){
            if (headerName.length()>=6){
                String substring1 = headerName.substring(0, 2);
                String substring2 = headerName.substring(2);
                headerName=substring1+"\n"+substring2;
            }
        }
        textView.setText(headerName);
        //70
        if (i>5){
            leftLayout.addView(textView, width, DensityUtil.dip2px(context, 50));
        }else{
            leftLayout.addView(textView);
        }
        return textView;
    }


    /**
     * 设置头部title单个布局
     * @param headerName
     * @param width
     * @param leftLayout
     * @return
     */
    private TextView NewaddListHeaderTextView(String headerName, int width, LinearLayout leftLayout,boolean pb) {
        TextView textView = new TextView(getContext());
        textView.setText(headerName);
        textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER);

        textView.setTextSize(11);
        leftLayout.addView(textView, width, DensityUtil.dip2px(context, 50));

        return textView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) Math.abs(ev.getX() - mStartX);
                if (offsetX > mTriggerMoveDis) {//水平移动大于30触发拦截
                    return true;
                } else {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 右边可滑动的总宽度
     * @return
     */
    private int rightTitleTotalWidth() {
        if (0 == mRightTotalWidth) {
            for (int i = 0; i < mRightTitleWidthList.length; i++) {
                mRightTotalWidth = mRightTotalWidth + mRightTitleWidthList[i];
            }
        }
        return mRightTotalWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:

                if(getHeaderCount()>5){
                    int offsetX = (int) Math.abs(event.getX() - mStartX);
                    if (offsetX > 30) {
                        mMoveOffsetX = (int) (mStartX - event.getX() + mFixX);
                        if (0 > mMoveOffsetX) {
                            mMoveOffsetX = 0;
                        } else {
                            //当滑动大于最大宽度时，不在滑动（右边到头了）
                            if(mRightTitleLayout!=null){
                                if ((mRightTitleLayout.getWidth() + mMoveOffsetX) > rightTitleTotalWidth()) {
                                    mMoveOffsetX = rightTitleTotalWidth() - mRightTitleLayout.getWidth();
                                }
                            }
                        }
                        if(mRightTitleLayout!=null){
                            //跟随手指向右滚动
                            mRightTitleLayout.scrollTo(mMoveOffsetX, 0);
                        }
                        if (null != mMoveViewList) {
                            for (int i = 0; i < mMoveViewList.size(); i++) {
                                //使每个item随着手指向右滚动
                                mMoveViewList.get(i).scrollTo(mMoveOffsetX, 0);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mFixX = mMoveOffsetX; //设置最大水平平移的宽度
                //每次左右滑动都要更新CommonAdapter中的mFixX的值
                if(mAdapter!=null){
                    ((CommonAdapter)mAdapter).setFixX(mFixX);
                }
                break;

        }

        return super.onTouchEvent(event);
    }

    /**
     * 列表头部数据
     * @param headerListData
     */
    public void setHeaderListData(String[] headerListData,String[] mLeftTextList) {
        mRightTitleList = headerListData;
        this.mLeftTextList=mLeftTextList;
        mRightTitleWidthList = new int[headerListData.length];
        for (int i = 0; i < headerListData.length; i++) {
            mRightTitleWidthList[i] = DensityUtil.dip2px(context, mRightItemWidth);
        }
        mLeftTextWidthList = new int[]{DensityUtil.dip2px(context, mLeftViewWidth)};
    }

    /**
     * 获取头数据量
     *
     * @return
     */
    public int getHeaderCount(){
        return mRightTitleList.length+mLeftTextList.length;
    }

}
