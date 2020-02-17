package com.example.background.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Luwang {

    /**
     * no : 1
     * jing : 112.922076
     * wei : 27.89692796
     */

    private int no;
    private double jing;
    private double wei;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public double getJing() {
        return jing;
    }

    public void setJing(double jing) {
        this.jing = jing;
    }

    public double getWei() {
        return wei;
    }

    public void setWei(double wei) {
        this.wei = wei;
    }
}
