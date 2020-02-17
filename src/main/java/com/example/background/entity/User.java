package com.example.background.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Setter
@Getter

public class User {

    private String openid;
    private int passenger;
    //private double jing;
    //private double wei;
    //private Timestamp time;

    public User(String openid,int passenger){
        this.openid = openid;
        this.passenger = passenger;
        //this.jing = jing;
        //this.wei = wei;
        //this.time = time;
    }

    public int getPassenger(){return this.passenger;}
}