package com.xha.mangotv.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xha.mangotv.R;
import com.xha.mangotv.entity.HistoryListInfo;
import com.xha.mangotv.view.XCRoundRectImageView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ysq on 2018/9/3.
 */

public class HistoricalViewingAdapter extends BaseAdapter {

    private List<HistoryListInfo> data;
    private Context context;

    public HistoricalViewingAdapter( Context context,List<HistoryListInfo> data) {
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

        ViewHodler viewHodler=null;

        if (convertView==null){

            convertView=View.inflate(context,R.layout.item_historical_viewing,null);
            viewHodler = new ViewHodler();
            viewHodler.roundRectImageView_data=convertView.findViewById(R.id.roundRectImageView_data);
            viewHodler.tv_ls_name=convertView.findViewById(R.id.tv_ls_name);
            convertView.setTag(viewHodler);
        }else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        HistoryListInfo historyListInfo = data.get(position);
        viewHodler. roundRectImageView_data.setImageResource(historyListInfo.imgres);
        viewHodler.tv_ls_name.setText(historyListInfo.name);
        return convertView;
    }
    class ViewHodler{
        TextView tv_ls_name;
        XCRoundRectImageView roundRectImageView_data;
    }
}
