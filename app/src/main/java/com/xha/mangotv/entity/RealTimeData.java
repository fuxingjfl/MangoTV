package com.xha.mangotv.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ysq on 2018/9/17.
 */

public class RealTimeData implements Serializable{

    public String channelName;

    public String channelCode;

    public String stbNum;

    public String inflowStbNum;

    public String outflowStbNum;

    public String programName;

    public String audienceRating;

    public String day;

    public String time;

    public String operator;

    public String operatorName;

    public String areaName;

    public String orderNo;

    public String marketShare;

    public String orderStatus;

    public String inRating;

    public String outRating;

    public List<InflowList> inflowLists;

    public List<OutflowList> outflowLists;

    public List<StbNumList> stbNumLists;

}
