package com.xha.mangotv.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xha.mangotv.R;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.util.ChoiceDelListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by ysq on 2018/9/5.
 */

public class RealTimeAdapter extends BaseAdapter {

    private Context context;
    private List<DuiBiData> data;
    private int pos=-1;
    private ChoiceDelListener choiceDelListener;

    public RealTimeAdapter(Context context,List<DuiBiData> data,ChoiceDelListener choiceDelListener){
        this.context=context;
        this.data=data;
        this.choiceDelListener=choiceDelListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHodler viewHodler;

        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_real_time,null);
            viewHodler = new ViewHodler();
            viewHodler.rl_tm_hs = convertView.findViewById(R.id.rl_tm_hs);
            viewHodler.rl_sc=convertView.findViewById(R.id.rl_sc);
            viewHodler.tv_pdmc=convertView.findViewById(R.id.tv_pdmc);
            viewHodler.tv_jmmc=convertView.findViewById(R.id.tv_jmmc);
            viewHodler.tv_ssl=convertView.findViewById(R.id.tv_ssl);
            viewHodler.iv_status=convertView.findViewById(R.id.iv_status);
            viewHodler.tv_del_pd=convertView.findViewById(R.id.tv_del_pd);
            viewHodler.pd_logo=convertView.findViewById(R.id.pd_logo);
            convertView.setTag(viewHodler);

        }else{

            viewHodler= (ViewHodler) convertView.getTag();

        }
        DuiBiData duiBiData = data.get(position);


        if (duiBiData.channelName.contains("江苏靓妆")){
            viewHodler.pd_logo.setImageResource(R.drawable.jz);
        }else if (duiBiData.channelName.contains("安徽")){
            viewHodler.pd_logo.setImageResource(R.drawable.ah);
        }else if (duiBiData.channelName.contains("北京")){
            viewHodler.pd_logo.setImageResource(R.drawable.bjws);
        }else if (duiBiData.channelName.contains("重庆")){
            viewHodler.pd_logo.setImageResource(R.drawable.cq);
        }else if (duiBiData.channelName.contains("东方")){
            viewHodler.pd_logo.setImageResource(R.drawable.dfws);
        }else if (duiBiData.channelName.contains("东南")){
            viewHodler.pd_logo.setImageResource(R.drawable.dnws);
        }else if (duiBiData.channelName.contains("凤凰")){
            viewHodler.pd_logo.setImageResource(R.drawable.fhws);
        }else if (duiBiData.channelName.contains("甘肃")){
            viewHodler.pd_logo.setImageResource(R.drawable.gsws);
        }else if (duiBiData.channelName.contains("广东")){
            viewHodler.pd_logo.setImageResource(R.drawable.gdws);
        }else if (duiBiData.channelName.contains("广西")){
            viewHodler.pd_logo.setImageResource(R.drawable.gxws);
        }else if (duiBiData.channelName.contains("贵州")){
            viewHodler.pd_logo.setImageResource(R.drawable.gzws);
        }else if (duiBiData.channelName.contains("河北")){
            viewHodler.pd_logo.setImageResource(R.drawable.hbws);
        }else if (duiBiData.channelName.contains("河南")){
            viewHodler.pd_logo.setImageResource(R.drawable.hn);
        }else if (duiBiData.channelName.contains("黑龙江")){
            viewHodler.pd_logo.setImageResource(R.drawable.hlj);
        }else if (duiBiData.channelName.contains("湖北")){
            viewHodler.pd_logo.setImageResource(R.drawable.hu_bws);
        }else if (duiBiData.channelName.contains("湖南电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.hndy);
        }else if (duiBiData.channelName.contains("湖南经视")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnjs);
        }else if (duiBiData.channelName.contains("湖南教育")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnjy);
        }else if (duiBiData.channelName.contains("湖南电视剧")){
            viewHodler.pd_logo.setImageResource(R.drawable.hndsj);
        }else if (duiBiData.channelName.contains("湖南都市")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnds);
        }else if (duiBiData.channelName.contains("湖南公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.hngg);
        }else if (duiBiData.channelName.contains("湖南娱乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnyl);
        } else if (duiBiData.channelName.contains("湖南国际")){
            viewHodler.pd_logo.setImageResource(R.drawable.hngj);
        } else if (duiBiData.channelName.contains("湖南")){
            viewHodler.pd_logo.setImageResource(R.drawable.moren_pd);
        } else if (duiBiData.channelName.contains("吉林")){
            viewHodler.pd_logo.setImageResource(R.drawable.jlws);
        }else if (duiBiData.channelName.contains("江苏")){
            viewHodler.pd_logo.setImageResource(R.drawable.jsws);
        }else if (duiBiData.channelName.contains("江西")){
            viewHodler.pd_logo.setImageResource(R.drawable.jxws);
        }else if (duiBiData.channelName.contains("辽宁")){
            viewHodler.pd_logo.setImageResource(R.drawable.ln);
        }else if (duiBiData.channelName.contains("旅游卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.lyws);
        }else if (duiBiData.channelName.contains("内蒙古")){
            viewHodler.pd_logo.setImageResource(R.drawable.nmg);
        }else if (duiBiData.channelName.contains("宁夏")){
            viewHodler.pd_logo.setImageResource(R.drawable.nxws);
        }else if (duiBiData.channelName.contains("青海")){
            viewHodler.pd_logo.setImageResource(R.drawable.qhg);
        }else if (duiBiData.channelName.contains("山东")){
            viewHodler.pd_logo.setImageResource(R.drawable.sdws);
        }else if (duiBiData.channelName.contains("山西")){
            viewHodler.pd_logo.setImageResource(R.drawable.sxws);
        }else if (duiBiData.channelName.contains("陕西")){
            viewHodler.pd_logo.setImageResource(R.drawable.shan_xws);
        }else if (duiBiData.channelName.contains("四川")){
            viewHodler.pd_logo.setImageResource(R.drawable.scws);
        }else if (duiBiData.channelName.contains("天津")){
            viewHodler.pd_logo.setImageResource(R.drawable.tj);
        }else if (duiBiData.channelName.contains("西藏")){
            viewHodler.pd_logo.setImageResource(R.drawable.xzws);
        }else if (duiBiData.channelName.contains("新疆")){
            viewHodler.pd_logo.setImageResource(R.drawable.xj);
        }else if (duiBiData.channelName.contains("云南")){
            viewHodler.pd_logo.setImageResource(R.drawable.ynws);
        }else if (duiBiData.channelName.contains("浙江")){
            viewHodler.pd_logo.setImageResource(R.drawable.zjws);
        }else if (duiBiData.channelName.contains("深圳")){
            viewHodler.pd_logo.setImageResource(R.drawable.szws);
        }else if (duiBiData.channelName.contains("中国气象")){
            viewHodler.pd_logo.setImageResource(R.drawable.zgqx);
        } else if (duiBiData.channelName.contains("南方卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.gdnfws);
        }else if (duiBiData.channelName.contains("魅力时尚")){
            viewHodler.pd_logo.setImageResource(R.drawable.mlss);
        }else if (duiBiData.channelName.contains("时尚频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.sspd);
        }else if (duiBiData.channelName.contains("卡酷动画")){
            viewHodler.pd_logo.setImageResource(R.drawable.kkws);
        } else if (duiBiData.channelName.contains("百姓健康")){
            viewHodler.pd_logo.setImageResource(R.drawable.chtv);
        }else if (duiBiData.channelName.contains("财富天下")){
            viewHodler.pd_logo.setImageResource(R.drawable.cftx);
        }else if (duiBiData.channelName.contains("CHC动作电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_dzdy);
        }else if (duiBiData.channelName.contains("CHC高清电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_gqdy);
        }else if (duiBiData.channelName.contains("CHC家庭影院")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_jtyy);
        }else if (duiBiData.channelName.contains("DOX韩流")){
            viewHodler.pd_logo.setImageResource(R.drawable.tthl);
        }else if (duiBiData.channelName.contains("DOX剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_jc);
        }else if (duiBiData.channelName.contains("DOX新艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_xy);
        }else if (duiBiData.channelName.contains("DOX新知")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_xz);
        }else if (duiBiData.channelName.contains("DOX映画")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yh);
        }else if (duiBiData.channelName.contains("DOX怡家")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yj);
        }else if (duiBiData.channelName.contains("DOX英伦")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yl);
        }else if (duiBiData.channelName.contains("DOX院线")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yx);
        }else if (duiBiData.channelName.contains("高尔夫频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.grfwq);
        }else if (duiBiData.channelName.contains("芒果演艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.mgyy);
        }else if (duiBiData.channelName.contains("嘉丽购")){
            viewHodler.pd_logo.setImageResource(R.drawable.jlg);
        }else if (duiBiData.channelName.contains("家庭理财")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtlc);
        }else if (duiBiData.channelName.contains("家庭消费")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtxf);
        }else if (duiBiData.channelName.contains("金鹰卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.jykt);
        }else if (duiBiData.channelName.contains("快乐垂钓")){
            viewHodler.pd_logo.setImageResource(R.drawable.klcd);
        }else if (duiBiData.channelName.contains("卡酷卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.kkws);
        }else if (duiBiData.channelName.contains("快乐宠物")){
            viewHodler.pd_logo.setImageResource(R.drawable.klcw);
        }else if (duiBiData.channelName.contains("快乐购")){
            viewHodler.pd_logo.setImageResource(R.drawable.klg);
        }else if (duiBiData.channelName.contains("快乐购高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.klg);
        }else if (duiBiData.channelName.contains("人物")){
            viewHodler.pd_logo.setImageResource(R.drawable.rw);
        }else if (duiBiData.channelName.contains("四海钓鱼")){
            viewHodler.pd_logo.setImageResource(R.drawable.shdy);
        }else if (duiBiData.channelName.contains("时尚频道高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.sspd);
        }else if (duiBiData.channelName.contains("天元围棋")){
            viewHodler.pd_logo.setImageResource(R.drawable.tywq);
        }else if (duiBiData.channelName.contains("网路棋牌")){
            viewHodler.pd_logo.setImageResource(R.drawable.wlqp);
        }else if (duiBiData.channelName.contains("现代房产")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdfc);
        }else if (duiBiData.channelName.contains("新动漫")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdm);
        }else if (duiBiData.channelName.contains("现代女性")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdnx);
        }else if (duiBiData.channelName.contains("先锋乒羽")){
            viewHodler.pd_logo.setImageResource(R.drawable.xfpy);
        }else if (duiBiData.channelName.contains("新疆卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.xj);
        }else if (duiBiData.channelName.contains("央广健康")){
            viewHodler.pd_logo.setImageResource(R.drawable.ygjk);
        }else if (duiBiData.channelName.contains("优漫卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.ymkt);
        }else if (duiBiData.channelName.contains("游戏竞技")){
            viewHodler.pd_logo.setImageResource(R.drawable.yxjj);
        }else if (duiBiData.channelName.contains("优优宝贝")){
            viewHodler.pd_logo.setImageResource(R.drawable.yybb);
        }else if (duiBiData.channelName.contains("中华美食")){
            viewHodler.pd_logo.setImageResource(R.drawable.zhms);
        }else if (duiBiData.channelName.contains("证券资讯")){
            viewHodler.pd_logo.setImageResource(R.drawable.zjzx);
        }else if (duiBiData.channelName.contains("株洲新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.zzxwzh);
        }else if (duiBiData.channelName.contains("长沙经贸")){
            viewHodler.pd_logo.setImageResource(R.drawable.csjm);
        }else if (duiBiData.channelName.contains("长沙女性")){
            viewHodler.pd_logo.setImageResource(R.drawable.csnx);
        }else if (duiBiData.channelName.contains("长沙新闻")){
            viewHodler.pd_logo.setImageResource(R.drawable.csxw);
        }else if (duiBiData.channelName.contains("长沙政法")){
            viewHodler.pd_logo.setImageResource(R.drawable.cszf);
        }else if (duiBiData.channelName.contains("长沙政法高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.cszf);
        }else if (duiBiData.channelName.contains("金鹰纪实")){
            viewHodler.pd_logo.setImageResource(R.drawable.jyjs);
        }else if (duiBiData.channelName.contains("炫动卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdkt);
        }else if (duiBiData.channelName.contains("IPTV电竞")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_dj);
        }else if (duiBiData.channelName.contains("广场舞轮播频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.gcwlb);
        }else if (duiBiData.channelName.contains("长沙新闻高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.csxw);
        }else if (duiBiData.channelName.contains("第一剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dyjc);
        }else if (duiBiData.channelName.contains("国防军事")){
            viewHodler.pd_logo.setImageResource(R.drawable.gfjs);
        }else if (duiBiData.channelName.contains("天天院线")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttyx);
        }else if (duiBiData.channelName.contains("茶频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.cpd);
        }else if (duiBiData.channelName.contains("天天韩流")){
            viewHodler.pd_logo.setImageResource(R.drawable.tthl);
        }else if (duiBiData.channelName.contains("芒果曲艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.mgqy);
        }else if (duiBiData.channelName.contains("靓妆")){
            viewHodler.pd_logo.setImageResource(R.drawable.jz);
        }else if (duiBiData.channelName.contains("IPTV好吃厨房")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_hccf);
        }else if (duiBiData.channelName.contains("金鹰纪实高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.jyjs);
        }else if (duiBiData.channelName.contains("天天英伦")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttyl);
        }else if (duiBiData.channelName.contains("CCTV-新科动漫")){
                viewHodler.pd_logo.setImageResource(R.drawable.xkdm);
        }else if (duiBiData.channelName.contains("天天新知")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttxz);
        }else if (duiBiData.channelName.contains("美食天府")){
            viewHodler.pd_logo.setImageResource(R.drawable.mstf);
        }else if (duiBiData.channelName.contains("高尔夫网球")){
            viewHodler.pd_logo.setImageResource(R.drawable.grfwq);
        }else if (duiBiData.channelName.contains("风云剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyjc);
        }else if (duiBiData.channelName.contains("环球奇观")){
            viewHodler.pd_logo.setImageResource(R.drawable.hqqg);
        }else if (duiBiData.channelName.contains("DOX雅趣")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yq);
        }else if (duiBiData.channelName.contains("女性时尚")){
            viewHodler.pd_logo.setImageResource(R.drawable.nxss);
        }else if (duiBiData.channelName.contains("CGTN纪录频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.cgtn_jlpd);
        }else if (duiBiData.channelName.contains("怀旧剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.hjjc);
        }else if (duiBiData.channelName.contains("风云音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyyy);
        }else if (duiBiData.channelName.contains("天天剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_jc);
        }else if (duiBiData.channelName.contains("天天怡家")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yj);
        }else if (duiBiData.channelName.contains("热门综艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.rmzy);
        }else if (duiBiData.channelName.contains("风云足球")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyzq);
        }else if (duiBiData.channelName.contains("经典电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_jddy);
        }else if (duiBiData.channelName.contains("天天雅趣")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yq);
        }else if (duiBiData.channelName.contains("电视指南")){
            viewHodler.pd_logo.setImageResource(R.drawable.dszn);
        }else if (duiBiData.channelName.contains("湘潭新闻综合频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.xtxwzh);
        }else if (duiBiData.channelName.contains("热播剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_rbjc);
        }else if (duiBiData.channelName.contains("天天映画")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yh);
        }else if (duiBiData.channelName.contains("世界地理")){
            viewHodler.pd_logo.setImageResource(R.drawable.sjdl);
        }else if (duiBiData.channelName.contains("浏阳新闻高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.lyxw);
        }else if (duiBiData.channelName.contains("娄底宣传")){
            viewHodler.pd_logo.setImageResource(R.drawable.ldxz);
        }else if (duiBiData.channelName.contains("休闲指南")){
            viewHodler.pd_logo.setImageResource(R.drawable.xxzn);
        }else if (duiBiData.channelName.contains("芒果乐活")){
            viewHodler.pd_logo.setImageResource(R.drawable.mglh);
        }else if (duiBiData.channelName.contains("经典剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_jdjc);
        }else if (duiBiData.channelName.contains("家庭剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtjc);
        }else if (duiBiData.channelName.contains("古装剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.gzjc);
        }else if (duiBiData.channelName.contains("湘潭公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.xtgg);
        }else if (duiBiData.channelName.contains("IPTV足球")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_zq);
        }else if (duiBiData.channelName.contains("岳阳科教频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.yykj);
        }else if (duiBiData.channelName.contains("岳阳新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.yyxw);
        }else if (duiBiData.channelName.contains("常德新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.cdxw);
        }else if (duiBiData.channelName.contains("岳阳睛彩公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.yygg);
        }else if (duiBiData.channelName.contains("爱上4K")){
            viewHodler.pd_logo.setImageResource(R.drawable.as4k);
        }else if (duiBiData.channelName.contains("怀化新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.hhxw);
        }else if (duiBiData.channelName.contains("CCTV-老故事")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv_lgs);
        }
//        else if (duiBiData.channelName.contains("芒果")){
//            viewHodler.pd_logo.setImageResource(R.drawable.moren_pd);
//        }
        else if (duiBiData.channelName.contains("CCTV-10")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv10);
        }else if (duiBiData.channelName.contains("CCTV-11")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv11);
        }else if (duiBiData.channelName.contains("CCTV-12")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv12);
        }else if (duiBiData.channelName.contains("CCTV-13")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else if (duiBiData.channelName.contains("CCTV-14")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv14);
        }else if (duiBiData.channelName.contains("CCTV-15")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (duiBiData.channelName.contains("CCTV-22")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv22);
        }else if (duiBiData.channelName.contains("CCTV5+")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv5_jia);
        }else if (duiBiData.channelName.contains("CCTV-1")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv1);
        }else if (duiBiData.channelName.contains("CCTV-2")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv2);
        }else if (duiBiData.channelName.contains("CCTV-3")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv3);
        }else if (duiBiData.channelName.contains("CCTV-4")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv4);
        }else if (duiBiData.channelName.contains("CCTV-5")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv5);
        }else if (duiBiData.channelName.contains("CCTV-6")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv6);
        }else if (duiBiData.channelName.contains("CCTV-7")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv7);
        }else if (duiBiData.channelName.contains("CCTV-8")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv8);
        }else if (duiBiData.channelName.contains("CCTV-9")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv9);
        }else if (duiBiData.channelName.contains("CCTV-新闻")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else if (duiBiData.channelName.contains("CCTV-少儿")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv14);
        }else if (duiBiData.channelName.contains("CCTV-文化")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv_wh);
        }else if (duiBiData.channelName.contains("CCTV-音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (duiBiData.channelName.contains("CCTV-音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (duiBiData.channelName.contains("CCTV-NEWS")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else {
            viewHodler.pd_logo.setImageResource(R.drawable.default_tp);
        }


        viewHodler.tv_pdmc.setText(duiBiData.channelName);


        if (duiBiData.programName==null||"null".equals(duiBiData.programName)||"未匹配到epg".equals(duiBiData.programName)){

            duiBiData.programName="节目名称";

        }

        viewHodler.tv_jmmc.setText(duiBiData.programName);
        if (duiBiData.audienceRating==null||"null".equals(duiBiData.audienceRating)){

            duiBiData.audienceRating="0";

        }

        viewHodler.tv_ssl.setText(duiBiData.audienceRating+"%");

        if("UP".equals(duiBiData.orderStatus)){

            viewHodler.iv_status.setImageResource(R.drawable.shang_shen);
            viewHodler.iv_status.setVisibility(View.VISIBLE);
        }else if("DOWN".equals(duiBiData.orderStatus)){
            viewHodler.iv_status.setImageResource(R.drawable.xia_j);
            viewHodler.iv_status.setVisibility(View.VISIBLE);
        }else{
            viewHodler.iv_status.setVisibility(View.INVISIBLE);
        }

        viewHodler.rl_tm_hs.setAlpha(0.5f);

        if(pos!=-1){

            if(position==pos){
                viewHodler.rl_sc.setVisibility(View.VISIBLE);
            }else{
                viewHodler.rl_sc.setVisibility(View.GONE);
            }

        }else{
            viewHodler.rl_sc.setVisibility(View.GONE);
        }

        viewHodler.tv_del_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choiceDelListener.OnChoiceDelListener(position);

            }
        });
        viewHodler.rl_tm_hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceDelListener.OnRecoveryListener(position);
            }
        });


        return convertView;
    }

    class ViewHodler{

        RelativeLayout rl_tm_hs;
        RelativeLayout rl_sc;
        TextView tv_pdmc,tv_jmmc,tv_ssl;
        ImageView iv_status;
        TextView tv_del_pd;
        ImageView pd_logo;
    }

    public void setPos(int pos){

        this.pos=pos;

    }

}
