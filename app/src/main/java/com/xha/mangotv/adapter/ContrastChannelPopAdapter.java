package com.xha.mangotv.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.AddContrastChannelActivity;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.util.GlobalParams;
import com.xha.mangotv.view.ChannelTVPop;
import com.xha.mangotv.view.SelectClickListener;

import java.util.List;

/**
 * Created by ysq on 2018/9/3.
 */

public class ContrastChannelPopAdapter extends BaseQuickAdapter <ContrastChannelData, BaseViewHolder> {

    private List<ContrastChannelData> data;
    private SelectClickListener selectClickListener;
    private List<Boolean> zt;
    private Context context;
    private int count =0;//记录选中的个数
    private String channelCode;
    public ContrastChannelPopAdapter(@LayoutRes int layoutResId, @Nullable List<ContrastChannelData> data, List<Boolean> zt, Context context, int count,String channelCode) {
        super(layoutResId, data);
        this.data=data;
        this.zt = zt;
        this.context=context;
        this.channelCode=channelCode;
//        this.count=count;
    }

    public void setOnSelectClickListener(SelectClickListener selectClickListener){
        this.selectClickListener=selectClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ContrastChannelData item) {

        final RelativeLayout rl_pdmc=helper.getView(R.id.rl_pdmc);

        final TextView tv_pd_name=helper.getView(R.id.tv_pd_name);

        tv_pd_name.setText(item.channelName);
        if (zt.get(helper.getPosition())){

            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_xz);

            tv_pd_name.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_hs);

            tv_pd_name.setTextColor(context.getResources().getColor(R.color.text_hui));
        }


        if (channelCode.equals(item.channelCode)){

            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_xz);

            tv_pd_name.setTextColor(context.getResources().getColor(R.color.black));

        }

        if (channelCode.equals(item.channelCode)){

        }else{
            rl_pdmc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(helper.getPosition()<zt.size()){
                        Log.e("TAG","第一个数据::::"+helper.getPosition()+"第二个数据::::"+zt.size());
                        if(zt.get(helper.getPosition())){//选中
                            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_hs);
                            tv_pd_name.setTextColor(context.getResources().getColor(R.color.text_hui));
                            selectClickListener.OnSelectClickListener(helper.getAdapterPosition(),false,item);

                        }else{//未选中
                            Log.e("TAG","数据cou==="+ GlobalParams.count);
                            if(GlobalParams.count<3){
                                rl_pdmc.setBackgroundResource(R.drawable.shape_rl_xz);
                                tv_pd_name.setTextColor(context.getResources().getColor(R.color.black));
                                selectClickListener.OnSelectClickListener(helper.getPosition(),true,item);
                            }
                        }
                    }
                }
            });
        }

    }
    public void setCount(int count){
        this.count=count;
    }

    public void setStatr(List<Boolean> zt){
        this.zt=zt;
    }

}
