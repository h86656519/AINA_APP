package com.example.user.aina_app.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.MyDBHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAddressActivity extends AppCompatActivity {
    private final String TAG = "NewAddressActivity";
    private String[] province = new String[]{"請選擇縣市", "台北市", "新北市"};
    private String[] city = new String[]{"請選擇鄉鎮市區", "100 中正區", "103 大同區", "104 中山區", "105 松山區",
            "106 大安區", "108 萬華區", "110 信義區", "111 士林區",
            "112 北投區", "114 內湖區", "115 南港區", "116 文山區"};

    private String[][] pandc = new String[][]{
            {"請選擇鄉鎮市區"},
            {"請選擇鄉鎮市區", "100 中正區", "103 大同區", "104 中山區", "105 松山區", "106 大安區",
                    "108 萬華區", "110 信義區", "111 士林區", "112 北投區", "114 內湖區",
                    "115 南港區", "116 文山區"},
            {"請選擇鄉鎮市區", "207 萬里區", "208 金山區", "220 板橋區", "221 汐止區", "222 深坑區",
                    "223 石碇區", "224 瑞芳區", "226 平溪區", "227 雙溪區", "228 貢寮區",
                    "231 新店區", "232 坪林區", "233 烏來區", "234 永和區", "235 中和區",
                    "236 土城區", "237 三峽區", "238 樹林區", "253 石門區",}};

    private Spinner spCity;
    private Spinner spCountryside;
    ArrayAdapter<String> spCityselectListeneradapter;
    ArrayAdapter<String> spCountrysideadapter;
    int pos;
    //    private String totallist[] = new String[7];
    ArrayList<String> totallist = new ArrayList<String>();
    EditText receiver, phone, street;
    Boolean hasDigitflag, recipientVertifyresult = false, phoneVertifyresult = false, streetVertifyresult = false, finalcheck = false, cityresult = false, countrysideresult = false;
    private MyDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        receiver = findViewById(R.id.receiver_editext);
        phone = findViewById(R.id.phone_editext);
        street = findViewById(R.id.street_editext);
        for (int i = 0; i <= 5; i++) {
            totallist.add(i, "0");
        }
        initView();
        openDatabase();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.addAddress_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.newAddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        receiver.setHint(R.string.receiver_hint);
        phone.setHint(R.string.phone_hint);
        street.setHint(R.string.street_hint);
        receiver.addTextChangedListener(new NewTextWatcher(receiver, "receiver"));
        phone.addTextChangedListener(new NewTextWatcher(phone, "phone"));
        street.addTextChangedListener(new NewTextWatcher(street, "street"));
        receiver.setOnFocusChangeListener(new newOnFocusChangeListener(receiver, "receiver"));
        phone.setOnFocusChangeListener(new newOnFocusChangeListener(phone, "phone"));
        street.setOnFocusChangeListener(new newOnFocusChangeListener(street, "street"));

        spCityselectListeneradapter = new ArrayAdapter<String>(NewAddressActivity.this, android.R.layout.simple_spinner_item, province);
        spCityselectListeneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity = (Spinner) findViewById(R.id.city_spinner);
        spCity.setAdapter(spCityselectListeneradapter);
        spCity.setOnItemSelectedListener(spCityselectListener);

        spCountrysideadapter = new ArrayAdapter<String>(NewAddressActivity.this, android.R.layout.simple_spinner_item, city);
        spCountryside = (Spinner) findViewById(R.id.countryside_spinner);
        spCountryside.setAdapter(spCountrysideadapter);
        spCountryside.setOnItemSelectedListener(spCountrysideselectListener);
    }

    private AdapterView.OnItemSelectedListener spCityselectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
//            Log.i(TAG, "City :" + position);
            String City = spCity.getSelectedItem().toString();
//            Log.i(TAG, "City  :" + City);
            pos = spCity.getSelectedItemPosition();
            if (pos == 0) {
//                spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(NewAddressActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setEnabled(false);
                spCountrysideadapter.notifyDataSetChanged();

            } else {
                spCountryside.setEnabled(true);
                //spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(NewAddressActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setAdapter(spCountrysideadapter);
                //  totallist.set(3,String.valueOf(position));
                spCountrysideadapter.notifyDataSetChanged();
//                totallist.set(4, City);
//                totallist[4] = City;

                if (City.equals("請選擇縣市")) {
                    cityresult = false;
                    finalcheck = false;
                } else {
                    totallist.add(3, City);
                    cityresult = true;
                }
            }
        }

        public void onNothingSelected(AdapterView arg0) {
//           spCityselectListeneradapter 為空的時候才會用到這方法
        }
    };

    private AdapterView.OnItemSelectedListener spCountrysideselectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String Countryside = spCountryside.getSelectedItem().toString();
//            totallist.set(5, Countryside);
//            totallist[5] = Countryside;
            if (Countryside.equals("請選擇鄉鎮市區")) {
                finalcheck = false;
                countrysideresult = false;
            } else {
                totallist.add(4, Countryside);
                countrysideresult = true;
            }

