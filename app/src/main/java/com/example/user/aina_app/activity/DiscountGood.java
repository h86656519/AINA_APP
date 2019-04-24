package com.example.user.aina_app.activity;

public  class DiscountGood extends Good {

    @Override
    public double getGoodPrice() {
        return super.getGoodPrice() * 0.9;
    }

}
