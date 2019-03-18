package com.xha.mangotv.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.abel533.echarts.series.Line;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.ContrastChannelPopAdapter;
import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.util.GlobalParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysq on 2018/9/4.
 */

public class ChannelUserTrendPop extends PopupWindow {
    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    private RecyclerView rv_pdlx;
    private TextView tv_qx_pop,tv_qr_pop;
    private ChannelUserTrendSelectorListener channelTypeChoiceListener;

    private List<AddContrastChannel> list;

    private List<ContrastChannelData> mlist;

    private List<Boolean> xz;
    private List<ContrastChannelData> xzdata;
    private ContrastChannelPopAdapter contrastChannelAdapter1;
    private LinearLayout ll_sg,ll_content_view,ll_content_data;

    private String time;
    private TextView tv_tiem;
    private String channelCode;
    private TextView tv_dq;
    public ChannelUserTrendPop(Context context, int resId
            , ChannelUserTrendSelectorListener channelTypeChoiceListener, List<AddContrastChannel> list,String time,String channelCode) {
        super(context);
        this.context = context;
        this.resId = resId;
        this.channelTypeChoiceListener=channelTypeChoiceListener;
        this.list=list;
        this.time=time;
        this.channelCode=channelCode;
        mlist = new ArrayList<>();
        xz = new ArrayList<>();
        xzdata = new ArrayList<>();
        for(int i=0;i<list.get(0).mlist.size();i++){

            xz.add(false);

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

        rv_pdlx=defaultView.findViewById(R.id.rv_pdlx);
        ll_sg=defaultView.findViewById(R.id.ll_sg);
        ll_content_view=defaultView.findViewById(R.id.ll_content_view);
        ll_content_data=defaultView.findViewById(R.id.ll_content_data);
        tv_tiem=defaultView.findViewById(R.id.tv_tiem);
        tv_dq=defaultView.findViewById(R.id.tv_dq);
        tv_qx_pop=defaultView.findViewById(R.id.tv_qx_pop);
        tv_qr_pop=defaultView.findViewById(R.id.tv_qr_pop);
        tv_tiem.setText(time);

        for (int z = 0; z < list.size(); z++) {
            AddContrastChannel addContrastChannel = list.get(z);
            final TextView textView = new TextView(context);
            textView.setId(z);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            textView.setLayoutParams(lp1);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0,15,0,15);

            textView.setTextSize(13.0f);
            textView.setText(addContrastChannel.name);
            ll_content_data.addView(textView);

            View view = new View(context);
            view.setId(z+10);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, 5, 1.0f);
            view.setLayoutParams(lp2);
            view.setBackgroundResource(R.color.persimmon);
            ll_content_view.addView(view);

            if (z==0){
                textView.setTextColor(context.getResources().getColor(R.color.persimmon));
                view.setVisibility(View.VISIBLE);
            }else{
                textView.setTextColor(context.getResources().getColor(R.color.text_hui));
                view.setVisibility(View.INVISIBLE);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i=0;i<list.size();i++){
                        AddContrastChannel addContrastChannel1 = list.get(i);
                        TextView childAt = (TextView) ll_content_data.getChildAt(i);
                        View view = ll_content_view.getChildAt(i);
                        if (childAt==v){
                            childAt.setTextColor(context.getResources().getColor(R.color.persimmon));
                            view.setVisibility(View.VISIBLE);
                            mlist.clear();
                            if (contrastChannelAdapter1!=null){
                                contrastChannelAdapter1.notifyDataSetChanged();
                            }

                            xz.clear();
                            for(int y=0;y<addContrastChannel1.mlist.size();y++){
                                ContrastChannelData contrastChannelData = addContrastChannel1.mlist.get(y);

                                boolean isyy=false;

                                for (int n=0;n<xzdata.size();n++){

                                    if (contrastChannelData.channelCode.equals(xzdata.get(n).channelCode)){//有这个id
                                        isyy=true;
                                    }
                                }

                                if (isyy){
                                    xz.add(true);
                                }else{
                                    xz.add(false);
                                }
                            }

                            mlist.addAll(list.get(i).mlist);
                            if (contrastChannelAdapter1!=null){
                                contrastChannelAdapter1.setStatr(xz);
                                contrastChannelAdapter1.notifyDataSetChanged();
                            }
                        }else{
                            childAt.setTextColor(context.getResources().getColor(R.color.text_hui));
                            view.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });

        }



//        List<ContrastChannelData> mlist = new ArrayList<>();
//        final List<Boolean> zt = new ArrayList<>();
//        for (int j = 0; j < addContrastChannel.mlist.size(); j++) {
//            mlist.add(addContrastChannel.mlist.get(j));
//            ContrastChannelData contrastChannelData = addContrastChannel.mlist.get(j);
//            int start=0;
//            for (int a=0; a<pd_code_list.size();a++){
//                if(contrastChannelData.channelCode.equals(pd_code_list.get(a))){
//                    start=1;
//                    break;
//                }
//            }
//            if(start==0){
//                zt.add(false);
//            }else{
//                zt.add(true);
//                xzdata.add(contrastChannelData);
//                count++;
//            }
//        }
//
//
//
//        final ContrastChannelAdapter contrastChannelAdapter1 = new ContrastChannelAdapter(R.layout.item_contrast_channel, mlist, zt,AddContrastChannelActivity.this,count);
//        contrastChannelAdapter1.setOnSelectClickListener(new SelectClickListener() {
//            @Override
//            public void OnSelectClickListener(int pos, boolean state,ContrastChannelData item) {
//
//                if(state){
//                    zt.set(pos,true);
//                    count++;
//
//                    xzdata.add(item);
//
//                }else{
//                    zt.set(pos,false);
//                    count--;
//
//                    for(int i=0;i<xzdata.size();i++){
//
//                        ContrastChannelData contrastChannelData = xzdata.get(i);
//                        if(item.channelCode.equals(contrastChannelData.channelCode)){
//                            xzdata.remove(i);
//                            break;
//                        }
//
//                    }
//
//                }
//                for(int i=0;i<addContrastChannels.size();i++){
//
//                    ViewGroup childAt = (ViewGroup) ll_content.getChildAt(i);
//                    RecyclerView recyclerView= (RecyclerView) childAt.getChildAt(1);
//                    ContrastChannelAdapter contrastChannelAdapter= (ContrastChannelAdapter) recyclerView.getAdapter();
//                    contrastChannelAdapter.setCount(count);
//                    contrastChannelAdapter.notifyDataSetChanged();
//
//                }
//            }
//        });
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(AddContrastChannelActivity.this, 3);
//        rv_yspd.setLayoutManager(gridLayoutManager1);
//        gridLayoutManager1.setAutoMeasureEnabled(true);
//        rv_yspd.setNestedScrollingEnabled(false);
//        rv_yspd.setAdapter(contrastChannelAdapter1);
//
//        ll_content.addView(view);

