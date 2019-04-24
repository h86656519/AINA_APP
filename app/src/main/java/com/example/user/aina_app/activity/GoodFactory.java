package com.example.user.aina_app.activity;

import android.content.res.Resources;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class GoodFactory {

    public  ArrayList<Good> createdGood(Resources resources) {
        ArrayList<Good> goods = new ArrayList<Good>();
        for (int i = 0; i < 6; i++) {
            Good good = new Good();
            if (i == 3) {
                good = new DiscountGood();
            }
//            else if(i == 2){
//                good = new FreeGood();
//            }
            good.setGoodImage(R.drawable.product);
            good.setGoodName(resources.getString(R.string.specialproductname_det));
            good.setGoodPrice(1000 + i * 100);
            good.setGoodQuantity(1);
            good.setCheckedGoodStatus(true);
            goods.add(good);
        }
        return goods;
    }
}
