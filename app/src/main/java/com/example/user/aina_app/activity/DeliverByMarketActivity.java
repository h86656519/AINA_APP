package com.example.user.aina_app.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliverByMarketActivity extends AppCompatActivity {
    private final String TAG = "DeliverByMarketActivity";
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
    private String totallist[] = new String[8];
    int pos;
    String Countryside, City;
    EditText recipient, phone, street, email, id_rd;
    TextView buyerinfo, recipientnbr, spCitywarning, spCountrysidewarning;
    TextView warning1, warning2, warning3, warning4, warning5;
    RadioGroup markets;
    RadioButton market1, market2;
    int textnbr;
    View line1, spCityline, spCountrysideline;
    // LinearLayout line1ar;
    LinearLayout splayout, hintlayout, spWarninglayout;
    RelativeLayout relative;
    Boolean recipientVertifyresult = true,
            idVertifyresult = true,
            streetVertifyresult = true,
            phoneVertifyresult = true,
            emailVertifyresult = true,
            cityFlag = true,
            CountrysideFlag = true,
            HasDigitflag, idNullVertifyresult, hasFocus1;

    Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_by_market);
//        recipient = findViewById(R.id.recipient_editext);
//        id_rd = findViewById(R.id.id_editext);
//        phone = findViewById(R.id.phone_editext);
//        street = findViewById(R.id.street_editext);
//        email = findViewById(R.id.email_editext);
//        recipient.setHint("13");
        initView();
//        card1();
        setconpoment();
        card2();

    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.DeliverByMarket_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.delieverByMarket);
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

    public void setconpoment() {
        market1 = findViewById(R.id.market1_radiobutton);
        market2 = findViewById(R.id.market2_radiobutton);
        markets = findViewById(R.id.markets_radiogroup);
        commit = findViewById(R.id.finish_button);
        commit.setBackground(getResources().getDrawable(R.drawable.button_ovalround_disable));
        buyerinfo = new TextView(this);
        line1 = new View(this);
        recipient = new EditText(this);
        id_rd = new EditText(this);
        phone = new EditText(this);
        street = new EditText(this);
        email = new EditText(this);
        spCity = new Spinner(this, Spinner.MODE_DIALOG);
        spCountryside = new Spinner(this, Spinner.MODE_DIALOG);
        spCityline = new View(this);
        spCountrysideline = new View(this);

        warning1 = new TextView(this);
        warning2 = new TextView(this);
        warning3 = new TextView(this);
        warning4 = new TextView(this);
        warning5 = new TextView(this);
        spCitywarning = new TextView(this);
        spCountrysidewarning = new TextView(this);

        warning1.setText(R.string.warning);
        warning2.setText(R.string.warning);
        warning3.setText(R.string.warning);
        warning4.setText(R.string.warning);
        warning5.setText(R.string.warning);
        spCountrysidewarning.setText(R.string.warning);
        warning5.setText(R.string.warning);
        warning1.setTextColor(this.getColor(R.color.red));
        warning2.setTextColor(this.getColor(R.color.red));
        warning3.setTextColor(this.getColor(R.color.red));
        warning4.setTextColor(this.getColor(R.color.red));
        warning5.setTextColor(this.getColor(R.color.red));
        spCitywarning.setTextColor(this.getColor(R.color.red));
        spCountrysidewarning.setTextColor(this.getColor(R.color.red));
        recipientnbr = new TextView(this);

        spCityselectListeneradapter = new ArrayAdapter<String>(DeliverByMarketActivity.this, android.R.layout.simple_spinner_item, province);
        spCityselectListeneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spCity = (Spinner) findViewById(R.id.city_spinner);
        spCity.setAdapter(spCityselectListeneradapter);
        spCity.setOnItemSelectedListener(spCityselectListener);

        spCountrysideadapter = new ArrayAdapter<String>(DeliverByMarketActivity.this, android.R.layout.simple_spinner_item, city);
//        spCountryside = (Spinner) findViewById(R.id.countryside_spinner);
        spCountryside.setAdapter(spCountrysideadapter);
        spCountryside.setOnItemSelectedListener(spCountrysideselectListener);

        //暫時不作次功能
        markets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.market1_radiobutton:
                        //暫時不作次功能
                        break;

                    case R.id.market2_radiobutton:
                        //暫時不作次功能
                        break;
                }
            }
        });

    }

    private AdapterView.OnItemSelectedListener spCityselectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
