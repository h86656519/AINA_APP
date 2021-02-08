package com.example.user.aina_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.user.aina_app.R;

import java.util.ArrayList;

public class ProductvetifyDetailActivity extends AppCompatActivity {
    private final String TAG = "ProductvetifyDetailActivity";
    String goodNameCht, goodNameEng;
    TextView nameCht, NameEng, batchNbr, data, ean, brand, Specifi, country;
    ImageView goodpic, report, other;
    ArrayList<String> goodSpecifi_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productvetify_detail);
        goodSpecifi_list.add("醫立妥乳膏1%");
        goodSpecifi_list.add("ELIDEL CREAM 1% /TUBE(10或15公克)");
        goodSpecifi_list.add("01001");
        goodSpecifi_list.add("2018/12/19");
        goodSpecifi_list.add("4987316020723");
        goodSpecifi_list.add("佐藤");
        goodSpecifi_list.add("100T");
        goodSpecifi_list.add("日本");
        initView();
        data();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.ProductvetifyDetail_toolbar);
        Toolbar.LayoutParams tvLP = new Toolbar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setGravity(Gravity.LEFT);
        textView.setLayoutParams(tvLP);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left); //setNavigationIcon需要放在 setSupportActionBar之后才会生效
//        goodNameCht = this.getString(R.string.specialproductname_det).substring(0,7);
//        Log.i(TAG, "goodname" + goodNameCht);

        textView.setText(goodSpecifi_list.get(0));
        textView.setTextColor(this.getColor(R.color.contents_text));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        toolbar.setBackgroundColor(this.getColor(R.color.white));

        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                finish();
            }
        });
    }

    public void data() {
        nameCht = findViewById(R.id.good_chin_textview);
        NameEng = findViewById(R.id.good_eng_textview);
        batchNbr = findViewById(R.id.batchNbr_textview);
        data = findViewById(R.id.data_textview);
        ean = findViewById(R.id.ean_textview);
        brand = findViewById(R.id.brand_textview);
        Specifi = findViewById(R.id.Specifi_textview);
        country = findViewById(R.id.country_textview);

        goodpic = findViewById(R.id.goodpic_imageview);
        report = findViewById(R.id.report_imageview);
        other = findViewById(R.id.other_imageview);

        nameCht.setText(goodSpecifi_list.get(0));
//        goodNameEng = this.getString(R.string.specialproductname_det2);
//        NameEng.setText(goodNameEng.substring(9, goodNameEng.length()));
        NameEng.setText(goodSpecifi_list.get(1));
        batchNbr.setText(goodSpecifi_list.get(2));
        data.setText(goodSpecifi_list.get(3));
        ean.setText(goodSpecifi_list.get(4));
        brand.setText(goodSpecifi_list.get(5));
        Specifi.setText(goodSpecifi_list.get(6));
        country.setText(goodSpecifi_list.get(7));

        goodpic.setImageDrawable(this.getDrawable(R.drawable.product));
        report.setImageDrawable(this.getDrawable(R.drawable.report));
        other.setImageDrawable(this.getDrawable(R.drawable.certificate));

        LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvLP.setMargins(125, 20, 60, 50);
        TextView goodDetail = new TextView(this);
        goodDetail.setText(this.getString(R.string.goodDetail));
        goodDetail.setLayoutParams(tvLP);
        LinearLayout linearLayout = findViewById(R.id.goodDetail_linear);
        linearLayout.addView(goodDetail);

    }


}
