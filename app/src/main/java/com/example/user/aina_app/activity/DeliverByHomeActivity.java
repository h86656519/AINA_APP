package com.example.user.aina_app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.aina_app.Adapter.AddressDetailAdapter;
import com.example.user.aina_app.R;
import com.example.user.aina_app.common.MyDBHelper;

import java.util.ArrayList;

//新增/選擇地址
public class DeliverByHomeActivity extends AppCompatActivity {
    MyDBHelper dbHelper;
    ArrayList<String> deliverByHomeList = new ArrayList<String>();
    ArrayList<String> name_list = new ArrayList<String>();
    ArrayList<String> address_list = new ArrayList<String>();
    ArrayList<String> phone_list = new ArrayList<String>();
    private final String TAG = "DeliverByHomeActivity";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_by_home);
//        Intent intent =new Intent();
//        deliverByHomeList = intent.getStringArrayListExtra("totallist");
//        startActivity(intent);
//        Log.i(TAG, "City :" + deliverByHomeList.get(0));
        initView();
        updatelistview();
    }
    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.newSelectAddress_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.newSelectAddress);
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
        startActivity(new Intent(this, NewAddressActivity2.class));
    }

    //讀取全部的資料內容
    public void updatelistview() {
        try {
            SQLiteOpenHelper faqDatabaseHelper = new MyDBHelper(this);
            db = faqDatabaseHelper.getReadableDatabase();
            cursor = db.query("customer",
                    new String[]{"_id", "name", "address", "phone"}, // 抓特定欄位，null的話表示抓全部
                    null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
//                Log.i("資料表欄位總數(共有幾個欄位)", ":" + cursor.getColumnCount());
//                Log.i("資料 _id", ":" + cursor.getString(0).toString()); //對應query第二個參數的陣列
//                Log.i("資料 name", ":" + cursor.getString(1).toString());
//                Log.i("資料 address", ":" + cursor.getString(2).toString());
//                Log.i("資料 phone", ":" + cursor.getString(3).toString());
//                Log.i(TAG, "總筆數 : " + cursor.getCount());
                for (int i = 1; i <= cursor.getCount(); i++) {
                    name_list.add(cursor.getString(1).toString());
                    address_list.add(cursor.getString(2).toString());
                    phone_list.add(cursor.getString(3).toString());
                    cursor.moveToNext(); //移到下一筆資料
                }

                AddressDetailAdapter addressDetailAdapter = new AddressDetailAdapter(DeliverByHomeActivity.this, name_list, phone_list, address_list);
                RecyclerView Addrissecycleview = (RecyclerView) findViewById(R.id.address_recycle);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                Addrissecycleview.setLayoutManager(linearLayoutManager);
                Addrissecycleview.setAdapter(addressDetailAdapter);

            } else {
                Toast toast = Toast.makeText(this, "資料讀取失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();     //關閉資料庫
    }

}
