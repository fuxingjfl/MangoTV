package com.xha.mangotv.util;

/**
 * 
 * 存储公共的URL，广播的Action
 * 
 * @author dell-dell
 *
 */

public interface ConstantValues {

	/**
	 * 服务器接口相关常量�??
	 */
	// 统一编码方式
	String ENCODING = "UTF-8";
	
	final String domain="222.74.63.136:9998";
	
	/** 测试环境下�?�用户端接口公共地址�? **/
	final String URI_YONG_TEST = "http://"+domain+"/hunanPhone/";

	final String AUTH_SITE="http://47.106.95.182:9091/AuthService/";

//	final String AUTH_SITE="http://10.200.10.34:8080/hunanPhone/";

	final String IS="is_login";
	final String IS_CS="is_cs";
	final String MAINTAINMESSAGE="maintainmessage";



	
	//用户信息
	final String USERSTATE="state";
	
	final String USERPHONE="phone";
	
	final String USERTOKEN="token";
	
	final String USERUID="id";
	final String USER_IMG="img";
	final String USER_MID="usermid";


	final String inout="inout";
	final String history="history";
	final String realtimeLine="realtimeLine";
	final String selected="selected";
	final String indexSet="indexSet";
	final String areaList="areaList";
	final String channelGroup="channelGroup";



	final String channelGroup_fezb="channelGroup_fezb";
	final String areaList_fezb="areaList_fezb";
	final String selected_fezb="selected_fezb";


	final String indexSet_pdssph="indexSet_pdssph";
	final String areaList_pdssph="areaList_pdssph";
	final String inoutStbNum_pdssph="inoutStbNum_pdssph";
	final String inout_pdssph="inout_pdssph";
	final String channelGroup_pdssph="channelGroup_pdssph";
	final String selected_pdssph="selected_pdssph";
	final String userOnline = "userOnline";

	final String areaList_zbtj="areaList_zbtj";
	final String channelGroup_zbtj="channelGroup_zbtj";
	final String selected_zbtj="selected_zbtj";


	final String selected_pdsszs="selected_pdsszs";
	final String indexSet_pdsszs="indexSet_pdsszs";
	final String areaList_pdsszs="areaList_pdsszs";
	final String channelGroup_pdsszs="channelGroup_pdsszs";

	final String MSG_NAME="处理异常";


	//广播
	final String REFRESH_ADDRESS="Refresh_address";

	final String ADD_PD="add_channel";

	final String SX_DATA="sx_data";//首页实时数据是否显示

	final String SX_INOUT="sx_inout";//流入流出的数据更新
}