//            Log.i(TAG, "City :" + position);
            City = spCity.getSelectedItem().toString();
//            Log.i(TAG, "City  :" + City);
            pos = spCity.getSelectedItemPosition();
            if (pos == 0) {
//                spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(DeliverByMarketActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setEnabled(false);
                spCountrysideadapter.notifyDataSetChanged();
                Log.i(TAG, "spCityselectListener 1");
//                if (City.equals("請選擇縣市")|| cityFlag == true) {
//                    Log.i(TAG, "spCityselectListener 初始");
//                    cityFlag = false;
//                }
            } else {
                spCountryside.setEnabled(true);
                //spCountrysideadapter = new ArrayAdapter<String>(AddCreditCardActivity.this, android.R.productvetifydetail_layout.simple_spinner_dropdown_item, pandc[pos]);
                spCountrysideadapter = new ArrayAdapter<String>(DeliverByMarketActivity.this, android.R.layout.simple_spinner_item, pandc[pos]);
                spCountrysideadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountryside.setAdapter(spCountrysideadapter);
                //  totallist.set(3,String.valueOf(position));
                spCountrysideadapter.notifyDataSetChanged();
//                totallist.set(4, City);
                totallist[5] = City;


            }
            if (position > 0) {
                cityFlag = true;
            }
            spinnerVertify();
        }

        public void onNothingSelected(AdapterView arg0) {
//           spCityselectListeneradapter 為空的時候才會用到這方法
        }
    };

    private AdapterView.OnItemSelectedListener spCountrysideselectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Countryside = spCountryside.getSelectedItem().toString();
//            totallist.set(5, Countryside);
            totallist[6] = Countryside;
