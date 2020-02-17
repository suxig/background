package com.example.background.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Map {

    private String openid;
    private double jing;
    private double wei;
    private int dir;
    private int road;

    public Map(String openid, double jing, double wei, int dir, int road){
        this.openid = openid;
        this.jing = jing;
        this.wei = wei;
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
