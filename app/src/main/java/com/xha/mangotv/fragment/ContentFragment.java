package com.xha.mangotv.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.SwitchPreference;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xha.mangotv.MainActivity;
import com.xha.mangotv.Presenter.RealTimePresenter;
import com.xha.mangotv.Presenter.RealTimeView;
import com.xha.mangotv.R;
import com.xha.mangotv.activity.AddressActivity;
import com.xha.mangotv.activity.ViewingDetailsActivity;
import com.xha.mangotv.adapter.CoinAdapter;
import com.xha.mangotv.adapter.CommonViewHolder;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.config.Config;
import com.xha.mangotv.entity.CoinInfo;
import com.xha.mangotv.entity.Index;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.RealTimeData;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.BaseDialog;
import com.xha.mangotv.view.HRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysq on 2018/8/31.
 */

public class ContentFragment extends BaseFragment implements RealTimeView{
    private List<RealTimeData> list=new ArrayList<>();
    private HRecyclerView hRecyclerView;
    private RealTimePresenter realTimePresenter ;
    private String channelGroupCode="";
    private String liveRealCompareType="SECOND";
    private String areaCode="14300";
    private CoinAdapter adapter;
    private TimerTask realTimetask;
    private Timer realTimetimer;
    private boolean iszs;
    private Handler handler = new Handler();
    private List<Index> tabList;
    private static int pos=-1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        this.activity= (Activity) context;
    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_content;
    }

    @Override
    protected void lazyLoad() {
        View contentView = getContentView();
        Bundle arguments = getArguments();
        channelGroupCode=arguments.getString("channelGroupCode");
        iszs=arguments.getBoolean("iszs");
        hRecyclerView= (HRecyclerView) contentView.findViewById(R.id.id_hrecyclerview);
        if(realTimePresenter==null){
            realTimePresenter = new RealTimePresenter(this);
        }
        String string = PreUtil.getInstance().getString(ConstantValues.indexSet);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
            tabList = new ArrayList<>();
            List<String> tl = new ArrayList<>();
            tabList.add(new Index("节目名称 ",0));
            for (int i=0;i<jsonArray.length();i++){
                String tab_name=jsonArray.getString(i);
                if("audienceRating".equals(tab_name)){
                    tabList.add(new Index("收视率",1));
                }else if ("marketShare".equals(tab_name)){
                    tabList.add(new Index("收视份额",2));
                }else if ("stbNum".equals(tab_name)){
                    tabList.add(new Index("在线用户数",3));
                }
            }

            Collections.sort(tabList, new Comparator<Index>() {

                @Override
                public int compare(Index o1, Index o2) {
                    int i = o1.getScore() - o2.getScore();
                    return i;
                }
            });
            Log.e("TAG","数据个数是:::"+tabList.size());
            String[] tab = new String[tabList.size()];
            for (int i=0;i<tabList.size();i++){
                tab[i]=tabList.get(i).name;
            }
//            String[] tab = {"节目名称 ","用户数","收视率","收视份额"};
            String[] mLeftTextList = new String[]{"频道名称"};
            hRecyclerView.setHeaderListData(tab,mLeftTextList);
            if (channelGroupCode != null && channelGroupCode.length() > 0) {
                if (iszs) {
                    stopRealTimeData();
                    getRealTimeData();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        pos=-1;
        stopRealTimeData();
    }
    private void getData(){
        if(getActivity()!=null){
            if(NetUtil.checkNet(getActivity())){
                realTimePresenter.getDisposeData();
            }else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    @Override
    public String getRTUrl() {
//        return ConstantValues.URI_YONG_TEST+"appReal/getRealLiveDataByChannelGroup";
        return ConstantValues.AUTH_SITE+"interface";
    }
    @Override
    public int getRTCode() {
        return HttpConstants.search_news01;
    }
    @Override
    public String getRTBody() {
//        HashMap<String,String> map = new HashMap<>();
//        map.put("channelGroupCode",channelGroupCode);
//        map.put("liveRealCompareType",liveRealCompareType);
//        map.put("areaCode",areaCode);
////        15510066877
//        map.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
//        JSONObject jo = new JSONObject(map);
//        Log.e("TAG","获取参数:::"+jo.toString());
//        return jo.toString();

        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","appReal/getRealLiveDataByChannelGroup");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("sign",sign);
            jsonObject.put("channelGroupCode",channelGroupCode);
            jsonObject.put("liveRealCompareType",liveRealCompareType);
            jsonObject.put("areaCode",areaCode);
    //        15510066877
            jsonObject.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::channelGroupCode==="+channelGroupCode+"=="+jsonObject.toString());
            return jsonObject.toString();
        }
    }
    @Override
    public void getRTDataFailureMsg(String msg) {
        list.clear();
        if (adapter!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
        if (adapter==null){
            if (hRecyclerView.getHeaderCount() == 5) {
                setLayout(R.layout.item_content5);
            }else
            if(hRecyclerView.getHeaderCount()==4){
                setLayout(R.layout.item_content4);
            }else  if (hRecyclerView.getHeaderCount()==3){
                setLayout(R.layout.item_content3);
            }
            else  if (hRecyclerView.getHeaderCount()==2){
                setLayout(R.layout.item_content2);
            }
            else{
                setLayout(R.layout.item_content);
            }
            hRecyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    public void setLayout(int layout){
        adapter= new CoinAdapter(getContext(), list,tabList, layout, new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getActivity(), ViewingDetailsActivity.class);
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("RealTimeData",list.get(position));
                RealTimeData realTimeData=list.get(position);
                intent.putExtra("channelCode",realTimeData.channelCode);
                Log.e("TAG","数量1===="+list.get(position).inflowLists.size()+",数量2==="+list.get(position).outflowLists.size());
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(int position) {

            }
        });
    }

    @Override
    public void getRTData(final Info info) {
        Log.e("TAG", "频道组:::" + channelGroupCode);
        list.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        list.addAll(info.realTimeDatas);
        Log.e("TAG", "执行陈宫了数据为::::" + list.size());
        Log.e("TAG","小条目数据:::"+hRecyclerView.getHeaderCount());
        if (adapter==null){
        if (hRecyclerView!=null){
            if (hRecyclerView.getHeaderCount() == 5) {
                setLayout(R.layout.item_content5);
            }else
            if(hRecyclerView.getHeaderCount()==4){
                setLayout(R.layout.item_content4);
            }else  if (hRecyclerView.getHeaderCount()==3){
                setLayout(R.layout.item_content3);
            }
            else  if (hRecyclerView.getHeaderCount()==2){
                setLayout(R.layout.item_content2);
            }
            else{
                setLayout(R.layout.item_content);
            }
            hRecyclerView.setAdapter(adapter);
        }
    }else{
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
        if (pos!=-1){
            Intent broad = new Intent();
            broad.setAction(ConstantValues.SX_INOUT);
            broad.putExtra("RealTimeData",list.get(pos));
            if (getActivity()!=null){
                getActivity().sendBroadcast(broad);
            }
        }
    }
    //刷新列表的
    public void setRefreshArea(String areaCode){
        this.areaCode=areaCode;
        if(realTimePresenter==null){
            realTimePresenter = new RealTimePresenter(this);
        }
        if(getUserVisibleHint()){
            if(adapter!=null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            if(channelGroupCode!=null&&channelGroupCode.length()>0){
                stopRealTimeData();
                getRealTimeData();
            }
        }
    }
    /**
     * 开启定时任务轮询信息更新
     */
    private void getRealTimeData() {
        if (realTimetimer == null) {
            realTimetimer = new Timer();
        }
        if (realTimetask == null) {
            realTimetask = new TimerTask() {
                @Override
                public void run() {
                    if(channelGroupCode!=null&&channelGroupCode.length()>0){
                        getData();
                    }
                }
            };
        }
        if (realTimetask != null && realTimetimer != null) {
            realTimetimer.schedule(realTimetask, 0, 10 * 1000);
        }
    }
    //关闭实时获取的资源
    public void stopRealTimeData() {
        if (realTimetimer != null) {
            realTimetimer.cancel();
            realTimetimer = null;
        }
        if (realTimetask != null) {
            realTimetask.cancel();
            realTimetask = null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        pos=-1;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRealTimeData();
    }
}
