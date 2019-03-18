package com.xha.mangotv.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.Index;
import com.xha.mangotv.entity.LiShiInfo;
import com.xha.mangotv.entity.RealTimeData;

import java.util.List;

/**
 * Created by ysq on 2018/8/31.
 */

public class ChannelRatingsAdapter extends CommonAdapter<LiShiInfo> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;
    private Context context;
    private List<Index> tabList;
    public ChannelRatingsAdapter(Context context, List<LiShiInfo> dataList, List<Index> tabList, int layoutId, CommonViewHolder.onItemCommonClickListener listener) {
        super(context, dataList, layoutId);
        commonClickListener = listener;
        this.context=context;
        this.tabList=tabList;
    }

    @Override
    public void bindData(CommonViewHolder holder, LiShiInfo data) {

        TextView textView = holder.getView(R.id.id_name);
        if(holder.getAdapterPosition()<=2){

            textView.setTextColor(context.getResources().getColor(R.color.persimmon));
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.black));
        }


        holder.setText(R.id.id_name, (holder.getAdapterPosition()+1)+" "+data.channelName);

//        holder.setText(R.id.id_name, data.channelName).
//                setText(R.id.id_tv_price_last,data.audienceRating+"%")
//                .setText(R.id.id_tv_rise_rate24,data.marketShare+"%").
//                setText(R.id.id_tv_vol24,data.arrivalRate+"%").
//                setText(R.id.id_tv_close,data.currentRanking).
//                setText(R.id.id_tv_open,data.playTime).
//                setText(R.id.id_tv_bid,data.viewTime).
//                setText(R.id.id_tv_ask,data.stbNum).setCommonClickListener(commonClickListener);

        for (int i=0;i<tabList.size();i++){
            String s = tabList.get(i).name;
            if("收视率".equals(s)){
                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.audienceRating+"%");
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.audienceRating+"%");
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.audienceRating+"%");
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.audienceRating+"%");
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.audienceRating+"%");
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.audienceRating+"%");
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.audienceRating+"%");
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.audienceRating+"%");
                    }
                }

            }else if("收视份额".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.marketShare+"%");
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.marketShare+"%");
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.marketShare+"%");
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.marketShare+"%");
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.marketShare+"%");
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.marketShare+"%");
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.marketShare+"%");
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.marketShare+"%");
                    }
                }


            }else if("到达率".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.arrivalRate+"%");
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.arrivalRate+"%");
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.arrivalRate+"%");
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.arrivalRate+"%");
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.arrivalRate+"%");
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.arrivalRate+"%");
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.arrivalRate+"%");
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.arrivalRate+"%");
                    }
                }



            }else if("直播次数".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.playTime);
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.playTime);
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.playTime);
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.playTime);
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.playTime);
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.playTime);
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.playTime);
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.playTime);
                    }
                }

            }else if("直播收视时长".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.viewTime);
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.viewTime);
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.viewTime);
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.viewTime);
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.viewTime);
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.viewTime);
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.viewTime);
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.viewTime);
                    }
                }

            }else if("直播用户数".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.stbNum);
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.stbNum);
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.stbNum);
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.stbNum);
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.stbNum);
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.stbNum);
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.stbNum);
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.stbNum);
                    }
                }

            }else if("同时段排名".equals(s)){

                if(i==0){
                    if (holder.getView(R.id.id_tv_price_last)!=null){
                        holder.setText(R.id.id_tv_price_last, data.currentRanking);
                    }
                }else if (i==1){
                    if (holder.getView(R.id.id_tv_rise_rate24)!=null){
                        holder.setText(R.id.id_tv_rise_rate24, data.currentRanking);
                    }
                }else if (i==2){
                    if (holder.getView(R.id.id_tv_vol24)!=null){
                        holder.setText(R.id.id_tv_vol24, data.currentRanking);
                    }
                }else if(i==3){
                    if (holder.getView(R.id.id_tv_close)!=null){
                        holder.setText(R.id.id_tv_close, data.currentRanking);
                    }
                }else if(i==4){
                    if (holder.getView(R.id.id_tv_open)!=null){
                        holder.setText(R.id.id_tv_open, data.currentRanking);
                    }
                }else if(i==5){
                    if (holder.getView(R.id.id_tv_bid)!=null){
                        holder.setText(R.id.id_tv_bid, data.currentRanking);
                    }
                }else if(i==6){
                    if (holder.getView(R.id.id_tv_ask)!=null){
                        holder.setText(R.id.id_tv_ask, data.currentRanking);
                    }
                }else if(i==7){
                    if (holder.getView(R.id.id_tv_percent)!=null){
                        holder.setText(R.id.id_tv_percent, data.currentRanking);
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