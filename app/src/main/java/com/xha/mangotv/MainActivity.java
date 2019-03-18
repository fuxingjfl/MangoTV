package com.xha.mangotv;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xha.mangotv.Presenter.PermissionAccessPresenter;
import com.xha.mangotv.Presenter.PermissionAccessView;
import com.xha.mangotv.base.BaseActivity;
import com.xha.mangotv.base.BaseFragment;
import com.xha.mangotv.entity.Info;
import com.xha.mangotv.entity.PDssInfo;
import com.xha.mangotv.entity.PDtypeInfo;
import com.xha.mangotv.entity.PDzsInfo;
import com.xha.mangotv.entity.PermissionAccess;
import com.xha.mangotv.entity.SSlvInfo;
import com.xha.mangotv.entity.ZBjiemInfo;
import com.xha.mangotv.fragment.HistoricalViewingFragment;
import com.xha.mangotv.fragment.RealtimeViewingFragment;
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

import java.util.HashMap;
import java.util.List;


public class MainActivity extends BaseActivity implements PermissionAccessView {



    private RealtimeViewingFragment realtimeViewingFragment;
    private HistoricalViewingFragment historicalViewingFragment;
    private BaseFragment currentf;
    private RadioGroup rgp;
    private RadioButton rb_home,rb_find;
    private PermissionAccessPresenter permissionAccessPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_main);
        rgp= (RadioGroup) findViewById(R.id.rgp);
        rb_home= (RadioButton) findViewById(R.id.rb_home);
        rb_find= (RadioButton) findViewById(R.id.rb_find);

        if (savedInstanceState!=null){

            realtimeViewingFragment = (RealtimeViewingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "realtimeViewingFragment");
            historicalViewingFragment = (HistoricalViewingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "historicalViewingFragment");

        }

        if(realtimeViewingFragment==null){
            realtimeViewingFragment = new RealtimeViewingFragment();
        }
        addFragments(realtimeViewingFragment);


        permissionAccessPresenter = new PermissionAccessPresenter(this);
        getPermissionData();
        rgp.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    private void getPermissionData(){
        if(NetUtil.checkNet(MainActivity.this)){
            permissionAccessPresenter.getDisposeData();
        }else {
            Toast.makeText(MainActivity.this, "请检查当前网络是否可用！！！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (realtimeViewingFragment!=null){

            getSupportFragmentManager().putFragment(outState, "realtimeViewingFragment", realtimeViewingFragment);

        }

        if (historicalViewingFragment!=null){

            getSupportFragmentManager().putFragment(outState, "historicalViewingFragment", historicalViewingFragment);

        }


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                case R.id.rb_home:
                    if(realtimeViewingFragment==null){
                        realtimeViewingFragment= new RealtimeViewingFragment();
                    }
                    addFragments(realtimeViewingFragment);
                    break;
                case R.id.rb_find:
                    if(historicalViewingFragment==null){
                        historicalViewingFragment= new HistoricalViewingFragment();
                    }
                    addFragments(historicalViewingFragment);
                    break;
            }

        }
    };



    private void addFragments(BaseFragment f) {
        // 第一步：得到fragment管理类
        FragmentManager manager = getSupportFragmentManager();
        // 第二步：开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();

        if (currentf != null) {
            //每次把前一个fragment给隐藏了
            transaction.hide(currentf);
        }
        //isAdded:判断当前的fragment对象是否被加载过
        if (!f.isAdded()) {
            // 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
            transaction.add(R.id.fl_content, f);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commitAllowingStateLoss();
        currentf = f;
    }


    @Override
    public String getPAUrl() {
//        return ConstantValues.URI_YONG_TEST+"appSystem/getPermissionByPhoneNumber";
        return ConstantValues.AUTH_SITE+"interface";
    }

    @Override
    public int getPACode() {
        return HttpConstants.search_news01;
    }

    @Override
    public String getPABody() {

//                HashMap<String,String> map_yc = new HashMap<>();
//                map_yc.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
//                JSONObject jo = new JSONObject(map_yc);
//                return jo.toString();


        HashMap<String,String> map = new HashMap<>();
        long time=System.currentTimeMillis();
            map.put("redirectUrl","appSystem/getPermissionByPhoneNumber");
            map.put("phone", PreUtil.getInstance().getString(PreContact.username));
            map.put("imei", MACUtil.getMacAddress());
            map.put("currTime",time+"");
            String sign= Sign.getSign(map, getSecret());
            JSONObject jsonObject=null;
            try {
                jsonObject= JSONTools.parseMapToJson(map);
                jsonObject.put("redirectUrl","appSystem/getPermissionByPhoneNumber");
                jsonObject.put("phone", PreUtil.getInstance().getString(PreContact.username));
                jsonObject.put("imei", MACUtil.getMacAddress());
                jsonObject.put("currTime",time+"");
                jsonObject.put("sign",sign);
        ;       jsonObject.put("phoneNum", PreUtil.getInstance().getString(PreContact.username));
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                Log.e("TAG","发送的数据:::"+jsonObject.toString());
            return jsonObject.toString();
        }

    }

    @Override
    public void getPADataFailureMsg(String msg) {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPAData(Info info) {

        PermissionAccess permissionAccess = info.permissionAccess;

        SSlvInfo sSlvInfo = permissionAccess.sSlvInfo;

        PreUtil.getInstance().putBoolean(ConstantValues.inout,sSlvInfo.inout);
        PreUtil.getInstance().putBoolean(ConstantValues.history,sSlvInfo.history);
        PreUtil.getInstance().putBoolean(ConstantValues.realtimeLine,sSlvInfo.realtimeLine);
        PreUtil.getInstance().putBoolean(ConstantValues.selected,sSlvInfo.selected);

        List<String> indexSet= sSlvInfo.indexSet;
        JSONArray jsonArray1 = new JSONArray(indexSet);

        PreUtil.getInstance().putString(ConstantValues.indexSet,jsonArray1.toString());
        List<String> areaList= sSlvInfo.areaList;
        JSONArray jsonArray2 = new JSONArray(areaList);

        PreUtil.getInstance().putString(ConstantValues.areaList,jsonArray2.toString());
        List<String> channelGroup= sSlvInfo.channelGroup;
        JSONArray jsonArray3 = new JSONArray(channelGroup);
        PreUtil.getInstance().putString(ConstantValues.channelGroup,jsonArray3.toString());

        //频道分类份额占比
        PDtypeInfo pDtypeInfo= permissionAccess.pDtypeInfo;
        List<String> channelGroup_fezb= pDtypeInfo.channelGroup;
        JSONArray jsonArray4 = new JSONArray(channelGroup_fezb);
        PreUtil.getInstance().putString(ConstantValues.channelGroup_fezb,jsonArray4.toString());
        List<String> pDtypeInfo_areaList= pDtypeInfo.areaList;
        JSONArray pDtypeInfo_dz = new JSONArray(pDtypeInfo_areaList);
        PreUtil.getInstance().putString(ConstantValues.areaList_fezb,pDtypeInfo_dz.toString());
        PreUtil.getInstance().putBoolean(ConstantValues.selected_fezb,pDtypeInfo.selected);

        //频道收视排行
        PDssInfo pDssInfo = permissionAccess.pDssInfo;
        PreUtil.getInstance().putBoolean(ConstantValues.selected_pdssph,pDssInfo.selected);
        List<String> pdss_indexSet= pDssInfo.indexSet;
        JSONArray pdss_ja = new JSONArray(pdss_indexSet);
        PreUtil.getInstance().putString(ConstantValues.indexSet_pdssph,pdss_ja.toString());
        List<String> pdss_areaList= pDssInfo.areaList;
        Log.e("TAG","pdss_areaList==="+pdss_areaList.size());
        JSONArray pdss_dz = new JSONArray(pdss_areaList);
        PreUtil.getInstance().putString(ConstantValues.areaList_pdssph,pdss_dz.toString());
        List<String> pdss_channelGroup= pDssInfo.channelGroup;
        JSONArray pdss_pdz = new JSONArray(pdss_channelGroup);
        PreUtil.getInstance().putString(ConstantValues.channelGroup_pdssph,pdss_pdz.toString());
        Log.e("TAG","inout_pdssph=="+pDssInfo.inout);
        PreUtil.getInstance().putBoolean(ConstantValues.inout_pdssph,pDssInfo.inout);
        PreUtil.getInstance().putBoolean(ConstantValues.inoutStbNum_pdssph,pDssInfo.inoutStbNum);
        PreUtil.getInstance().putBoolean(ConstantValues.userOnline,pDssInfo.userOnline);

        //直播节目收视统计
        ZBjiemInfo zBjiemInfo = permissionAccess.zBjiemInfo;
        PreUtil.getInstance().putBoolean(ConstantValues.selected_zbtj,zBjiemInfo.selected);
        List<String> zBjiemInfo_areaList= zBjiemInfo.areaList;
        JSONArray zBjiemInfo_dz = new JSONArray(zBjiemInfo_areaList);
        PreUtil.getInstance().putString(ConstantValues.areaList_zbtj,zBjiemInfo_dz.toString());
        List<String> zBjiemInfo_channelGroup= zBjiemInfo.channelGroup;
        JSONArray zBjiemInfo_pdz = new JSONArray(zBjiemInfo_channelGroup);
        PreUtil.getInstance().putString(ConstantValues.channelGroup_zbtj,zBjiemInfo_pdz.toString());


        //频道收视走势
        PDzsInfo pDzsInfo = permissionAccess.pDzsInfo;
        List<String> zs_indexSet= pDzsInfo.indexSet;
        JSONArray jsonArray5 = new JSONArray(zs_indexSet);
        PreUtil.getInstance().putString(ConstantValues.indexSet_pdsszs,jsonArray5.toString());
        List<String> pDzsInfo_areaList= pDzsInfo.areaList;
        JSONArray pDzsInfo_ja = new JSONArray(pDzsInfo_areaList);
        PreUtil.getInstance().putString(ConstantValues.areaList_pdsszs,pDzsInfo_ja.toString());
        List<String> pDzsInfo_channelGroup= pDzsInfo.channelGroup;
        JSONArray pDzsInfo_pd = new JSONArray(pDzsInfo_channelGroup);
        PreUtil.getInstance().putString(ConstantValues.channelGroup_pdsszs,pDzsInfo_pd.toString());
        PreUtil.getInstance().putBoolean(ConstantValues.selected_pdsszs,pDzsInfo.selected);


        Intent intent = new Intent();
        intent.setAction(ConstantValues.SX_DATA);
        sendBroadcast(intent);

    }
}