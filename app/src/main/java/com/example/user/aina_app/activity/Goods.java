package com.example.user.aina_app.activity;

public class Goods {
    int goodPrice;
    int goodQuantity;
    boolean checkedGoodStatus;
    String goodName;
    int goodImage;

    public void setGoodPrice(int goodPrice) {
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



    public int getGoodPrice() {
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
