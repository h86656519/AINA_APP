package com.example.user.aina_app.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import com.example.user.aina_app.activity.QuMediaBaseActivity2;
import com.example.user.aina_app.activity.CustomerBaseActivity;
import com.example.user.aina_app.common.QuMediaBaseActivity2;
import com.qumedia.android.core.activity.QuMediaBaseActivity;
import com.qumedia.android.core.common.QuMediaLogger;

public abstract class CustomerNfcBaseActivity extends QuMediaBaseActivity2 {
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter tag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndef.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        // Setup an intent filter for all MIME based dispatches
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mFilters = new IntentFilter[]{
                tag, ndef, tech
        };
        // Setup a tech list for all NfcF tags
        mTechLists = new String[][]{new String[]{IsoDep.class.getName(),
                NfcA.class.getName(),
                NfcB.class.getName(),
                NfcF.class.getName(),
                NfcV.class.getName(),
                Ndef.class.getName(),
                NdefFormatable.class.getName(),
                MifareClassic.class.getName(),
                MifareUltralight.class.getName()}};
    }
    protected abstract boolean resolveIntent(Intent intent);

    //當夜讀取的時候收到的在這一頁的時候
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        resolveIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
        }
    }

    @Override
    public void showProcessDialog() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public void showMessageDialog(String s) {

    }

    @Override
    public void showFinishMessageDialog(String s) {

    }

    @Override
    public void showFinishResultMessageDialog(String s, int i) {

    }

    @Override
    public void showErrorDialog(String s) {

    }

    @Override
    public void showFinishErrorDialog(String s) {

    }
}
