package com.example.user.aina_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddCreditCardActivity extends AppCompatActivity {
    private final String TAG = "AddCreditCardActivity";
    private String[] province = new String[]{"請選擇縣市", "台北市", "新北市"};
    private String[] city = new String[]{"請選擇鄉鎮市區", "100 中正區", "103 大同區", "104 中山區", "105 松山區",
            "106 大安區", "108 萬華區", "110 信義區", "111 士林區",
            "112 北投區", "114 內湖區", "115 南港區", "116 文山區"};

    private String[][] pandc = new String[][]{
            {"請選擇鄉鎮市區"},
            {"100 中正區", "103 大同區", "104 中山區", "105 松山區", "106 大安區",
                    "108 萬華區", "110 信義區", "111 士林區", "112 北投區", "114 內湖區",
                    "115 南港區", "116 文山區"},
            {"207 萬里區", "208 金山區", "220 板橋區", "221 汐止區", "222 深坑區",
                    "223 石碇區", "224 瑞芳區", "226 平溪區", "227 雙溪區", "228 貢寮區",
                    "231 新店區", "232 坪林區", "233 烏來區", "234 永和區", "235 中和區",
                    "236 土城區", "237 三峽區", "238 樹林區", "253 石門區",}};

    private Spinner spCity;
    private Spinner spCountryside;
    private Spinner spYear;
    private Spinner spMonth;

    ArrayAdapter<String> spCityselectListeneradapter;
    ArrayAdapter<String> spCountrysideadapter;
    int pos;

    private ArrayList<String> dataYear = new ArrayList<String>();
    private ArrayList<String> dataMonth = new ArrayList<String>();
    //   private ArrayList<String> totallist = new ArrayList<String>();
    private String totallist[] = new String[7];
    private ArrayAdapter<String> adapterSpYear;
    private ArrayAdapter<String> adapterSpMonth;
    Button btfinish;
    EditText cardnbr, cardowner, street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        spYear = findViewById(R.id.add_year_spinner);
        spMonth = findViewById(R.id.add_month_spinner);
        btfinish = findViewById(R.id.finish_button);
        cardnbr = findViewById(R.id.cardnbr_editText);
        cardowner = findViewById(R.id.cardowner_editext);
        street = findViewById(R.id.street_editext);
//
//        for (int i = 0; i < 7; i++) {
//            totallist.add(i, "0");
//        }
        initView();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.addcreditcard_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.addcreditcard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //给布局对象分配id
        dataYear.add("有效年分");
// 年份设定为当年的前后20年
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 40; i++) {
            dataYear.add("" + (cal.get(Calendar.YEAR) - 20 + i));
        }
        adapterSpYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataYear);
        adapterSpYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setOnItemSelectedListener(spYearselectListener);
        spYear.setAdapter(adapterSpYear);
        // spYear.setSelection(20); //預設顯示今年


        adapterSpMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataMonth);
        adapterSpMonth.add("有效月份");
        for (int i = 1; i <= 12; i++) {
            dataMonth.add("" + (i < 10 ? "0" + i : i));
        }
        adapterSpMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(adapterSpMonth);
        spMonth.setOnItemSelectedListener(spMonthselectListener);

        spCityselectListeneradapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.layout.simple_spinner_item, province);
        spCityselectListeneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity = (Spinner) findViewById(R.id.city_spinner);
        spCity.setAdapter(spCityselectListeneradapter);
        spCity.setOnItemSelectedListener(spCityselectListener);

        spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.layout.simple_spinner_item, city);
        spCountryside = (Spinner) findViewById(R.id.countryside_spinner);
        spCountryside.setAdapter(spCountrysideadapter);
        spCountryside.setOnItemSelectedListener(spCountrysideselectListener);
    }

    private AdapterView.OnItemSelectedListener spYearselectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.i(TAG, "spYearselectListener :" + position);
            String Years = spYear.getSelectedItem().toString();
//            Log.i(TAG, "Years  :" + Years);
//            totallist.set(1, Years);
            totallist[1] = Years;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener spMonthselectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.i(TAG, "spMonthselectListener :" + position);
            String Month = spMonth.getSelectedItem().toString();
//            Log.i(TAG, "Years  :" + Month);
//            totallist.set(2, Month);
            totallist[2] = Month;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener spCityselectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
//            Log.i(TAG, "City :" + position);
            String City = spCity.getSelectedItem().toString();
//            Log.i(TAG, "City  :" + City);
            pos = spCity.getSelectedItemPosition();
            if (pos == 0) {
//                spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setEnabled(false);
                spCountrysideadapter.notifyDataSetChanged();

            } else {
                spCountryside.setEnabled(true);
                //spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setAdapter(spCountrysideadapter);
                //  totallist.set(3,String.valueOf(position));
                spCountrysideadapter.notifyDataSetChanged();
//                totallist.set(4, City);
                totallist[4] = City;
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
            totallist[5] = Countryside;
//            Log.i(TAG, "Countryside  :" + Countryside);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.finish_button:
                String nbr = cardnbr.getText().toString();
                String owner = cardowner.getText().toString();
                String road = street.getText().toString();
//                totallist.set(0, nbr);
//                totallist.set(3, owner);
//                totallist.set(6, road);
                totallist[0] = nbr;
                totallist[3] = owner;
                totallist[6] = road;

//                Log.i(TAG, "totallist  :" + totallist[0]);
//                Log.i(TAG, "totallist  :" + totallist[1]);
//                Log.i(TAG, "totallist  :" + totallist[2]);
//                Log.i(TAG, "totallist  :" + totallist[3]);
//                Log.i(TAG, "totallist  :" + totallist[4]);
//                Log.i(TAG, "totallist  :" + totallist[5]);
//                Log.i(TAG, "totallist  :" + totallist[6]);
                break;
        }
    }
}
