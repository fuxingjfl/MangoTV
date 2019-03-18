package com.xha.mangotv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xha.mangotv.R;

import java.util.List;

/**
 * Created by ysq on 2018/11/22.
 */

public class TimeAdapter extends BaseAdapter {

    private Context context;
    private List<String> mlist;
    private int position=0;
    private ItemClickListener itemClickListener;
    public TimeAdapter(Context context, List<String> mlist){
        this.context=context;
        this.mlist=mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
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
    public View getView(final int p, View convertView, ViewGroup parent) {

        ViewHodler viewHodler=null;
//        if (convertView==null){

            convertView=View.inflate(context, R.layout.item_time_pop,null);
            viewHodler = new ViewHodler();
            viewHodler.tv_time_item=convertView.findViewById(R.id.tv_time_item);

            convertView.setTag(viewHodler);

//        }else{
//            viewHodler= (ViewHodler) convertView.getTag();
//        }

        viewHodler.tv_time_item.setText(mlist.get(p));
        if (position==-1){
            viewHodler.tv_time_item.setBackgroundResource(R.drawable.shape_rl_hs);
        }else {
            if (position == p) {
                viewHodler.tv_time_item.setBackgroundResource(R.drawable.shape_rl_xz);
            } else {
                viewHodler.tv_time_item.setBackgroundResource(R.drawable.shape_rl_hs);
            }

        }

        viewHodler.tv_time_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=p;
                itemClickListener.OnItemClickListener(mlist.get(p),p);
            }
        });
        return convertView;
    }

    class ViewHodler{
        TextView tv_time_item;
    }


    public interface ItemClickListener{

        void OnItemClickListener(String time,int pos);

    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

}
