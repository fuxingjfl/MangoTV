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
import com.xha.mangotv.adapter.RegionAdapter;
import com.xha.mangotv.entity.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysq on 2018/9/4.
 */

public class RegionTVPop extends PopupWindow{
    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    private RecyclerView rv_dq;
    private TextView tv_qx_pop,tv_qr_pop;
    private RegionSelectorListener regionSelectorListener;

    private List<Address> list;
    private List<Boolean> xz;
    private RegionAdapter regionAdapter;
    private LinearLayout ll_sg;
    private String code,name;
    private String text;
    private TextView tv_time,tv_dq;

    public RegionTVPop(Context context, int resId
               , RegionSelectorListener regionSelectorListener, List<Address> list,String text) {
        super(context);
        this.context = context;
        this.resId = resId;
        this.regionSelectorListener=regionSelectorListener;
        this.list=list;
        this.text=text;
        xz = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if (i==0){
                xz.add(true);
            }else{
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

        rv_dq=defaultView.findViewById(R.id.rv_dq);
        tv_time=defaultView.findViewById(R.id.tv_time);
        tv_qx_pop=defaultView.findViewById(R.id.tv_qx_pop);
        tv_qr_pop=defaultView.findViewById(R.id.tv_qr_pop);
        ll_sg=defaultView.findViewById(R.id.ll_sg);
        tv_dq=defaultView.findViewById(R.id.tv_dq);
        regionAdapter = new RegionAdapter(R.layout.item_contrast_channel, list,xz,context,regionChoiceListener);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 4);
        rv_dq.setLayoutManager(gridLayoutManager1);
        gridLayoutManager1.setAutoMeasureEnabled(true);
        rv_dq.setNestedScrollingEnabled(false);
        rv_dq.setAdapter(regionAdapter);
        tv_time.setText(text);
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

            for(int i=0;i<xz.size();i++){

                if(i==pos){
                    xz.set(pos,true);
                }else {
                    xz.set(i,false);
                }


            }

            code = list.get(pos).areaCode;
            name=list.get(pos).areaName;
            regionAdapter.notifyDataSetChanged();

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.tv_qx_pop:

                    regionSelectorListener.onCancelListener();

                    break;
                case R.id.tv_qr_pop:
                    if (code==null||code.length()<=0){
                        code=list.get(0).areaCode;
                        name=list.get(0).areaName;
                    }
                    regionSelectorListener.onRegionSelectorListener(code,name);

                    break;
                case R.id.ll_sg:
                    regionSelectorListener.onCancelListener();
                    break;
            }
        }
    };


    public interface RegionSelectorListener{

        void onCancelListener();

        void onRegionSelectorListener(String data,String name);

    }

    /**
     *
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }


    public void setText(String text){
        tv_time.setText(text);
    }

    public void setNameText(String text){
        tv_dq.setText(text);
    }

}
