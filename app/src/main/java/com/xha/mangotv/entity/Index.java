package com.xha.mangotv.entity;

/**
 * Created by ysq on 2018/12/17.
 */

public class Index {

    public String name;
    public int score;

    public Index(String name, int score){
        super();
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
