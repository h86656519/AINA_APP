package com.example.user.aina_app.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user.aina_app.R;

public class MenstruationActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MenstruationActivity";
    TextView calender;
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menstruation);
        initView();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.menstruation_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.menstruation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name，要放在 setSupportActionBar 後面
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        calender = findViewById(R.id.calender_textview);
        calender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calender_textview:
                Log.i(TAG, "calender_textview : ");
//
                fmanager = getSupportFragmentManager();
//                ftransaction = fmanager.beginTransaction();
//                CalanderFragment  calanderFragment = new CalanderFragment();
//                ftransaction.replace(R.id.Menstruation, calanderFragment);
//                ftransaction.commit();
                startActivity(new Intent(MenstruationActivity.this, CalanderActivity.class));
                break;
        }

    }
}
