package com.xha.mangotv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xha.mangotv.Presenter.AddressPresenter;
import com.xha.mangotv.Presenter.AddressView;
import com.xha.mangotv.R;
import com.xha.mangotv.adapter.AddressAdapter;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.entity.Address;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.okhttpUtils.HttpConstants;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.JSONTools;
import com.xha.mangotv.util.MACUtil;
import com.xha.mangotv.util.MyContants;
import com.xha.mangotv.util.NetUtil;
import com.xha.mangotv.util.PreContact;
import com.xha.mangotv.util.PreUtil;
import com.xha.mangotv.util.Sign;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ysq on 2018/8/30.
 */

public class AddressActivity extends BaseActivity implements AddressView{

    private List<Address> list;
    private RecyclerView address_recycle;
    private RelativeLayout rl_bt;
    private ImageView iv_fanhui;
    private AddressAdapter adapter;
    private AddressPresenter addressPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_address);
        address_recycle= (RecyclerView) findViewById(R.id.address_recycle);
        rl_bt= (RelativeLayout) findViewById(R.id.rl_bt);
        iv_fanhui= (ImageView) findViewById(R.id.iv_fanhui);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) rl_bt.getLayoutParams();
        layoutParams.topMargin=getStatusBarHeight();
        iv_fanhui.setImageResource(R.drawable.back);
        list = new ArrayList<>();
        addressPresenter=new AddressPresenter(this);
//        address_recycle.setLayoutManager(new LinearLayoutManager(this));
//        address_recycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        address_recycle.setNestedScrollingEnabled(false);
//        address_recycle.setAdapter(adapter);
        iv_fanhui.setOnClickListener(onClickListener);
        getAddressData();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_fanhui:
                    finish();
                    break;
            }
        }
    };
    private void getAddressData(){
        if(NetUtil.checkNet(AddressActivity.this)){
            addressPresenter.getDisposeData();
        }else {
            Toast.makeText(AddressActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getAUrl() {
//        return ConstantValues.URI_YONG_TEST+"area/findAllArea";
        return ConstantValues.AUTH_SITE+"interface";
    }

    @Override
    public int getACode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getABody() {
//        return "";
        HashMap<String,String> map = new HashMap<>();
        map.put("redirectUrl","area/findAllArea");
        map.put("phone", PreUtil.getInstance().getString(PreContact.username));
        map.put("imei", MACUtil.getMacAddress());
        map.put("currTime",System.currentTimeMillis()+"");
        String sign= Sign.getSign(map, getSecret());
        JSONObject jsonObject=null;
        try {
            jsonObject= JSONTools.parseMapToJson(map);
            jsonObject.put("sign",sign);
            jsonObject.put("params","");
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }
    }

    @Override
    public void getADataFailureMsg(String msg) {
        Toast.makeText(AddressActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAData(Info info) {
        try {
            List<Address> addresses = info.addresses;
            List<Address> addressmlist= new ArrayList<>();
            String string = PreUtil.getInstance().getString(ConstantValues.areaList);
            JSONArray jsonArray = null;
            jsonArray = new JSONArray(string);
            for (int i=0;i<addresses.size();i++){
                Address address = addresses.get(i);
                for(int j=0;j<jsonArray.length();j++){
                    String code=jsonArray.getString(j);
                    if(address.areaCode.equals(code)){
                        addressmlist.add(address);
                        break;
                    }
                }
            }
            list.addAll(addressmlist);
            adapter = new AddressAdapter(R.layout.item_address,list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            address_recycle.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setAutoMeasureEnabled(true);
            address_recycle.setNestedScrollingEnabled(false);
            address_recycle.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.setAction(ConstantValues.REFRESH_ADDRESS);
                    intent.putExtra("areaCode",list.get(position).areaCode);
                    intent.putExtra("areaName",list.get(position).areaName);
                    sendBroadcast(intent);
                    finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
