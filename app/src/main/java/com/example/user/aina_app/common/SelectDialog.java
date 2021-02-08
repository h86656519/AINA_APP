package com.example.user.aina_app.common;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class SelectDialog extends AlertDialog {

    protected SelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectDialog(Context context) {
        super(context);
    }

}
