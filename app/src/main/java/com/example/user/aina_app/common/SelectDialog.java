package com.example.user.aina_app.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class SelectDialog extends AlertDialog {

    protected SelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectDialog(Context context) {
        super(context);
    }

}
