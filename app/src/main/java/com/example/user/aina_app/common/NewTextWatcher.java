package com.example.user.aina_app.common;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTextWatcher implements TextWatcher {
    private final String TAG = "DeliverByMarketActivity";
    private EditText editText;

    public NewTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//        int selectionStart = editText.getSelectionStart();
//        int selectionEnd = editText.getSelectionEnd();
//        Log.i(TAG, "输入文字后的状态 :" + s);
//        textnbr = 20 - s.length();
//        HasDigit(String.valueOf(s));
//        isContainChinese(String.valueOf(s));
//        recipientnbr.setText(String.valueOf(textnbr) + "/20");
//
////                超過20個字就不讓輸入
//        if (textnbr < 0) {
//            s.delete(selectionStart - 1, selectionEnd);
//            int tempSelection = selectionEnd;
//            recipient.setText(s);
//            recipient.setSelection(editable.length()); //設定游標在最後
//            warning1.setText("數字最多20個字喔~");
//        }

    }
}