//            Log.i(TAG, "Countryside  :" + Countryside);
            if (position > 0) {
                Log.i(TAG, "run123");
                CountrysideFlag = false;
            }
            spinnerVertify();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void onclick(View view) {
        String name = recipient.getText().toString();
        String id = id_rd.getText().toString();
        String nbr = phone.getText().toString();
        String road = street.getText().toString();
        String mail = email.getText().toString();

        Log.i(TAG, "name : " + name);
        Log.i(TAG, "id : " + id);
        Log.i(TAG, "nbr : " + nbr);
        Log.i(TAG, "road : " + road);
        Log.i(TAG, "mail : " + mail);
        Log.i(TAG, "Countryside : " + Countryside);
        Log.i(TAG, "City : " + City);

        Log.i(TAG, "warning1.isShown() : " + warning1.isShown());

        switch (view.getId()) {
            case R.id.finish_button:
                spinnerVertify();
                if (City.equals("請選擇縣市")  ) {
                    spCitywarning.setVisibility(View.VISIBLE);
                    Log.i(TAG, "沒寫完整");
                }else if(Countryside.equals("請選擇鄉鎮市區")){
                    spCountrysidewarning.setVisibility(View.VISIBLE);
                }
                else  {
                    spCitywarning.setVisibility(View.INVISIBLE);
                    spCountrysidewarning.setVisibility(View.INVISIBLE);
                    if (!warning1.isShown() || !warning2.isShown() || !warning3.isShown() || !warning4.isShown() || !warning5.isShown() || cityFlag) {
//                        Log.i(TAG, "填寫完整");
//                    totallist[0] = radiobutton; 還剩radiobutton的選項要放入???
                        totallist[1] = name;
                        totallist[2] = id;
                        totallist[3] = nbr;
                        totallist[6] = road;
                        totallist[7] = mail;
                    }
                }
        }
    }


    public void refresh() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                // upadte textView here
                recipient.setHintTextColor(Color.RED);
                recipient.setHint("必填欄位");
                handler.postDelayed(this, 500); // set time here to refresh textView
            }
        });
    }

    public void card1() {
        //LinearLayout line1ar = (LinearLayout) findViewById(R.id.buyer_line1ar);

//        buyer = new TextView(this);
//        buyer.setText(R.string.buyer);
//        LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        tvLP.setMargins(15, 30, 0, 5);
//        buyer.setTextSize(16);
//        buyer.setLayoutParams(tvLP);
//        line1ar.addView(buyer);
//
//        line1 = new View(this);
//        LinearLayout.LayoutParams line1LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
//        line1LP.setMargins(0, 10, 0, 10);
//        line1.setLayoutParams(line1LP);
//        line1.setBackgroundColor(getResources().getColor(R.color.contents_text));
//        line1ar.addView(line1);
//
//        recipient = new EditText(this);
//        id_rd = new EditText(this);
//        phone = new EditText(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(20, 0, 10, 5);
//        recipient.setHint(this.getString(R.string.buyerName));
//        recipient.setSingleLine();
//        phone.setHint(this.getString(R.string.buyerPhonenumber));
//        phone.setSingleLine();
//        id_rd.setHint(this.getString(R.string.buyerID));
//        id_rd.setSingleLine();
//        recipient.setLayoutParams(lp);
//        phone.setLayoutParams(lp);
//        id_rd.setLayoutParams(lp);
//        line1ar.addView(recipient);
//        line1ar.addView(phone);
//        line1ar.addView(id_rd);
//
//        LinearLayout spline1ar = (LinearLayout) drawParent(this);
//        LinearLayout.LayoutParams spLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
//        spLP.setMargins(10, 10, 10, 0);
//
//        spLP.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        spCity = new Spinner(this, Spinner.MODE_DIALOG);
//        spCountryside = new Spinner(this, Spinner.MODE_DIALOG);
//        spCity.setLayoutParams(spLP);
//        spCountryside.setLayoutParams(spLP);
//
//        spCity.setBackground(this.getDrawable(R.drawable.spinner_raow_selector));
//        spCountryside.setBackground(this.getDrawable(R.drawable.spinner_raow_selector));
//        spline1ar.addView(spCity);
//        spline1ar.addView(spCountryside);
//        line1ar.addView(spline1ar);
//
//        street = new EditText(this);
//        email = new EditText(this);
//        street.setHint(this.getString(R.string.tripUniformInvoice_hint4));
//        street.setSingleLine();
//        email.setHint(this.getString(R.string.buyerEmail));
//        email.setSingleLine();
//        street.setLayoutParams(lp);
//        email.setLayoutParams(lp);
//        line1ar.addView(street);
//        line1ar.addView(email);
    }

    public void card2() {
        relative = (RelativeLayout) findViewById(R.id.buyer_relative);
//        購買者資料
        buyerinfo.setText(R.string.buyer);
        //  buyer.setId(R.id.TextView_1); 另一種作法，要先在資源當先建好id 名稱
        buyerinfo.setId(View.generateViewId());
//        LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams tvLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvLP.setMargins(15, 30, 0, 5);
        buyerinfo.setTextSize(16);
        buyerinfo.setLayoutParams(tvLP);
        relative.addView(buyerinfo);

//      分割線
//        LinearLayout.LayoutParams line1LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        RelativeLayout.LayoutParams line1LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 2);
        line1LP.setMargins(0, 0, 0, 10);
//        line1LP.addRule(RelativeLayout.BELOW, R.id.TextView_1); 另一種寫法1
        line1LP.addRule(RelativeLayout.BELOW, buyerinfo.getId());
        line1.setLayoutParams(line1LP);
        //   line1.setId(R.id.line_1); 另一種寫法1
        line1.setId(View.generateViewId());
        line1.setBackgroundColor(getResources().getColor(R.color.contents_text));
        relative.addView(line1);

//      收件人
        RelativeLayout.LayoutParams recipientLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //recipientLP.addRule(RelativeLayout.BELOW, R.id.line1_1);
        recipientLP.addRule(RelativeLayout.BELOW, line1.getId());
        recipientLP.setMargins(65, 15, 50, 0);
        recipient.setSingleLine();
        recipient.setBackground(this.getDrawable(R.drawable.bg_edittext));
        recipient.setId(View.generateViewId());
        recipient.setHint(this.getString(R.string.buyerName));
        recipient.setLayoutParams(recipientLP);
        recipient.addTextChangedListener(new NewTextWatcher(recipient, "recipient"));
        recipient.setOnFocusChangeListener(new newOnFocusChangeListener(recipient, "recipient"));
        relative.addView(recipient);

//        line2 = new View(this);
//        RelativeLayout.LayoutParams line2LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
//        line2LP.setMargins(50, 0, 0, 0);
//        line2LP.addRule(RelativeLayout.BELOW, recipient.getId());
//        line2.setLayoutParams(line2LP);
//        line2.setId(View.generateViewId());
//        line2.setBackgroundColor(getResources().getColor(R.color.contents_text));
//        relative.addView(line2);

//        警告提示1，收件人字數顯示

        LinearLayout hintline1ar = (LinearLayout) hintline1(this);
        LinearLayout.LayoutParams hintLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
        hintLP.setMargins(65, 0, 0, 0);
        recipientnbr.setText(String.valueOf(20) + "/20");
        recipientnbr.setInputType(InputType.TYPE_CLASS_NUMBER);
        recipientnbr.setLayoutParams(hintLP);
        recipientnbr.setGravity(Gravity.RIGHT);
        hintline1ar.addView(warning1);
        hintline1ar.addView(recipientnbr);
        warning1.setLayoutParams(hintLP);
        warning1.setTextSize(12);
        warning1.setId(View.generateViewId());
        warning1.setVisibility(View.INVISIBLE);
        relative.addView(hintline1ar);

//      身分證
        RelativeLayout.LayoutParams id_rdLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //   id_rdLP.addRule(RelativeLayout.BELOW,R.id.EditText_1);
        id_rdLP.addRule(RelativeLayout.BELOW, hintlayout.getId());
        id_rdLP.setMargins(65, 10, 50, 0);
        //  id_rd.setId(R.id.EditText_2);
        id_rd.setId(View.generateViewId());
        id_rd.setLayoutParams(id_rdLP);
        id_rd.setBackground(this.getDrawable(R.drawable.bg_edittext));
        id_rd.addTextChangedListener(new NewTextWatcher(id_rd, "id_rd"));
        id_rd.setOnFocusChangeListener(new newOnFocusChangeListener(id_rd, "id_rd"));
        id_rd.setHint(this.getString(R.string.buyerID));
        id_rd.setSingleLine();
        relative.addView(id_rd);

        RelativeLayout.LayoutParams warning2LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        warning2LP.addRule(RelativeLayout.BELOW, id_rd.getId());
        warning2LP.setMargins(65, 0, 5, 0);
        warning2.setLayoutParams(warning2LP);
        warning2.setId(View.generateViewId());
        warning2.setTextSize(12);
        warning2.setVisibility(View.INVISIBLE);
        relative.addView(warning2);

//      電話號碼
        RelativeLayout.LayoutParams phonelp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        phonelp.setMargins(65, 0, 50, 0);
        phonelp.addRule(RelativeLayout.BELOW, warning2.getId());
        phone.setId(View.generateViewId());
        phone.setHint(this.getString(R.string.buyerPhonenumber));
        phone.setSingleLine();
        phone.setBackground(this.getDrawable(R.drawable.bg_edittext));
        phone.setLayoutParams(phonelp);
        phone.addTextChangedListener(new NewTextWatcher(phone, "phone"));
        phone.setOnFocusChangeListener(new newOnFocusChangeListener(phone, "phone"));
        phone.addTextChangedListener(new NewTextWatcher(phone, "phone"));
        relative.addView(phone);

        RelativeLayout.LayoutParams warning3LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        warning3LP.addRule(RelativeLayout.BELOW, phone.getId());
        warning3LP.setMargins(65, 0, 50, 0);
        warning3.setLayoutParams(warning3LP);
        warning3.setId(View.generateViewId());
        warning3.setTextSize(12);
        warning3.setVisibility(View.INVISIBLE);
        relative.addView(warning3);

        LinearLayout spline1ar = (LinearLayout) drawParent(this);
//      spLP 的型態取決最近的父layout
        LinearLayout.LayoutParams spLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
        spLP.setMargins(30, 0, 50, 10);
        spLP.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        spCity.setLayoutParams(spLP);
        spCity.setBackground(this.getDrawable(R.drawable.spinner_background_));
        spline1ar.addView(spCity);

        spCountryside.setLayoutParams(spLP);
        spCountryside.setBackground(this.getDrawable(R.drawable.spinner_background_));
        spline1ar.addView(spCountryside);

        relative.addView(spline1ar);

        LinearLayout spWarningline1ar = (LinearLayout) drawParent1(this);
        LinearLayout.LayoutParams spWarningLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
        spWarningLP.setMargins(40, 0, 50, 10);
        spWarningLP.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        spCitywarning.setText("\t請選擇縣市");
        spCitywarning.setVisibility(View.INVISIBLE);
        spCountrysidewarning.setVisibility(View.INVISIBLE);
        spCountrysidewarning.setText("請選擇鄉鎮市區");
        spCitywarning.setLayoutParams(spWarningLP);
        spCountrysidewarning.setLayoutParams(spWarningLP);
        spCitywarning.setTextSize(12);
        spCountrysidewarning.setTextSize(12);
        spWarningline1ar.addView(spCitywarning);
        spWarningline1ar.addView(spCountrysidewarning);
        relative.addView(spWarningline1ar);

//      街道
        RelativeLayout.LayoutParams streetLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        streetLP.addRule(RelativeLayout.BELOW, spWarningline1ar.getId());
        streetLP.setMargins(65, 0, 50, 0);
        street.setHint(this.getString(R.string.tripUniformInvoice_hint4));
        street.setSingleLine();
        street.setOnFocusChangeListener(new newOnFocusChangeListener(street, "street"));
        street.addTextChangedListener(new NewTextWatcher(street, "street"));
        street.setBackground(this.getDrawable(R.drawable.bg_edittext));
        street.setId(View.generateViewId());
        street.setLayoutParams(streetLP);
        relative.addView(street);

        RelativeLayout.LayoutParams warning4LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        warning4LP.addRule(RelativeLayout.BELOW, street.getId());
        warning4LP.setMargins(65, 0, 50, 0);
        warning4.setLayoutParams(warning4LP);
        warning4.setId(View.generateViewId());
        warning4.setTextSize(12);
        warning4.setVisibility(View.INVISIBLE);
        relative.addView(warning4);

//      電子郵件
        RelativeLayout.LayoutParams emaillp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        emaillp.addRule(RelativeLayout.BELOW, warning4.getId());
        emaillp.setMargins(65, 0, 50, 0);
        email.setHint(this.getString(R.string.buyerEmail));
        email.setSingleLine();
        email.setBackground(this.getDrawable(R.drawable.bg_edittext));
        email.setId(View.generateViewId());
        email.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        email.addTextChangedListener(new NewTextWatcher(email, "email"));
        email.setLayoutParams(emaillp);
        email.setOnFocusChangeListener(new newOnFocusChangeListener(email, "email"));
        relative.addView(email);

        RelativeLayout.LayoutParams warning5LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        warning5LP.addRule(RelativeLayout.BELOW, email.getId());
        warning5LP.setMargins(65, 0, 50, 20);
        warning5.setLayoutParams(warning5LP);
        warning5.setId(View.generateViewId());
        warning5.setTextSize(12);
        warning5.setVisibility(View.INVISIBLE);
        relative.addView(warning5);
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.i(TAG, "hasFocus1 : " + hasFocus1);
                emailvertify(hasFocus1);
                return false;
            }
        });

    }

    public View drawParent(Context context) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 0, 0, 0);
        splayout = new LinearLayout(context);
        //  params.gravity = Gravity.CENTER_HORIZONTAL;
        splayout.setId(View.generateViewId());
        splayout.setOrientation(LinearLayout.HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, warning3.getId());
        splayout.setLayoutParams(params);
        return splayout;

    }

    public View drawParent1(Context context) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 0, 0, 0);
        params.addRule(RelativeLayout.BELOW, splayout.getId());
        spWarninglayout = new LinearLayout(context);
        //  params.gravity = Gravity.CENTER_HORIZONTAL;
        spWarninglayout.setId(View.generateViewId());
        spWarninglayout.setOrientation(LinearLayout.HORIZONTAL);
        spWarninglayout.setLayoutParams(params);
        return spWarninglayout;

    }

    //收件者的
    public View hintline1(Context context) {
        hintlayout = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, recipient.getId()); //  params.gravity = Gravity.CENTER_HORIZONTAL;
        hintlayout.setOrientation(LinearLayout.HORIZONTAL);
        hintlayout.setId(View.generateViewId());
        layoutParams.setMargins(0, 0, 45, 0);
        hintlayout.setLayoutParams(layoutParams);
        return hintlayout;
    }

    //   只能输入中文或者是英文或者是中英混合
    InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//            try {
