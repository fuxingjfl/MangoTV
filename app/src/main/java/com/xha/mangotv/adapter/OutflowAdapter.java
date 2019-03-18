package com.xha.mangotv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.InflowList;
import com.xha.mangotv.entity.OutflowList;

import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class OutflowAdapter extends BaseAdapter {

    private Context context;
    private List<OutflowList> data;

    public OutflowAdapter(Context context, List<OutflowList> data){
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
        OutflowList outflowList = data.get(position);
        viewHodler.tv_pdmc.setText((position+1)+" "+outflowList.channelName);
        viewHodler.tv_jmmc.setText(outflowList.programName);
        if (outflowList.rating==null||"null".equals(outflowList.rating)){
            viewHodler.tv_ssl.setText("0%");
        }else{
            viewHodler.tv_ssl.setText(outflowList.rating+"%");
        }
        return convertView;
    }

    class ViewHodler{

        TextView tv_pdmc,tv_jmmc,tv_ssl;

    }


}
