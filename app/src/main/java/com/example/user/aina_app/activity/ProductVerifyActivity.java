package com.example.user.aina_app.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.R;
import com.example.user.aina_app.action.HttpCallBackProcessAction;
import com.example.user.aina_app.common.VertifyDialogFragment;
import com.example.user.aina_app.common.CustomerConfigData;
import com.example.user.aina_app.common.ViartrilMobileResponseVO;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.util.StringUtils;

import java.io.Serializable;

public class ProductVerifyActivity extends CustomerNfcBaseActivity {

    private static final String TAG = ProductVerifyActivity.class.getName();
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private PendingIntent mPendingIntent;
    private NfcAdapter mAdapter;
    private TextView btnMobileMain;
    public static final String VERIFY_RESULT_BUNDLE_KEY = "VERIFY_RESULT_BUNDLE_KEY";
    /**
     * nfc 讀取到的值
     */
    private String scanValue;

    private int recordAction = -1;

    private int scanCount = 0;


    private int btnSelectedType = 1;


    public ProductVerifyActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_verify);
        QuMediaLogger.debug(TAG, ".......onCreate...ProductVerifyActivity............");
        Log.i(TAG, ".......onCreate...ProductVerifyActivity............");
        initToolbar();

        if (!resolveIntent(getIntent())) {
            // onValueScaned(scanValue);
            initValues();
        }
    }


    /**
     * 初始化 member values
     */
    public void initValues() {
        if (btnMobileMain == null) {
            onValueScaned(scanValue);
        } else {
            onValueScaned(scanValue);
        }
    }

    @Override
    public void doHttpCallBack(Serializable doAction) {
        super.doHttpCallBack(doAction);
        ViartrilMobileResponseVO reponseVO = (ViartrilMobileResponseVO) doAction;
        if (reponseVO.isSucess()) {
            VertifyDialogFragment dialogFragment = new VertifyDialogFragment();
            dialogFragment.setResult("success");
            dialogFragment.show(getSupportFragmentManager(), "成功");
            //  Toast.makeText(ProductVerifyActivity.this, "成功", Toast.LENGTH_SHORT).show();


        } else if (reponseVO.getStatusCode().equals("8055")) {
            //  goFailPage();
            // Toast.makeText(ProductVerifyActivity.this, "失敗", Toast.LENGTH_SHORT).show();

            VertifyDialogFragment dialogFragment = new VertifyDialogFragment();
            dialogFragment.setResult("fail");
            dialogFragment.show(getSupportFragmentManager(), "失敗");


        } else {
            //showErrorDialog(reponseVO.getStatusDesc());
            Toast.makeText(ProductVerifyActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 更新 Activity
     */
    public void onValueScaned(String tagName) {
        //cloasDialog();
        if (!StringUtils.isEmpty(tagName)) {
            StringBuilder requestUrl = new StringBuilder(CustomerConfigData.VIARTRIL_WEB_SERVICE_URL);
            requestUrl.append(CustomerConfigData.DO_CHECK_TAG);
            requestUrl.append("tokenKey=").append(tagName);
            startHttpRequestDiaLog(requestUrl
                            .toString(), HttpCallBackProcessAction.class, null, 0,
                    CustomerConfigData.ACTION_TAB_1
            );
        }
    }

    protected boolean resolveIntent(Intent intent) {
        scanValue = "";
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            byte[] byte_id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            String cardNumber = StringUtils.convertByteArrayToHexString(byte_id);
            scanValue = cardNumber;
            //Tag 就是讀到的 RFID Block 0 的 ID 碼
            QuMediaLogger.debug(TAG, "scanValue=[" + scanValue + "] ");
            //   Log.i("suvini", ":" + scanValue);
            initValues();
            return true;
        }
        return false;
    }


    /**
     * 設定選定的項目為以選取
     */


    @Override
    public void onResume() {
        super.onResume();
        // doSetSelectedItem();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    public void onclick(View view) {
        Intent intent = new Intent(ProductVerifyActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //  finish();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.product_verify);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        //左上返回鍵
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //左上返回鍵
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}