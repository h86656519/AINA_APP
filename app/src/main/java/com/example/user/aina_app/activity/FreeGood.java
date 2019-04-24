package com.example.user.aina_app.activity;

public class FreeGood extends Good {
    @Override
    public void setGoodPrice(double goodPrice) {
        super.setGoodPrice(goodPrice);
    }

    @Override
    public double getGoodPrice() {
        return super.getGoodPrice() * 0;
    }
}
