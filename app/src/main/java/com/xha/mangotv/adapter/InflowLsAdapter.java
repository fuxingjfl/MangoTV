package com.xha.mangotv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.InflowList;
import com.xha.mangotv.entity.LastCount;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.PreUtil;

import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class InflowLsAdapter extends BaseAdapter {

    private Context context;
    private List<LastCount> data;
    private boolean inoutStbNum_pdssph;
    public InflowLsAdapter(Context context, List<LastCount> data){
        this.context=context;
        this.data=data;
        inoutStbNum_pdssph = PreUtil.getInstance().getBoolean(ConstantValues.inoutStbNum_pdssph,false);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;

        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_inflow_outflow_ls,null);
            viewHodler = new ViewHodler();
            viewHodler.tv_jmmc=convertView.findViewById(R.id.tv_jmmc);
            viewHodler.tv_pdmc=convertView.findViewById(R.id.tv_pdmc);
            viewHodler.tv_ssl=convertView.findViewById(R.id.tv_ssl);

            convertView.setTag(viewHodler);

        }else{

            viewHodler= (ViewHodler) convertView.getTag();

        }

        LastCount inflowList = data.get(position);

        viewHodler.tv_pdmc.setText((position+1)+" "+inflowList.lastChanneName);
        viewHodler.tv_jmmc.setText(inflowList.lastStbNum);
        if (inoutStbNum_pdssph){
            viewHodler.tv_jmmc.setVisibility(View.VISIBLE);
        }else{
            viewHodler.tv_jmmc.setVisibility(View.INVISIBLE);
        }
        viewHodler.tv_ssl.setText(inflowList.lastRate);
        return convertView;
    }

    class ViewHodler{

        TextView tv_pdmc,tv_jmmc,tv_ssl;

    }

}
