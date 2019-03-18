package com.example.user.aina_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.CustomerConfigData;
import com.example.user.aina_app.common.QuMediaAndroidUtils2;
import com.example.user.aina_app.common.QuMediaBaseActivity2;
import com.qumedia.android.core.util.QuMediaAndroidUtils;

public class MainActivity extends QuMediaBaseActivity2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showProcessDialog() {

    }


    public void onclick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.entertop:
                intent = new Intent(MainActivity.this, TopActivity.class);
                startActivity(intent);
                break;
            case R.id.vertifyproduct:
                //  Toast.makeText(MainActivity.this, "功能尚待補齊中", Toast.LENGTH_SHORT).show();
//                intent = new Intent(MainActivity.this, ProductVerifyActivity.class);
//                startActivity(intent);
                QuMediaAndroidUtils2.startActivity(MainActivity.this,
                        ProductVerifyActivity.class,
                        null,
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
 //                       Intent.FLAG_ACTIVITY_CLEAR_TOP
                        , CustomerConfigData.ACTION_TAB_1);
                break;
        }
    }


    @Override
    public void showMessageDialog(String s) {

    }

    @Override
    public void showFinishMessageDialog(String s) {

    }

    @Override
    public void showFinishResultMessageDialog(String s, int i) {

    }

    @Override
    public void showErrorDialog(String s) {

    }

    @Override
    public void showFinishErrorDialog(String s) {

    }
}
