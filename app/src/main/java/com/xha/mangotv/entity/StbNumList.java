package com.xha.mangotv.entity;

import android.content.Intent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ysq on 2018/9/17.
 */

public class StbNumList implements Serializable{

    public String channelCode;
    public String channelName;
    public String timeRange;
    public List<String> startTimeList;
    public List<Integer> stbNumList;
    public List<String> audienceRatingList;


}
