package com.example.user.aina_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class CouponSelectionActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    ArrayList<String> couponlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_selection);
        iniView();
        couponlist.add("折價劵 1");
        couponlist.add("折價劵 2");

        radioGroup = (RadioGroup) findViewById(R.id.coupon_radiogroup);

        for (int i = 0; i < couponlist.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
//            設間距不起作用????
//            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            //设置RadioButton边距 (int left, int top, int right, int bottom)
//            lp.setMargins(20, 20, 20, 20);
            //设置RadioButton背景
            //radioButton.setBackgroundResource(R.drawable.xx);
            //设置RadioButton的样式
            radioButton.setButtonDrawable(R.drawable.radiobutton_background);
            //设置文字距离四周的距离
            radioButton.setPadding(8, 8, 0, 8);
            //设置文字
            radioButton.setText(couponlist.get(i));
            radioButton.setTextSize(16);
            final int finalI = i;
            //设置radioButton的点击事件
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CouponSelectionActivity.this, "this is radioButton  " + finalI, Toast.LENGTH_SHORT).show();
                }
            });
            //将radioButton添加到radioGroup中
            radioGroup.addView(radioButton);
        }

    }

    void iniView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.couponselection_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.couponSelection);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