        mlist.addAll(list.get(0).mlist);

        contrastChannelAdapter1 = new ContrastChannelPopAdapter(R.layout.item_contrast_channel, mlist, xz,context, GlobalParams.count,channelCode);

        contrastChannelAdapter1.setOnSelectClickListener(new SelectClickListener() {
            @Override
            public void OnSelectClickListener(int pos, boolean state,ContrastChannelData item) {

                Log.e("TAG","state===::::"+state);

                if(state){
                    xz.set(pos,true);
                    GlobalParams.count++;
                    Log.e("TAG","888888888888888count===::::"+GlobalParams.count);
                    xzdata.add(item);

                }else{
                    xz.set(pos,false);
                    GlobalParams.count--;

                    for(int i=0;i<xzdata.size();i++){

                        ContrastChannelData contrastChannelData = xzdata.get(i);
                        if(item.channelCode.equals(contrastChannelData.channelCode)){
                            xzdata.remove(i);
                            break;
                        }

                    }

                }
                Log.e("TAG","页面里面的数据是:::"+GlobalParams.count);
                contrastChannelAdapter1.setCount(GlobalParams.count);
                contrastChannelAdapter1.notifyDataSetChanged();


            }
        });
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 3);
        rv_pdlx.setLayoutManager(gridLayoutManager1);
        gridLayoutManager1.setAutoMeasureEnabled(true);
        rv_pdlx.setNestedScrollingEnabled(false);
        rv_pdlx.setAdapter(contrastChannelAdapter1);


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


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.tv_qx_pop:

                    channelTypeChoiceListener.onCancelListener();

                    break;
                case R.id.tv_qr_pop:

                    List<String> ml = new ArrayList<>();

                    for (int i=0;i<xzdata.size();i++){

                        ml.add(xzdata.get(i).channelCode);

                    }

                    channelTypeChoiceListener.onChannelUserTrendSelectorListener(ml);

                    break;
                case R.id.ll_sg:
                    channelTypeChoiceListener.onCancelListener();
                    break;
            }
        }
    };


    public interface ChannelUserTrendSelectorListener{

        void onCancelListener();

        void onChannelUserTrendSelectorListener(List<String> ml);

    }

    /**
     *
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }

    public void setTime(String time){

        tv_tiem.setText(time);

    }

    public void setNameText(String name){
        tv_dq.setText(name);
    }

}