//                //转换成中文字符集的长度
//                int destLen = dest.toString().getBytes("GB18030").length;
//                int sourceLen = source.toString().getBytes("GB18030").length;
//                Log.i("tag", "String.valueOf(destLen + sourceLen)=" + String.valueOf(destLen + sourceLen));
//                //如果超过100个字符
//                if (destLen + sourceLen > 10) {
//                    Toast.makeText(DeliverByMarketActivity.this, "最多可以输入50个汉字字符或者100个英文字母", Toast.LENGTH_SHORT).show();
//                    return "";
//                }
//                //如果按返回键
//                if (source.length() < 1 && (dend - dstart >= 1)) {
//                    return dest.subSequence(dstart, dend - 1);
//                }
//                //其他情况直接返回输入的内容
//                return source;
//            } catch (UnsupportedEncodingException e)
//                e.printStackTrace();
//            }
            return "";
        }
    };

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

    //判断String中是否包含中文
    public boolean isContainChinese(String str) {
        boolean flag = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            Log.i(TAG, "有中文");
            flag = true;
        }
        return flag;
    }

    public class NewTextWatcher implements TextWatcher {
        private EditText editText;
        private String editTextID;
        //Editable editable = recipient.getText();

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
                case "recipient":
                    int selectionStart = editText.getSelectionStart();
                    int selectionEnd = editText.getSelectionEnd();
                    textnbr = 20 - s.length();
                    recipientnbr.setText(String.valueOf(textnbr) + "/20");
                    HasDigitflag = HasDigit(String.valueOf(s));
//                    Log.i(TAG, "HasDigitflag : " + HasDigitflag);
                    if (HasDigitflag == false) {
                        warning1.setText(R.string.warning);
                    } else {
                        warning1.setText("請以國字數字替代數字");
                    }
//                   超過20個字就不讓輸入
                    if (textnbr < 0) {
                        s.delete(selectionStart - 1, selectionEnd);
                        recipient.setText(s);
                        recipient.setSelection(s.length()); //設定游標在最後
                        warning1.setText("數字最多20個字喔~");
                    }
                    recipientVertifyresult = nullVertify(s); //不能為空
                    break;

                case "phone":
                    //不能為空
                    phoneVertifyresult = nullVertify(s);
                    break;

                case "id_rd":
                    idVertifyresult = idVertify(s);
                    idNullVertifyresult = nullVertify(s); //不能為空

                    break;

                case "email":
                    emailVertifyresult = nullVertify(s);
                    break;

                case "street":
                    streetVertifyresult = nullVertify(s); //不能為空
                    break;
            }
        }

        public boolean isContainChinese(String str) {
            boolean flag = false;
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                Log.i(TAG, "有中文");
                flag = true;

            }
            return flag;
        }

        // 判断一个字符串是否都为数字
        public boolean isDigit(String strNum) {
            Pattern pattern = Pattern.compile("[0-9]{1,}");
            Matcher matcher = pattern.matcher((CharSequence) strNum);
            return matcher.matches();
        }
    }

    private boolean isVaildEmailFormat() {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    public class newOnFocusChangeListener implements OnFocusChangeListener {
        private EditText editText;
        private String editTextID;

        public newOnFocusChangeListener(EditText editText, String editTextID) {
            this.editText = editText;
            this.editTextID = editTextID;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (editTextID) {
                case "recipient":
//                    Log.i(TAG, "recipientVertifyresult : " + recipientVertifyresult);
//                    Log.i(TAG, "warning1.isShown()  : " + warning1.isShown());
//                    Log.i(TAG, "HasDigitflag  : " + HasDigitflag);
                    if (recipientVertifyresult == true) {
                        if (warning1.isShown() == false) {
                            warning1.setVisibility(View.VISIBLE);
                        }
                        warning1.setText("請誤留白");
                    } else {
                        if (HasDigitflag != null) {
                            if (HasDigitflag == false) {
                                warning1.setVisibility(View.INVISIBLE);
                            } else {
                                warning1.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    break;

                case "id_rd":
//                    Log.i(TAG, "idVertifyresult : " + idVertifyresult);
//                    Log.i(TAG, "idNullVertifyresult : " + idNullVertifyresult);
//                    Log.i(TAG, "warning2.isShown() : " + warning2.isShown());
                    if (idVertifyresult == false) {
                        if (idNullVertifyresult == true) {
                            warning2.setVisibility(View.VISIBLE);
                            warning2.setText("請誤留白 1");
                        } else {
                            warning2.setVisibility(View.VISIBLE);
                            warning2.setText("無效的身分證號2，請再確認");
                        }
                    } else {
                        //消除一開始設的
                        warning2.setVisibility(View.INVISIBLE);
                    }
                    break;

                case "phone":
                    Log.i(TAG, "phoneVertifyresult : " + phoneVertifyresult);
                    if (phoneVertifyresult == false) {
                        warning3.setVisibility(View.INVISIBLE);
                    } else {
                        warning3.setVisibility(View.VISIBLE);
                        warning3.setText("請誤留白");
                    }
                    break;

                case "street":
//                    Log.i(TAG, "streetVertifyresult : " + streetVertifyresult);
                    if (streetVertifyresult == false) {
                        warning4.setVisibility(View.INVISIBLE);
                    } else {
                        warning4.setVisibility(View.VISIBLE);
                        warning4.setText("請誤留白");
                    }
                    break;

                case "email":
                    emailvertify(hasFocus);
                    hasFocus1 = hasFocus;
                    break;
            }

            if (warning1.isShown() || warning2.isShown() || warning3.isShown() || warning4.isShown() || warning5.isShown() || Countryside == "請選擇鄉鎮市區" || City == "請選擇縣市") {
//                Log.i(TAG, "沒寫完整");
                commit.setBackground(getResources().getDrawable(R.drawable.button_ovalround_disable));
            } else {
//                Log.i(TAG, "填寫完整");
                commit.setBackground(getResources().getDrawable(R.drawable.button_ovalround_enable));
            }
        }
    }

    public void emailvertify(boolean hasFocus) {
        //先判格式再判空
        Boolean emailflag = isVaildEmailFormat();
//                        Log.i(TAG, "emailflag : " + emailflag);
//                        Log.i(TAG, "warning5 : " + warning5.isShown());
//                        Log.i(TAG, "emailVertifyresult : " + emailVertifyresult);
        if (emailflag == false) {
            if (warning5.isShown() == false) {
//                                line1ar.addView(warning5);
//                                relative.addView(warning5);
                warning5.setVisibility(View.VISIBLE);
            }
            warning5.setText("mail 格式錯誤");
            if (emailVertifyresult == true) {
                warning5.setVisibility(View.VISIBLE);
                warning5.setText("請誤留白");
            }
        } else {
            //     warning5.setText("mail 格式正確");
//                            line1ar.removeView(warning5);
//                            relative.removeView(warning5);
//                            warning5.setVisibility(View.INVISIBLE);
            if (emailVertifyresult == false) {
                warning5.setVisibility(View.INVISIBLE);
            } else {
                warning5.setVisibility(View.VISIBLE);
                warning5.setText("請誤留白");
            }
        }
    }

    public Boolean idVertify(Editable s) {
        idVertifyresult = false;
        String v[] = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",
                "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "W", "Z", "I", "O"};
        try {
            if (s.length() == 10) {
                int inte = -1;
                String s1 = String.valueOf(Character.toUpperCase(String.valueOf(s).charAt(0)));
                for (int i = 0; i < 26; i++) {
                    if (s1.compareTo(v[i]) == 0) {
                        inte = i;
                        break;
                    }
                }
                int total = 0;
                int all[] = new int[11];
                String E = String.valueOf(inte + 10);
                int E1 = Integer.parseInt(String.valueOf(E.charAt(0)));
                int E2 = Integer.parseInt(String.valueOf(E.charAt(1)));
                all[0] = E1;
                all[1] = E2;

                for (int j = 2; j <= 10; j++)
                    all[j] = Integer.parseInt(String.valueOf(String.valueOf(s).charAt(j - 1)));
                for (int k = 1; k <= 9; k++)
                    total += all[k] * (10 - k);
                total += all[0] + all[10];

                if (total % 10 == 0) {
                    idVertifyresult = true;    //驗証成功
                    Log.i(TAG, "身分證驗證結果 : " + idVertifyresult);
                } else {
                    idVertifyresult = false;    //驗証失敗
                    Log.i(TAG, "身分證驗證結果 : " + idVertifyresult);
                    //   warning2.setText("無效的身分證號，請再確認");
                }
            } else {
                //   warning2.setText("無效的身分證號，請再確認");
            }
        } catch (NumberFormatException e) {
            warning2.setText("catch 無效的身分證號，請再確認");
        }
        return idVertifyresult;
    }

    public Boolean nullVertify(Editable s) {
        Boolean nullVertifyresult;
        if (s.length() > 0) {
            nullVertifyresult = false; //不為空
        } else {
            nullVertifyresult = true; //為空
        }
        return nullVertifyresult;
    }

    public void spinnerVertify() {
        Log.i(TAG, "cityFlag : " + cityFlag);
        if (City.equals("請選擇縣市") && cityFlag == false) {
            spCitywarning.setVisibility(View.VISIBLE);
        } else {
            spCitywarning.setVisibility(View.INVISIBLE);
        }
        if (Countryside != null) {
            Log.i(TAG, "CountrysideFlag : " + CountrysideFlag);
            if (Countryside.equals("請選擇鄉鎮市區") && CountrysideFlag == false) {
                spCountrysidewarning.setVisibility(View.VISIBLE);
            } else {
                spCountrysidewarning.setVisibility(View.INVISIBLE);
            }
        }

    }
}