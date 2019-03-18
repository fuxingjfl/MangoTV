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
import com.xha.mangotv.adapter.ChannelTypeAdapter;
import com.xha.mangotv.adapter.RegionAdapter;
import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysq on 2018/9/4.
 */

public class ChannelTypePop extends PopupWindow {
    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    private RecyclerView rv_pdlx;
    private TextView tv_qx_pop,tv_qr_pop;
    private ChannelTypeSelectorListener channelTypeChoiceListener;

    private List<ChannelGroup> list;
    private List<Boolean> xz;
    private ChannelTypeAdapter channelTypeAdapter;
    private LinearLayout ll_sg;
    private String channelGroupCode,name;
    private String text;
    private TextView tv_rl_content;
    private TextView tv_dq,tv_pdlx;
    private int xz_pos;
    public ChannelTypePop(Context context, int resId
            , ChannelTypeSelectorListener channelTypeChoiceListener, List<ChannelGroup> list,String text,int xz_pos) {
        super(context);
        this.context = context;
        this.resId = resId;
        this.channelTypeChoiceListener=channelTypeChoiceListener;
        this.list=list;
        this.text=text;
        xz = new ArrayList<>();
        this.xz_pos=xz_pos;
        for(int i=0;i<list.size();i++){

            if (i==this.xz_pos){
                xz.add(true);
            }else{
                xz.add(false);
            }


        }
        name=list.get(xz_pos).channelGroupName;
        channelGroupCode=list.get(xz_pos).channelGroupCode;
        initPopupWindow();
    }

    public void initPopupWindow() {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        defaultView = inflater.inflate(this.resId, null);
        defaultView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setContentView(defaultView);

        rv_pdlx=defaultView.findViewById(R.id.rv_pdlx);
        ll_sg=defaultView.findViewById(R.id.ll_sg);
        tv_rl_content=defaultView.findViewById(R.id.tv_rl_content);
        tv_qx_pop=defaultView.findViewById(R.id.tv_qx_pop);
        tv_qr_pop=defaultView.findViewById(R.id.tv_qr_pop);
        tv_pdlx=defaultView.findViewById(R.id.tv_pdlx);
        tv_dq=defaultView.findViewById(R.id.tv_dq);
        tv_rl_content.setText(text);
        channelTypeAdapter = new ChannelTypeAdapter(R.layout.item_contrast_channel, list,xz,context,regionChoiceListener);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 3);
        rv_pdlx.setLayoutManager(gridLayoutManager1);
        gridLayoutManager1.setAutoMeasureEnabled(true);
        rv_pdlx.setNestedScrollingEnabled(false);
        rv_pdlx.setAdapter(channelTypeAdapter);


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

    private ChannelTypeChoiceListener regionChoiceListener = new ChannelTypeChoiceListener() {

        @Override
        public void OnChannelTypeChoiceListener(int pos) {
            for(int i=0;i<xz.size();i++){

                if(i==pos){
                    xz.set(pos,true);
                }else {
                    xz.set(i,false);
                }


            }

            channelGroupCode=list.get(pos).channelGroupCode;
            name=list.get(pos).channelGroupName;
            channelTypeAdapter.notifyDataSetChanged();

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.tv_qx_pop:

                    channelTypeChoiceListener.onCancelListener();

                    break;
                case R.id.tv_qr_pop:

                    if (channelGroupCode==null||channelGroupCode.length()<=0){
                        channelGroupCode=list.get(xz_pos).channelGroupCode;
                    }

                    channelTypeChoiceListener.onRegionSelectorListener(channelGroupCode,name);


                    break;
                case R.id.ll_sg:
                    channelTypeChoiceListener.onCancelListener();
                    break;
            }
        }
    };


    public interface ChannelTypeSelectorListener{

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
        tv_rl_content.setText(text);
    }

    public void setNameText(String text){
        tv_dq.setText(text);
    }

    public void setPDText(String text){
        tv_pdlx.setText(text);
    }

}