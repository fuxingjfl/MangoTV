package com.xha.mangotv.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.ChannelRatingsActivity;
import com.xha.mangotv.activity.ChannelShareRatioActivity;
import com.xha.mangotv.activity.LiveTVviewingActivity;
import com.xha.mangotv.adapter.HistoricalViewingAdapter;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.HistoryListInfo;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史收视
 * Created by ysq on 2018/8/30.
 */

public class HistoricalViewingFragment extends BaseFragment {
    private MyListView rv_card;
    private List<HistoryListInfo> list;
    private HistoricalViewingAdapter historicalViewingAdapter;
    private ImageView iv_fanhui,title_tv;
    private RelativeLayout rl_bt;
    @Override
    protected int setContentView() {
        return R.layout.fragment_historical_viewing;
    }
    @Override
    protected void lazyLoad() {
        View contentView = getContentView();
        rv_card=contentView.findViewById(R.id.rv_card);
        iv_fanhui=contentView.findViewById(R.id.iv_fanhui);
        rl_bt=contentView.findViewById(R.id.rl_bt);
        title_tv=contentView.findViewById(R.id.title_tv);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.back);
        iv_fanhui.setVisibility(View.INVISIBLE);
        title_tv.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        for (int i=0;i<3;i++){
            HistoryListInfo historyListInfo = new HistoryListInfo();
            switch (i){
                case 0:
                    historyListInfo.name="频道收视排行";
                    historyListInfo.imgres=R.drawable.ls1;
                    break;
                case 1:
                    historyListInfo.name="频道分类份额占比";
                    historyListInfo.imgres=R.drawable.ls2;
                    break;
                case 2:
                    historyListInfo.name="直播节目收视统计";
                    historyListInfo.imgres=R.drawable.ls3;
                    break;
            }
            list.add(historyListInfo);
        }
        historicalViewingAdapter = new HistoricalViewingAdapter(getActivity(),list);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        rv_card.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setAutoMeasureEnabled(true);
//        rv_card.setNestedScrollingEnabled(false);
        rv_card.setAdapter(historicalViewingAdapter);
        rv_card.setOnItemClickListener(onItemClickListener);
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            switch (position){
                case 0:
                    boolean aBoolean1 = PreUtil.getInstance().getBoolean(ConstantValues.selected_pdssph, false);
                    if (aBoolean1){
                        intent= new Intent(getActivity(), ChannelRatingsActivity.class);
                        startActivity(intent);
                    }
                    break;
                case 1:
                    boolean aBoolean2 = PreUtil.getInstance().getBoolean(ConstantValues.selected_fezb, false);
                    if (aBoolean2){
                        intent = new Intent(getActivity(), ChannelShareRatioActivity.class);
                        startActivity(intent);
                    }
                    break;
                case 2:
                    boolean aBoolean3 = PreUtil.getInstance().getBoolean(ConstantValues.selected_zbtj, false);
                    if (aBoolean3){
                        intent = new Intent(getActivity(), LiveTVviewingActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
    //获取状态栏的高度
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
