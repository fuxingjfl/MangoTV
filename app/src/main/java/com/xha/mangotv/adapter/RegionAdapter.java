package com.xha.mangotv.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xha.mangotv.R;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.view.RegionChoiceListener;

import java.util.List;

/**
 * Created by ysq on 2018/9/4.
 */

public class RegionAdapter extends BaseQuickAdapter <Address,BaseViewHolder>{
    private List<Address> data;
    private List<Boolean> xz;
    private Context context;
    private RegionChoiceListener regionChoiceListener;
    public RegionAdapter(@LayoutRes int layoutResId, @Nullable List data,List<Boolean> xz,Context context,RegionChoiceListener regionChoiceListener) {
        super(layoutResId, data);
        this.data=data;
        this.context=context;
        this.xz=xz;
        this.regionChoiceListener=regionChoiceListener;
    }
    @Override
    protected void convert(final BaseViewHolder helper, Address item) {

        final RelativeLayout rl_pdmc = helper.getView(R.id.rl_pdmc);
        final TextView tv_pd_name=helper.getView(R.id.tv_pd_name);

        tv_pd_name.setText(item.areaName);

        if(xz.get(helper.getAdapterPosition())){

            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_xz);

            tv_pd_name.setTextColor(context.getResources().getColor(R.color.black));

        }else{
            rl_pdmc.setBackgroundResource(R.drawable.shape_rl_hs);

            tv_pd_name.setTextColor(context.getResources().getColor(R.color.text_hui));
        }
        rl_pdmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_pdmc.setBackgroundResource(R.drawable.shape_rl_xz);
                tv_pd_name.setTextColor(context.getResources().getColor(R.color.black));
                regionChoiceListener.OnRegionChoiceListener(helper.getPosition());

            }
        });
    }
}
