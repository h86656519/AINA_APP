package com.example.user.aina_app.activity;

import java.io.Serializable;

public class Good implements Serializable {
    private double goodPrice;
    private  int goodQuantity;
    private   boolean checkedGoodStatus;
    private   String goodName;
    private int goodImage;

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public void setGoodQuantity(int goodQuantity) {
        this.goodQuantity = goodQuantity;
    }

    public void setCheckedGoodStatus(boolean checkedGoodStatus) {
        this.checkedGoodStatus = checkedGoodStatus;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setGoodImage(int goodImage) {
        this.goodImage = goodImage;
    }


    public double getGoodPrice() {
        return goodPrice;
    }

    public int getGoodQuantity() {
        return goodQuantity;
    }

    public boolean isCheckedGoodStatus() {
        return checkedGoodStatus;
    }

    public String getGoodName() {
        return goodName;
    }

    public int getGoodImage() {
        return goodImage;
    }

}
