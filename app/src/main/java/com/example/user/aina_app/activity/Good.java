package com.example.user.aina_app.activity;

public class Good {
    String goodPrice;
    int goodQuantity;
    boolean checkedGoodStatus;
    String goodName;
    int goodImage;

    public void setGoodPrice(String goodPrice) {
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


    public String getGoodPrice() {
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
