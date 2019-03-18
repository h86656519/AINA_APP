package com.example.user.aina_app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.OnCalendarLongClickListener;

import java.util.Calendar;

import static android.view.KeyEvent.ACTION_DOWN;

public class CalanderActivity extends AppCompatActivity implements OnCalendarLongClickListener {
    CalendarView calendarView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);
        context = this;
        initView();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.calander_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.menstruation_calander);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name，要放在 setSupportActionBar 後面
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CalanderActivity.this, "您选择的时间是：" + year + "年" + month + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
                Log.i("sunini", "year" + year + month + dayOfMonth);
                //view.setBackground(context.getDrawable(R.drawable.icon_pen));
            }
        });

        calendarView.setOnLongClickListener(new CalendarView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(CalanderActivity.this, "Long click", Toast.LENGTH_LONG).show();

                return false;
            }

        });


        calendarView.setOnTouchListener(new CalendarView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == ACTION_DOWN) {

                }
                return false;
            }
        });
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Log.i("sunini", "yearrrrrrrrrrrrrrrrrrrrr");
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Log.i("sunini", "rrr132131313r");
    }


}
