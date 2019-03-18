package com.xha.mangotv.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xha.mangotv.R;
import com.xha.mangotv.entity.Address;

import java.util.List;

/**
 * Created by ysq on 2018/8/30.
 */

public class AddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {

    private List<Address> data;

    public AddressAdapter(@LayoutRes int layoutResId, @Nullable List<Address> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {

        TextView tv_address=helper.getView(R.id.tv_address);

        tv_address.setText(item.areaName);

    }
}
