package com.xha.mangotv.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.adapter.IndexAdapter;
import com.xha.mangotv.adapter.RegionAdapter;
import com.xha.mangotv.entity.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysq on 2018/10/25.
 */

public class IndexPop extends PopupWindow {
    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    private RecyclerView rv_dq;
    private TextView tv_qx_pop, tv_qr_pop,tv_rl_content;
    private IndexSelectorListener indexSelectorListener;

    private List<String> list;
    private List<Boolean> xz;
    private IndexAdapter indexAdapter;
    private LinearLayout ll_sg;
    private String code,name;
    private String time;
    private TextView tv_dq,tv_nr;
    private List<String > mlist;
    public IndexPop(Context context, int resId
            , IndexSelectorListener indexSelectorListener, List<String> list,String time) {
        super(context);
        this.context = context;
        this.resId = resId;
        this.indexSelectorListener = indexSelectorListener;
        this.list = list;
        this.time=time;
        xz = new ArrayList<>();
        mlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            if (i == 0) {
                xz.add(true);
            } else {
                xz.add(false);
            }

        }
        initPopupWindow();
    }

    public void initPopupWindow() {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        defaultView = inflater.inflate(this.resId, null);
        defaultView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setContentView(defaultView);

        rv_dq = defaultView.findViewById(R.id.rv_dq);
        tv_qx_pop = defaultView.findViewById(R.id.tv_qx_pop);
        tv_qr_pop = defaultView.findViewById(R.id.tv_qr_pop);
        ll_sg = defaultView.findViewById(R.id.ll_sg);
        tv_dq=defaultView.findViewById(R.id.tv_dq);
        tv_nr=defaultView.findViewById(R.id.tv_nr);
        tv_rl_content=defaultView.findViewById(R.id.tv_rl_content);
        tv_rl_content.setText(time);

        for (int i=0;i<list.size();i++){

            if ("audienceRating".equals(list.get(i))){
                mlist.add("收视率");
            }else if ("marketShare".equals(list.get(i))){
                mlist.add("收视份额");
            }else if ("stbNum".equals(list.get(i))){
                mlist.add("用户数");
            }else if ("arrivalRate".equals(list.get(i))){
                mlist.add("到达率");
            }else if ("viewTime".equals(list.get(i))){
                mlist.add("直播时长");
            }else if ("playTime".equals(list.get(i))){
                mlist.add("直播次数");
            }
        }

        indexAdapter = new IndexAdapter(R.layout.item_contrast_channel, mlist, xz, context, regionChoiceListener);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 3);
        rv_dq.setLayoutManager(gridLayoutManager1);
        gridLayoutManager1.setAutoMeasureEnabled(true);
        rv_dq.setNestedScrollingEnabled(false);
        rv_dq.setAdapter(indexAdapter);


        tv_qx_pop.setOnClickListener(onClickListener);
        tv_qr_pop.setOnClickListener(onClickListener);
        ll_sg.setOnClickListener(onClickListener);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//		 setAnimationStyle(R.style.popwin_anim_style);
//        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        setFocusable(false);
        setOutsideTouchable(false);
        update();

    }

    private RegionChoiceListener regionChoiceListener = new RegionChoiceListener() {
        @Override
        public void OnRegionChoiceListener(int pos) {

            for (int i = 0; i < xz.size(); i++) {

                if (i == pos) {
                    xz.set(pos, true);
                } else {
                    xz.set(i, false);
                }


            }

            code = list.get(pos);
            name=mlist.get(pos);
            indexAdapter.notifyDataSetChanged();

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tv_qx_pop:

                    indexSelectorListener.onCancelListener();

                    break;
                case R.id.tv_qr_pop:
                    if (code == null || code.length() <= 0) {
                        code = list.get(0);
                        name=mlist.get(0);
                    }
                    indexSelectorListener.onIndexSelectorListener(code,name);
                    break;
                case R.id.ll_sg:
                    indexSelectorListener.onCancelListener();
                    break;
            }
        }
    };


    public interface IndexSelectorListener {

        void onCancelListener();

        void onIndexSelectorListener(String data,String name);

    }

    /**
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }


    public void  setTime(String time){
        tv_rl_content.setText(time);
    }

    public void setNameText(String text){
        tv_dq.setText(text);
    }

    public void setZBText(String text){
        tv_nr.setText(text);
    }

}