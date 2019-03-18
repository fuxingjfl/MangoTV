package com.xha.mangotv.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.entity.RealTimeData;

import java.util.List;

/**
 * Created by ysq on 2018/8/31.
 */

public class LiShiAdapter extends CommonAdapter<LiShiInfo> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;

    private Context context;
    public LiShiAdapter(Context context, List<LiShiInfo> dataList, int layoutId, CommonViewHolder.onItemCommonClickListener listener) {
        super(context, dataList, layoutId);
        commonClickListener = listener;
        this.context=context;
    }

    @Override
    public void bindData(CommonViewHolder holder, LiShiInfo data) {


        if(data.programName==null){

            data.programName="null";
        }

        String programName=  data.programName;
        TextView textView = holder.getView(R.id.id_name);
        if(holder.getAdapterPosition()<=2){
            textView.setTextColor(context.getResources().getColor(R.color.persimmon));
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.black));
        }

        String startTime=data.startTime;
        StringBuffer stringBuffer = new StringBuffer(startTime);
        stringBuffer.insert(2,":");
        stringBuffer.insert(5,":");

        holder.setText(R.id.id_name, programName)
                .setText(R.id.id_tv_price_last, stringBuffer.toString())
                .setText(R.id.id_tv_rise_rate24, data.audienceRating+"%")
                .setText(R.id.id_tv_vol24, data.marketShare+"%")
                .setText(R.id.id_tv_close, data.arrivalRate+"%")
                .setCommonClickListener(commonClickListener);
        holder.getView(R.id.id_head_view).setBackgroundResource(R.color.xh);
        if(holder.getAdapterPosition()%2==0){
            holder.getView(R.id.id_move_layout).setBackgroundResource(R.color.xh);
        }else{
            holder.getView(R.id.id_move_layout).setBackgroundResource(R.color.xxh);
        }
    }
}