//            Log.i(TAG, "Countryside  :" + Countryside);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public class NewTextWatcher implements TextWatcher {
        private EditText editText;
        private String editTextID;

        public NewTextWatcher(EditText editText, String editTextID) {
            this.editText = editText;
            this.editTextID = editTextID;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i(TAG, "输入文字后的状态 :" + s);
//            Log.i(TAG, "editTextID : " + editTextID);
//            Log.i(TAG, "s : " + s);
            switch (editTextID) {
                case "receiver":
                    int selectionStart = editText.getSelectionStart();
                    int selectionEnd = editText.getSelectionEnd();
                    hasDigitflag = HasDigit(String.valueOf(s));

                    if (hasDigitflag == true) {
                        receiver.setText(""); //直接擋掉數字
                        receiver.setHint("請以國字數字替代數字");
                        receiver.setHintTextColor(Color.RED);
                        recipientVertifyresult = false;
                        finalcheck = false;
                    } else {
                        recipientVertifyresult = true;
                        finalcheck = true;
//                        if (recipientVertifyresult){
//                            receiver.setHint("請以國字數字替代數字");
//                            receiver.setHintTextColor(Color.RED);
//                        }
                    }

                    break;

                case "phone":

                    break;

                case "street":

                    break;
            }
        }
    }

    boolean check(EditText ed) {
        boolean checkflag = false;
//        if (ed.getText().length() > 0) {
//            checkflag = true;
//        }
        return checkflag;
    }

    // 判斷一個字符串是否含有數字
    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
            Log.i(TAG, "有數字");
        }
        return flag;
    }

    public Boolean nullVertify(EditText editText) {
        Boolean flag = false;
        String text = editText.getText().toString();//不能為空
        if (text.length() == 0) {
            flag = false;
            editText.setHint("請誤留白");
            editText.setHintTextColor(Color.RED);
            return flag;
        } else {
            flag = true;
            return flag;
        }

    }

    public class newOnFocusChangeListener implements View.OnFocusChangeListener {
        private EditText editText;
        private String editTextID;

        public newOnFocusChangeListener(EditText editText, String editTextID) {
            this.editText = editText;
            this.editTextID = editTextID;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (editTextID) {
                case "receiver":
//                    Log.i(TAG, "hasFocus : " + hasFocus);
//                    跳出後再判斷空值否
//                    if (hasFocus == false) {
//                        String receivertext = receiver.getText().toString();//不能為空
//                        if (receivertext.length() == 0) {
//                            recipientVertifyresult = false;
//                            receiver.setHint("請誤留白");
//                            receiver.setHintTextColor(Color.RED);
//                        } else {
//                            recipientVertifyresult = true;
//
//                        }
//                    }
                    if (hasFocus == false) {
                        recipientVertifyresult = nullVertify(receiver);
                        finalcheck = recipientVertifyresult;
                    }
//                    Log.i(TAG, "onFocusChange recipientVertifyresult : " + recipientVertifyresult);
                    break;

                case "phone":
//                    這寫法也ok
//                    if (hasFocus == false) {
//                        String nbr = phone.getText().toString();//不能為空
//                        if (nbr.length() == 0) {
//                            phoneVertifyresult = false;
//                            phone.setHint("請誤留白");
//                            phone.setHintTextColor(Color.RED);
//                        } else {
//                            phoneVertifyresult = true;
//                        }
//                    }
                    if (hasFocus == false) {
                        phoneVertifyresult = nullVertify(phone);
                        finalcheck = phoneVertifyresult;
                    }
//                    Log.i(TAG, "onFocusChange phoneVertifyresult : " + phoneVertifyresult);
                    break;

                case "street":
                    if (hasFocus == false) {
                        streetVertifyresult = nullVertify(street);
                        finalcheck = streetVertifyresult;
                    }
                    break;
            }
        }
    }

    //完成
    public void onclick(View view) {
        receiver.clearFocus();
        phone.clearFocus();
        street.clearFocus();
        nullVertify(receiver);
        nullVertify(phone);
        nullVertify(street);

        Log.i(TAG, "finish finalcheck : " + finalcheck);
        Log.i(TAG, "recipientVertifyresult : " + recipientVertifyresult);
        Log.i(TAG, "streetVertifyresult : " + streetVertifyresult);
        Log.i(TAG, "phoneVertifyresult : " + phoneVertifyresult);
        Log.i(TAG, "cityresult : " + cityresult);
        Log.i(TAG, "countrysideresult : " + countrysideresult);

        if (recipientVertifyresult && streetVertifyresult && phoneVertifyresult && cityresult && countrysideresult) {
//            totallist[0] = receiver.getText().toString();
//            totallist[1] = phone.getText().toString();
//            totallist[2] = street.getText().toString();
            totallist.add(0, receiver.getText().toString());
            totallist.add(1, phone.getText().toString());
            totallist.add(2, street.getText().toString());
            Log.i(TAG, "98453416354 : " );
//            Intent intent =new Intent();
//            intent.putStringArrayListExtra("totallist",totallist);
//            intent.setClass(NewAddressActivity.this,DeliverByHomeActivity.class);
//            startActivity(intent);
            //  add();
        }

    }

    private void openDatabase() {
        dbHelper = new MyDBHelper(this);   //取得DBHelper物件
        db = dbHelper.getWritableDatabase();  //透過dbHelper取得讀取資料庫的SQLiteDatabase物件，可用在新增、修改與刪除
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // dbHelper.close();     //關閉資料庫
        // db.close();
        if (db.isOpen()) {
            db.close();
        }
    }

    private void add() {
        StringBuffer address = new StringBuffer();
        address.append(spCity.getSelectedItem().toString());
        address.append(spCountryside.getSelectedItem().toString());
        address.append(street.getText().toString());
        Log.i(TAG, "String.valueOf(address) : " + String.valueOf(address));

        try {
            ContentValues values = new ContentValues();  //建立 ContentValues 物件並呼叫 put(key,value) 儲存欲新增的資料，key 為欄位名稱  value 為對應值。
            values.put("name", receiver.getText().toString());
            values.put("address", String.valueOf(address));
            values.put("phone", phone.getText().toString());
            db.insert("customer", null, values);
            Log.i(TAG, "db2 : " + db);
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(NewAddressActivity.this, "寫入失敗", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}
