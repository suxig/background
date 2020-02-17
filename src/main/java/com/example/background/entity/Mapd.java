package com.example.background.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Mapd {

    private String openid;
    private double jing;
    private double wei;
    private int last;
    private int now;
    private int dir;
    private int road;

    public Mapd(String openid, double jing, double wei, int last, int now, int dir, int road){
        this.openid = openid;
        this.jing = jing;
        this.wei = wei;
        this.last = last;
        this.now = now;
        this.dir = dir;
        this.road = road;
    }

    public String getOpenid(){ return this.openid; }

    public double getJing(){
        return this.jing;
    }

    public double getWei(){
        return this.wei;
    }

    public int getDir(){
        return this.dir;
    }

    public int getRoad() { return this.road;}
}
