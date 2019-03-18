package com.xha.mangotv.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ysq on 2018/9/13.
 */

public class Info implements Serializable{

    public String msg;

    public List<ChannelGroup> channelGroups;

    public List<Address> addresses;

    public List<RealTimeData> realTimeDatas;

    public List<DuiBiData> duiBiDatas;

    public PermissionAccess permissionAccess;

    public List<LiShiInfo> liShiInfos;

    public List<AddContrastChannel> addContrastChannels;

    public List<ContrastChannelData> contrastChannelDatas;

    public List<String> pd_code_list;

    public List<Channel> channels;

    public List<UserTrend> userTrends;

    public InflowOutflow inflowOutflow;
}
