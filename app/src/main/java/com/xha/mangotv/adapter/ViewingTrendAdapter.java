package com.xha.mangotv.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xha.mangotv.R;
import com.xha.mangotv.entity.DuiBiData;
import com.xha.mangotv.entity.LishiPD;
import com.xha.mangotv.util.ChoiceDelListener;

import java.util.List;

/**
 * Created by ysq on 2018/10/24.
 */

public class ViewingTrendAdapter extends BaseAdapter {

    private Context context;
    private List<LishiPD> data;
    private int pos=-1;
    private ChoiceDelListener choiceDelListener;
    public ViewingTrendAdapter(Context context,List<LishiPD> data,ChoiceDelListener choiceDelListener){
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
            convertView = View.inflate(context, R.layout.item_vt,null);
            viewHodler = new ViewHodler();
            viewHodler.rl_tm_hs = convertView.findViewById(R.id.rl_tm_hs);
            viewHodler.rl_sc=convertView.findViewById(R.id.rl_sc);
            viewHodler.tv_pdmc=convertView.findViewById(R.id.tv_pdmc);
            viewHodler.tv_del_pd=convertView.findViewById(R.id.tv_del_pd);
            viewHodler.pd_logo=convertView.findViewById(R.id.pd_logo);
            convertView.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) convertView.getTag();
        }
        LishiPD lishiPD = data.get(position);
        if (lishiPD.name.contains("江苏靓妆")){
            viewHodler.pd_logo.setImageResource(R.drawable.jz);
        }else if (lishiPD.name.contains("安徽")){
            viewHodler.pd_logo.setImageResource(R.drawable.ah);
        }else if (lishiPD.name.contains("北京")){
            viewHodler.pd_logo.setImageResource(R.drawable.bjws);
        }else if (lishiPD.name.contains("重庆")){
            viewHodler.pd_logo.setImageResource(R.drawable.cq);
        }else if (lishiPD.name.contains("东方")){
            viewHodler.pd_logo.setImageResource(R.drawable.dfws);
        }else if (lishiPD.name.contains("东南")){
            viewHodler.pd_logo.setImageResource(R.drawable.dnws);
        }else if (lishiPD.name.contains("凤凰")){
            viewHodler.pd_logo.setImageResource(R.drawable.fhws);
        }else if (lishiPD.name.contains("甘肃")){
            viewHodler.pd_logo.setImageResource(R.drawable.gsws);
        }else if (lishiPD.name.contains("广东")){
            viewHodler.pd_logo.setImageResource(R.drawable.gdws);
        }else if (lishiPD.name.contains("广西")){
            viewHodler.pd_logo.setImageResource(R.drawable.gxws);
        }else if (lishiPD.name.contains("贵州")){
            viewHodler.pd_logo.setImageResource(R.drawable.gzws);
        }else if (lishiPD.name.contains("河北")){
            viewHodler.pd_logo.setImageResource(R.drawable.hbws);
        }else if (lishiPD.name.contains("河南")){
            viewHodler.pd_logo.setImageResource(R.drawable.hn);
        }else if (lishiPD.name.contains("黑龙江")){
            viewHodler.pd_logo.setImageResource(R.drawable.hlj);
        }else if (lishiPD.name.contains("湖北")){
            viewHodler.pd_logo.setImageResource(R.drawable.hu_bws);
        }else if (lishiPD.name.contains("湖南电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.hndy);
        }else if (lishiPD.name.contains("湖南经视")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnjs);
        }else if (lishiPD.name.contains("湖南教育")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnjy);
        }else if (lishiPD.name.contains("湖南电视剧")){
            viewHodler.pd_logo.setImageResource(R.drawable.hndsj);
        }else if (lishiPD.name.contains("湖南都市")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnds);
        }else if (lishiPD.name.contains("湖南公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.hngg);
        }else if (lishiPD.name.contains("湖南娱乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.hnyl);
        } else if (lishiPD.name.contains("湖南国际")){
            viewHodler.pd_logo.setImageResource(R.drawable.hngj);
        } else if (lishiPD.name.contains("湖南")){
            viewHodler.pd_logo.setImageResource(R.drawable.moren_pd);
        }else if (lishiPD.name.contains("吉林")){
            viewHodler.pd_logo.setImageResource(R.drawable.jlws);
        }else if (lishiPD.name.contains("江苏")){
            viewHodler.pd_logo.setImageResource(R.drawable.jsws);
        }else if (lishiPD.name.contains("江西")){
            viewHodler.pd_logo.setImageResource(R.drawable.jxws);
        }else if (lishiPD.name.contains("辽宁")){
            viewHodler.pd_logo.setImageResource(R.drawable.ln);
        }else if (lishiPD.name.contains("旅游卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.lyws);
        }else if (lishiPD.name.contains("内蒙古")){
            viewHodler.pd_logo.setImageResource(R.drawable.nmg);
        }else if (lishiPD.name.contains("宁夏")){
            viewHodler.pd_logo.setImageResource(R.drawable.nxws);
        }else if (lishiPD.name.contains("青海")){
            viewHodler.pd_logo.setImageResource(R.drawable.qhg);
        }else if (lishiPD.name.contains("山东")){
            viewHodler.pd_logo.setImageResource(R.drawable.sdws);
        }else if (lishiPD.name.contains("山西")){
            viewHodler.pd_logo.setImageResource(R.drawable.sxws);
        }else if (lishiPD.name.contains("陕西")){
            viewHodler.pd_logo.setImageResource(R.drawable.shan_xws);
        }else if (lishiPD.name.contains("四川")){
            viewHodler.pd_logo.setImageResource(R.drawable.scws);
        }else if (lishiPD.name.contains("天津")){
            viewHodler.pd_logo.setImageResource(R.drawable.tj);
        }else if (lishiPD.name.contains("西藏")){
            viewHodler.pd_logo.setImageResource(R.drawable.xzws);
        }else if (lishiPD.name.contains("新疆")){
            viewHodler.pd_logo.setImageResource(R.drawable.xj);
        }else if (lishiPD.name.contains("云南")){
            viewHodler.pd_logo.setImageResource(R.drawable.ynws);
        }else if (lishiPD.name.contains("浙江")){
            viewHodler.pd_logo.setImageResource(R.drawable.zjws);
        }else if (lishiPD.name.contains("深圳")){
            viewHodler.pd_logo.setImageResource(R.drawable.szws);
        }else if (lishiPD.name.contains("中国气象")){
            viewHodler.pd_logo.setImageResource(R.drawable.zgqx);
        } else if (lishiPD.name.contains("南方卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.gdnfws);
        }else if (lishiPD.name.contains("魅力时尚")){
            viewHodler.pd_logo.setImageResource(R.drawable.mlss);
        }else if (lishiPD.name.contains("时尚频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.sspd);
        }else if (lishiPD.name.contains("卡酷动画")){
            viewHodler.pd_logo.setImageResource(R.drawable.kkws);
        }else if (lishiPD.name.contains("百姓健康")){
            viewHodler.pd_logo.setImageResource(R.drawable.chtv);
        }else if (lishiPD.name.contains("财富天下")){
            viewHodler.pd_logo.setImageResource(R.drawable.cftx);
        }else if (lishiPD.name.contains("CHC动作电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_dzdy);
        }else if (lishiPD.name.contains("CHC高清电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_gqdy);
        }else if (lishiPD.name.contains("CHC家庭影院")){
            viewHodler.pd_logo.setImageResource(R.drawable.chc_jtyy);
        }else if (lishiPD.name.contains("DOX韩流")){
            viewHodler.pd_logo.setImageResource(R.drawable.tthl);
        }else if (lishiPD.name.contains("DOX剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_jc);
        }else if (lishiPD.name.contains("DOX新艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_xy);
        }else if (lishiPD.name.contains("DOX新知")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_xz);
        }else if (lishiPD.name.contains("DOX映画")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yh);
        }else if (lishiPD.name.contains("DOX怡家")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yj);
        }else if (lishiPD.name.contains("DOX英伦")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yl);
        }else if (lishiPD.name.contains("DOX院线")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yx);
        }else if (lishiPD.name.contains("高尔夫频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.grfwq);
        }else if (lishiPD.name.contains("芒果演艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.mgyy);
        }else if (lishiPD.name.contains("嘉丽购")){
            viewHodler.pd_logo.setImageResource(R.drawable.jlg);
        }else if (lishiPD.name.contains("家庭理财")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtlc);
        }else if (lishiPD.name.contains("家庭消费")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtxf);
        }else if (lishiPD.name.contains("金鹰卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.jykt);
        }else if (lishiPD.name.contains("快乐垂钓")){
            viewHodler.pd_logo.setImageResource(R.drawable.klcd);
        }else if (lishiPD.name.contains("卡酷卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.kkws);
        }else if (lishiPD.name.contains("快乐宠物")){
            viewHodler.pd_logo.setImageResource(R.drawable.klcw);
        }else if (lishiPD.name.contains("快乐购")){
            viewHodler.pd_logo.setImageResource(R.drawable.klg);
        }else if (lishiPD.name.contains("快乐购高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.klg);
        }else if (lishiPD.name.contains("人物")){
            viewHodler.pd_logo.setImageResource(R.drawable.rw);
        }else if (lishiPD.name.contains("四海钓鱼")){
            viewHodler.pd_logo.setImageResource(R.drawable.shdy);
        }else if (lishiPD.name.contains("时尚频道高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.sspd);
        }else if (lishiPD.name.contains("天元围棋")){
            viewHodler.pd_logo.setImageResource(R.drawable.tywq);
        }else if (lishiPD.name.contains("网路棋牌")){
            viewHodler.pd_logo.setImageResource(R.drawable.wlqp);
        }else if (lishiPD.name.contains("现代房产")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdfc);
        }else if (lishiPD.name.contains("新动漫")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdm);
        }else if (lishiPD.name.contains("现代女性")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdnx);
        }else if (lishiPD.name.contains("先锋乒羽")){
            viewHodler.pd_logo.setImageResource(R.drawable.xfpy);
        }else if (lishiPD.name.contains("新疆卫视")){
            viewHodler.pd_logo.setImageResource(R.drawable.xj);
        }else if (lishiPD.name.contains("央广健康")){
            viewHodler.pd_logo.setImageResource(R.drawable.ygjk);
        }else if (lishiPD.name.contains("优漫卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.ymkt);
        }else if (lishiPD.name.contains("游戏竞技")){
            viewHodler.pd_logo.setImageResource(R.drawable.yxjj);
        }else if (lishiPD.name.contains("优优宝贝")){
            viewHodler.pd_logo.setImageResource(R.drawable.yybb);
        }else if (lishiPD.name.contains("中华美食")){
            viewHodler.pd_logo.setImageResource(R.drawable.zhms);
        }else if (lishiPD.name.contains("证券资讯")){
            viewHodler.pd_logo.setImageResource(R.drawable.zjzx);
        }else if (lishiPD.name.contains("株洲新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.zzxwzh);
        }else if (lishiPD.name.contains("长沙经贸")){
            viewHodler.pd_logo.setImageResource(R.drawable.csjm);
        }else if (lishiPD.name.contains("长沙女性")){
            viewHodler.pd_logo.setImageResource(R.drawable.csnx);
        }else if (lishiPD.name.contains("长沙新闻")){
            viewHodler.pd_logo.setImageResource(R.drawable.csxw);
        }else if (lishiPD.name.contains("长沙政法")){
            viewHodler.pd_logo.setImageResource(R.drawable.cszf);
        }else if (lishiPD.name.contains("长沙政法高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.cszf);
        }else if (lishiPD.name.contains("金鹰纪实")){
            viewHodler.pd_logo.setImageResource(R.drawable.jyjs);
        }else if (lishiPD.name.contains("炫动卡通")){
            viewHodler.pd_logo.setImageResource(R.drawable.xdkt);
        }else if (lishiPD.name.contains("IPTV电竞")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_dj);
        }else if (lishiPD.name.contains("广场舞轮播频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.gcwlb);
        }else if (lishiPD.name.contains("长沙新闻高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.csxw);
        }else if (lishiPD.name.contains("第一剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dyjc);
        }else if (lishiPD.name.contains("国防军事")){
            viewHodler.pd_logo.setImageResource(R.drawable.gfjs);
        }else if (lishiPD.name.contains("天天院线")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttyx);
        }else if (lishiPD.name.contains("茶频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.cpd);
        }else if (lishiPD.name.contains("天天韩流")){
            viewHodler.pd_logo.setImageResource(R.drawable.tthl);
        }else if (lishiPD.name.contains("芒果曲艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.mgqy);
        }else if (lishiPD.name.contains("靓妆")){
            viewHodler.pd_logo.setImageResource(R.drawable.jz);
        }else if (lishiPD.name.contains("IPTV好吃厨房")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_hccf);
        }else if (lishiPD.name.contains("金鹰纪实高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.jyjs);
        }else if (lishiPD.name.contains("天天英伦")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttyl);
        }else if (lishiPD.name.contains("CCTV-新科动漫")){
            viewHodler.pd_logo.setImageResource(R.drawable.xkdm);
        }else if (lishiPD.name.contains("天天新知")){
            viewHodler.pd_logo.setImageResource(R.drawable.ttxz);
        }else if (lishiPD.name.contains("美食天府")){
            viewHodler.pd_logo.setImageResource(R.drawable.mstf);
        }else if (lishiPD.name.contains("高尔夫网球")){
            viewHodler.pd_logo.setImageResource(R.drawable.grfwq);
        }else if (lishiPD.name.contains("风云剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyjc);
        }else if (lishiPD.name.contains("环球奇观")){
            viewHodler.pd_logo.setImageResource(R.drawable.hqqg);
        }else if (lishiPD.name.contains("DOX雅趣")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yq);
        }else if (lishiPD.name.contains("女性时尚")){
            viewHodler.pd_logo.setImageResource(R.drawable.nxss);
        }else if (lishiPD.name.contains("CGTN纪录频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.cgtn_jlpd);
        }else if (lishiPD.name.contains("怀旧剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.hjjc);
        }else if (lishiPD.name.contains("风云音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyyy);
        }else if (lishiPD.name.contains("天天剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_jc);
        }else if (lishiPD.name.contains("天天怡家")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yj);
        }else if (lishiPD.name.contains("热门综艺")){
            viewHodler.pd_logo.setImageResource(R.drawable.rmzy);
        }else if (lishiPD.name.contains("风云足球")){
            viewHodler.pd_logo.setImageResource(R.drawable.fyzq);
        }else if (lishiPD.name.contains("经典电影")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_jddy);
        }else if (lishiPD.name.contains("天天雅趣")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yq);
        }else if (lishiPD.name.contains("电视指南")){
            viewHodler.pd_logo.setImageResource(R.drawable.dszn);
        }else if (lishiPD.name.contains("湘潭新闻综合频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.xtxwzh);
        }else if (lishiPD.name.contains("热播剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_rbjc);
        }else if (lishiPD.name.contains("天天映画")){
            viewHodler.pd_logo.setImageResource(R.drawable.dox_yh);
        }else if (lishiPD.name.contains("世界地理")){
            viewHodler.pd_logo.setImageResource(R.drawable.sjdl);
        }else if (lishiPD.name.contains("浏阳新闻高清")){
            viewHodler.pd_logo.setImageResource(R.drawable.lyxw);
        }else if (lishiPD.name.contains("娄底宣传")){
            viewHodler.pd_logo.setImageResource(R.drawable.ldxz);
        }else if (lishiPD.name.contains("休闲指南")){
            viewHodler.pd_logo.setImageResource(R.drawable.xxzn);
        }else if (lishiPD.name.contains("芒果乐活")){
            viewHodler.pd_logo.setImageResource(R.drawable.mglh);
        }else if (lishiPD.name.contains("经典剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_jdjc);
        }else if (lishiPD.name.contains("家庭剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.jtjc);
        }else if (lishiPD.name.contains("古装剧场")){
            viewHodler.pd_logo.setImageResource(R.drawable.gzjc);
        }else if (lishiPD.name.contains("湘潭公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.xtgg);
        }else if (lishiPD.name.contains("IPTV足球")){
            viewHodler.pd_logo.setImageResource(R.drawable.iptv_zq);
        }else if (lishiPD.name.contains("岳阳科教频道")){
            viewHodler.pd_logo.setImageResource(R.drawable.yykj);
        }else if (lishiPD.name.contains("岳阳新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.yyxw);
        }else if (lishiPD.name.contains("常德新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.cdxw);
        }else if (lishiPD.name.contains("岳阳睛彩公共")){
            viewHodler.pd_logo.setImageResource(R.drawable.yygg);
        }else if (lishiPD.name.contains("爱上4K")){
            viewHodler.pd_logo.setImageResource(R.drawable.as4k);
        }else if (lishiPD.name.contains("怀化新闻综合")){
            viewHodler.pd_logo.setImageResource(R.drawable.hhxw);
        }else if (lishiPD.name.contains("CCTV-老故事")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv_lgs);
        }
//        else if (lishiPD.name.contains("芒果")){
//            viewHodler.pd_logo.setImageResource(R.drawable.moren_pd);
//        }
        else if (lishiPD.name.contains("CCTV-10")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv10);
        }else if (lishiPD.name.contains("CCTV-11")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv11);
        }else if (lishiPD.name.contains("CCTV-12")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv12);
        }else if (lishiPD.name.contains("CCTV-13")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else if (lishiPD.name.contains("CCTV-14")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv14);
        }else if (lishiPD.name.contains("CCTV-15")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (lishiPD.name.contains("CCTV-22")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv22);
        }else if (lishiPD.name.contains("CCTV5+")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv5_jia);
        }else if (lishiPD.name.contains("CCTV-1")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv1);
        }else if (lishiPD.name.contains("CCTV-2")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv2);
        }else if (lishiPD.name.contains("CCTV-3")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv3);
        }else if (lishiPD.name.contains("CCTV-4")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv4);
        }else if (lishiPD.name.contains("CCTV-5")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv5);
        }else if (lishiPD.name.contains("CCTV-6")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv6);
        }else if (lishiPD.name.contains("CCTV-7")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv7);
        }else if (lishiPD.name.contains("CCTV-8")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv8);
        }else if (lishiPD.name.contains("CCTV-9")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv9);
        }else if (lishiPD.name.contains("CCTV-新闻")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else if (lishiPD.name.contains("CCTV-少儿")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv14);
        }else if (lishiPD.name.contains("CCTV-文化")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv_wh);
        }else if (lishiPD.name.contains("CCTV-音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (lishiPD.name.contains("CCTV-音乐")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv15);
        }else if (lishiPD.name.contains("CCTV-NEWS")){
            viewHodler.pd_logo.setImageResource(R.drawable.cctv13);
        }else {
            viewHodler.pd_logo.setImageResource(R.drawable.default_tp);
        }

        viewHodler.tv_pdmc.setText(lishiPD.name);
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
                Log.e("TAG","点击删除了--==--=-=--------------=-=-==========");
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
        TextView tv_pdmc;
        RelativeLayout rl_sc;
        RelativeLayout rl_tm_hs;
        TextView tv_del_pd;
        ImageView pd_logo;
    }
    public void setPos(int pos){
        this.pos=pos;
    }
}
