package com.xha.mangotv.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.CoinInfo;
import com.xha.mangotv.entity.Index;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.entity.StbNumList;

import java.util.List;

/**
 * Created by ysq on 2018/8/31.
 */

public class CoinAdapter extends CommonAdapter<RealTimeData> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;
    private Context context;
    private List<Index> tabList;
    public CoinAdapter(Context context, List<RealTimeData> dataList,List<Index> tabList, int layoutId, CommonViewHolder.onItemCommonClickListener listener) {
        super(context, dataList, layoutId);
        commonClickListener = listener;
        this.context=context;
        this.tabList=tabList;
    }

    @Override
    public void bindData(CommonViewHolder holder, RealTimeData data) {

        TextView textView = holder.getView(R.id.id_name);
        if(holder.getAdapterPosition()<=2){
            textView.setTextColor(context.getResources().getColor(R.color.persimmon));
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.setText(R.id.id_name, (holder.getAdapterPosition()+1)+" "+data.channelName);

        for (int i=0;i<tabList.size();i++){

            String s = tabList.get(i).name;
            if("节目名称 ".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.programName);
                    }
                }


            }else if("收视率".equals(s)){

                if(i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.audienceRating+"%");
                    }
                }else if(i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.audienceRating+"%");
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.audienceRating+"%");
                    }
                }


            }else if("收视份额".equals(s)){

                if(i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.marketShare+"%");
                    }
                }else if(i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.marketShare+"%");
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.marketShare+"%");
                    }
                }



            }else if("在线用户数".equals(s)){

                if(i==1){
                    Log.e("TAG","执行了=====用户数:"+data.stbNum);
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.stbNum);
                    }
                }else if(i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.stbNum);
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.stbNum);
                    }
                }

            }

        }

                holder.setCommonClickListener(commonClickListener);

        holder.getView(R.id.id_head_view).setBackgroundResource(R.color.xh);
        if(holder.getAdapterPosition()%2==0){
            holder.getView(R.id.id_move_layout).setBackgroundResource(R.color.xh);
        }else{
            holder.getView(R.id.id_move_layout).setBackgroundResource(R.color.xxh);
        }
    }
}