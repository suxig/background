package com.example.background.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Data {

    private String openid;
    private double jing;
    private double wei;

    public Data(String openid, double jing, double wei){
        this.openid = openid;
        this.jing = jing;
        this.wei = wei;
    }

    public String getOpenid(){return this.openid;}

    public double getJing(){return this.jing;}

    public double getWei(){return this.wei;}
}