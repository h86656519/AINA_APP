package com.example.user.aina_app.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.aina_app.Adapter.CreditCardDetailAdapter;
import com.example.user.aina_app.Adapter.ShoppingCartAdapter;
import com.example.user.aina_app.R;

import java.util.ArrayList;

public class AddSelectCreditcardActivity extends AppCompatActivity {
    private ArrayList<String> bankname_list = new ArrayList<String>();
    private ArrayList<String> creditcardnbr_list= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_select_creditcard);
        Context mContext = this;
        bankname_list.add("交通銀行");
        bankname_list.add("台新銀行");
        bankname_list.add("中信銀行");

        creditcardnbr_list.add("1234 5678 9012 3456");
        creditcardnbr_list.add("1234 5678 9012 1111");
        creditcardnbr_list.add("1234 5678 9012 2222");

        CreditCardDetailAdapter CreditCardDetailAdapter = new CreditCardDetailAdapter(mContext, bankname_list, creditcardnbr_list);
        RecyclerView CreditCardrecycleview = (RecyclerView) findViewById(R.id.credittetail_recycle);
        LinearLayoutManager shoppingCartManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        CreditCardrecycleview.setLayoutManager(shoppingCartManager);
        CreditCardrecycleview.setAdapter(CreditCardDetailAdapter);

//        ArrayAdapter spCityselectListeneradapter = new ArrayAdapter(this,R.productvetifydetail_layout.creditcardlayout,creditcardnbr_list);
//        ListView listView = (ListView)findViewById(R.id.credittetail_listview);
        initView();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.creditcard_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.creditcard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void onclick(View view) {
        startActivity(new Intent(this,AddCreditCardActivity.class));
    }
}
