/*
 * ===========================================================================
 * QuMedia Confidential
 *
 * (C) Copyright QuMedia Corp. 2010
 * ===========================================================================
 */

/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 *
 *
 * (C) Copyright IBM Corp. 2008.
 *
 * ===========================================================================
 */
package com.example.user.aina_app.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.user.aina_app.R;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.util.AndroidContextUtils;


/**
 * <p>CustomerAndroidUtils
 * <p/>
 * </p>
 *
 * @author Brian Liao
 * @version 下午 11:06:49 2010/1/16
 * @see
 */
public class CustomerAndroidUtils extends QuMediaAndroidUtils2 {

    private static final String TAG = CustomerAndroidUtils.class.getName();

    /**
     * 顯示訊息
     *
     * @param message
     */
    public static void showMessageDialog(Activity view, String message, boolean isFinish) {
        CustomerAndroidUtils.openAlertDialog(view,
                AndroidContextUtils.getInstance().getBundleString(R.string.business_common_message), message, isFinish,0);
    }

    /**
     * 顯示錯誤訊息
     *
     * @param message
     */
    public static void showErrorDialog(Activity view, String message, boolean isFinish) {
        CustomerAndroidUtils.openAlertDialog(view,
                AndroidContextUtils.getInstance().getBundleString(R.string.business_common_error), message, isFinish,0);
    }


    /**
     * 輸出訊息
     *
     * @param view
     * @param message
     */
    public static void openAlertDialog(final Activity view, String title, String message, final boolean isFinish, final int resultCode) {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(view);
        aBuilder.setTitle(title)
                .setMessage(message)
                        //正向按鈕
                .setPositiveButton(AndroidContextUtils.getInstance().getBundleString(R.string.common_button_confirm),
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                if (isFinish) {
                                    QuMediaLogger.debug(TAG, "Finish Activity");
                                    if(resultCode!=0){
                                        view.setResult(resultCode, null);
                                    }
                                    view.finish();
                                }
                            }
                        })
                .show();
    }

}
