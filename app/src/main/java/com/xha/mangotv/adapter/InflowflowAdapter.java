package com.xha.mangotv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.InflowList;

import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class InflowflowAdapter extends BaseAdapter {

    private Context context;
    private List<InflowList> data;

    public InflowflowAdapter(Context context, List<InflowList> data){
        this.context=context;
        this.data=data;
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
            convertView = View.inflate(context, R.layout.item_inflow_outflow,null);
            viewHodler = new ViewHodler();
            viewHodler.tv_jmmc=convertView.findViewById(R.id.tv_jmmc);
            viewHodler.tv_pdmc=convertView.findViewById(R.id.tv_pdmc);
            viewHodler.tv_ssl=convertView.findViewById(R.id.tv_ssl);

            convertView.setTag(viewHodler);

        }else{

            viewHodler= (ViewHodler) convertView.getTag();

        }

        InflowList inflowList = data.get(position);
        viewHodler.tv_pdmc.setText((position+1)+" "+inflowList.channelName);
        viewHodler.tv_jmmc.setText(inflowList.programName);

        if (inflowList.rating==null||"null".equals(inflowList.rating)){
            viewHodler.tv_ssl.setText("0%");
        }else{
            viewHodler.tv_ssl.setText(inflowList.rating+"%");
        }


        return convertView;
    }

    class ViewHodler{

        TextView tv_pdmc,tv_jmmc,tv_ssl;

    }

}
