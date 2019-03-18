package com.xha.mangotv.entity;

/**
 * Created by ysq on 2018/9/17.
 */

public class Address implements Comparable<Address>{

    public String id;
    public String areaCode;
    public String areaName;
    public String parentAreaCode;
    public Object operator;
    public int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public int compareTo(Address o) {
        int i = Integer.parseInt(this.areaCode) - Integer.parseInt(o.areaCode);//先按照年龄排序
        return i;
    }
}
