package com.xha.mangotv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xha.mangotv.Presenter.AddContrastChannelPresenter;
import com.xha.mangotv.Presenter.AddContrastChannelView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.ContrastChannelAdapter;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.entity.AddContrastChannel;
import com.xha.mangotv.entity.ContrastChannelData;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.AppMenu;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import com.xha.mangotv.view.SelectClickListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/9/3.
 */

public class AddContrastChannelActivity extends BaseActivity implements AddContrastChannelView{
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private TextView title_content;
    private LinearLayout ll_content;
    public static int count = 0;
    private TextView tv_tj_qx,tv_tj_qr;
    private List<ContrastChannelData> xzdata;
    private Info Info;
    private List<String> pd_code_list;
    private String type;
    private String channelCode;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private AddContrastChannelPresenter addContrastChannelPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_add_contrast_channel);
        rl_bt = (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        title_content = (TextView) findViewById(R.id.title_content);
        tv_tj_qr= (TextView) findViewById(R.id.tv_tj_qr);
        tv_tj_qx= (TextView) findViewById(R.id.tv_tj_qx);
        Info = (com.xha.mangotv.entity.Info) getIntent().getSerializableExtra("Info");
        type=getIntent().getStringExtra("type");
        pd_code_list=Info.pd_code_list;
        channelCode = pd_code_list.get(0);
        pd_code_list.remove(0);
        xzdata = new ArrayList<>();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.back);
        title_content.setText("添加对比频道");
        title_content.setVisibility(View.VISIBLE);
        addContrastChannelPresenter = new AddContrastChannelPresenter(this);
        getPDData();
        iv_fanhui.setOnClickListener(onClickListener);
        tv_tj_qx.setOnClickListener(onClickListener);
        tv_tj_qr.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_tj_qx:
//                    for (int i=0;i<xzdata.size();i++){
//                        Log.e("TAG","频道数据::::"+xzdata.get(i).channelName);
//                    }
                    finish();
                    break;
                case R.id.iv_fanhui:
                    finish();
                    break;
                case R.id.tv_tj_qr:
                    Log.d("TAG","count数据:::"+count);
                    Info info = new Info();
                    info.contrastChannelDatas=new ArrayList<>();
                    info.contrastChannelDatas.addAll(xzdata);
                    Intent intent = new Intent();
                    intent.setAction(ConstantValues.ADD_PD);
                    intent.putExtra("pd",info);
                    sendBroadcast(intent);
                    finish();

                    break;
            }
        }
    };
    private void getPDData(){
        if(NetUtil.checkNet(AddContrastChannelActivity.this)){
            addContrastChannelPresenter.getDisposeData();
        }else {
            Toast.makeText(AddContrastChannelActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public String getCCUrl() {
//        return ConstantValues.URI_YONG_TEST+"channel/getAllChannelWithChannelGroup";
        return ConstantValues.AUTH_SITE+"interface";
    }
    @Override
    public int getCCCode() {
        return HttpConstants.search_news01;
    }
    @Override
    public String getCCBody() {
//        JSONObject jsonObject=null;
//            try {
//                HashMap<String,String> map_yc = new HashMap<>();
//                map_yc.put("phoneNum","13370133060");
//                jsonObject = new JSONObject(map_yc);
//                if ("频道收视".equals(type)){
//                jsonObject.put("appMenu", AppMenu.CHANNELVIEWINGTREND.toString());
//                }else{
//                    jsonObject.put("appMenu",AppMenu.REALTIME.toString());
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }finally {
//                return jsonObject.toString();
//            }


        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","channel/getAllChannelWithChannelGroup");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
            try {
                jsonObject= JSONTools.parseMapToJson(map);
                jsonObject.put("sign",sign);
                jsonObject.put("phoneNum",PreUtil.getInstance().getString(PreContact.username));
                if ("频道收视".equals(type)){
                    jsonObject.put("appMenu", "CHANNELVIEWINGTREND");
                }else{
                    jsonObject.put("appMenu","REALTIME");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }
    @Override
    public void getCCDataFailureMsg(String msg) {
        Toast.makeText(AddContrastChannelActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void getCCData(Info info) {


        final List<AddContrastChannel> addContrastChannels = info.addContrastChannels;

        List<ContrastChannelData> dataList = new ArrayList<>();

        for (int z = 0; z < addContrastChannels.size(); z++) {
            View view = View.inflate(AddContrastChannelActivity.this, R.layout.item_pd, null);
            RecyclerView rv_yspd = (RecyclerView) view.findViewById(R.id.rv_yspd);
            TextView tv_pdz =view.findViewById(R.id.tv_pdz);
            final AddContrastChannel addContrastChannel = addContrastChannels.get(z);
            tv_pdz.setText(addContrastChannel.name);
            final List<ContrastChannelData> mlist = new ArrayList<>();
                final List<Boolean> zt = new ArrayList<>();
                for (int j = 0; j < addContrastChannel.mlist.size(); j++) {
                    mlist.add(addContrastChannel.mlist.get(j));
                    ContrastChannelData contrastChannelData = addContrastChannel.mlist.get(j);
                    int start=0;
                    for (int a=0; a<pd_code_list.size();a++){
                        if(contrastChannelData.channelCode.equals(pd_code_list.get(a))){
                            //初始化 关联不同组的同一个id选中项count值
                            if (dataList.size()>0){
                                for (int k = 0;k<dataList.size();k++){
                                    ContrastChannelData contrastChannelData1 = dataList.get(k);
                                    if (contrastChannelData.channelCode.equals(contrastChannelData1.channelCode)){//具有相同的数据
                                        start=2;
                                        break;
                                    }else{
                                        dataList.add(contrastChannelData);
                                        start=1;
                                        break;
                                    }
                                }
                            }else{
                                dataList.add(contrastChannelData);
                                start=1;
                                break;
                            }
                        }
                    }
                    if(start==0){
                        zt.add(false);
                    }else if (start==1){
                        zt.add(true);
                        xzdata.add(contrastChannelData);
                        count++;
                    }else{
                        zt.add(true);
                    }
                }
                final ContrastChannelAdapter contrastChannelAdapter1 = new ContrastChannelAdapter(channelCode,R.layout.item_contrast_channel, mlist, zt,AddContrastChannelActivity.this,count);
                contrastChannelAdapter1.setOnSelectClickListener(new SelectClickListener() {
                    @Override
                    public void OnSelectClickListener(int pos, boolean state,ContrastChannelData item) {
                        if(state){
                            boolean isyy=false;
                            for (int i=0;i<xzdata.size();i++){
                                ContrastChannelData contrastChannelData = xzdata.get(i);
                                if(item.channelCode.equals(contrastChannelData.channelCode)){
                                    isyy=true;
                                    break;
                                }
                            }
                            if (!isyy){
                                zt.set(pos,true);
                                count++;
                                xzdata.add(item);

                                //关联不同组的同一个id选中项添加
                                for(int i=0;i<addContrastChannels.size();i++){
                                    ViewGroup childAt = (ViewGroup) ll_content.getChildAt(i);
                                    RecyclerView recyclerView= (RecyclerView) childAt.getChildAt(1);
                                    ContrastChannelAdapter contrastChannelAdapter= (ContrastChannelAdapter) recyclerView.getAdapter();
                                    AddContrastChannel addContrastChannel1 = addContrastChannels.get(i);
                                    List<Boolean> state1 = contrastChannelAdapter.getState();
                                    for (int j = 0; j < addContrastChannel1.mlist.size(); j++) {
                                        ContrastChannelData contrastChannelData = addContrastChannel1.mlist.get(j);
                                        if (item.channelCode.equals(contrastChannelData.channelCode)){
                                            state1.set(j,true);
                                        }
                                    }
                                    contrastChannelAdapter.setState(state1);
                                }
                            }
                        }else{
                            zt.set(pos,false);
                            count--;
                            for(int i=0;i<xzdata.size();i++){
                                ContrastChannelData contrastChannelData = xzdata.get(i);
                                if(item.channelCode.equals(contrastChannelData.channelCode)){
                                    xzdata.remove(i);
                                    break;
                                }
                            }

                            //关联不同组的同一个id选中项取消
                            for(int i=0;i<addContrastChannels.size();i++){
                                ViewGroup childAt = (ViewGroup) ll_content.getChildAt(i);
                                RecyclerView recyclerView= (RecyclerView) childAt.getChildAt(1);
                                ContrastChannelAdapter contrastChannelAdapter= (ContrastChannelAdapter) recyclerView.getAdapter();
                                AddContrastChannel addContrastChannel1 = addContrastChannels.get(i);
                                List<Boolean> state1 = contrastChannelAdapter.getState();
                                for (int j = 0; j < addContrastChannel1.mlist.size(); j++) {
                                    ContrastChannelData contrastChannelData = addContrastChannel1.mlist.get(j);
                                    if (item.channelCode.equals(contrastChannelData.channelCode)){
                                        state1.set(j,false);
                                    }
                                }
                                contrastChannelAdapter.setState(state1);
                            }

                        }
                        for(int i=0;i<addContrastChannels.size();i++){
                            ViewGroup childAt = (ViewGroup) ll_content.getChildAt(i);
                            RecyclerView recyclerView= (RecyclerView) childAt.getChildAt(1);
                            ContrastChannelAdapter contrastChannelAdapter= (ContrastChannelAdapter) recyclerView.getAdapter();
                            contrastChannelAdapter.setCount(count);
                            contrastChannelAdapter.notifyDataSetChanged();
                        }
                    }
                });
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(AddContrastChannelActivity.this, 3);
                rv_yspd.setLayoutManager(gridLayoutManager1);
                gridLayoutManager1.setAutoMeasureEnabled(true);
                rv_yspd.setNestedScrollingEnabled(false);
                rv_yspd.setAdapter(contrastChannelAdapter1);
            ll_content.addView(view);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        count=0;
    }
}
