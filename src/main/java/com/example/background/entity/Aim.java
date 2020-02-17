package com.example.background.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Aim {

    private String go;
    private double jing;
    private double wei;
    private int xianlu;

    public Aim(String go, double jing, double wei, int xianlu){
        this.go = go;
        this.jing = jing;
        this.wei = wei;
        this.xianlu = xianlu;
    }

    public String getGo(){
        return this.go;
    }

    public double getJing(){
        return this.jing;
    }

    public double getWei(){
        return this.wei;
    }

    public int getXianlu(){
        return xianlu;
    }

}