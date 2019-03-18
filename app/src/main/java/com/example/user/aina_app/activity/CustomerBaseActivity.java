/*
* ===========================================================================
* QuMedia Confidential
*
* (C) Copyright QuMedia Corp. 2010
* ===========================================================================
*/

package com.example.user.aina_app.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.CustomerAndroidUtils;
import com.example.user.aina_app.common.QuMediaBaseActivity2;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.util.AndroidContextUtils;
//import com.qumedia.android.viartril.customer.R;
//import com.qumedia.android.viartril.customer.activity.WebActivity;
//import com.qumedia.android.viartril.customer.common.graphics.QuMediaBaseActivity2;

import java.io.Serializable;
import java.util.HashMap;


/**
 * <p>CustomerBaseActivity
 * <p/>
 * </p>
 *
 * @author Brian Liao
 * @version 下午 10:06:44 2010/2/2
 * @see
 */
public class CustomerBaseActivity extends QuMediaBaseActivity2 {

    private static final String TAG = CustomerBaseActivity.class.getName();

  //  public static final int mainLayoutId = R.id.mainLinearLayout;

//    public static int titleId = R.id.mainTitle;
//
//    public static int contentId = R.id.mainContent;

    //public static final int BUTTON_BAR_BACKGROUND = Color.parseColor("#f97101");

    //public static final int HIGH_LIGHT_COLOR = Color.parseColor("#381a00");

    public static final int BUTTON_BAR_BACKGROUND = Color.parseColor("#000000");

    public static final int HIGH_LIGHT_COLOR = Color.parseColor("#000000");

    public static final int HIGH_LIGHT_TEXT_COLOR = Color.WHITE;

    //public static final int DEFAULT_COLOR = Color.TRANSPARENT;
    public static final int DEFAULT_COLOR = Color.BLACK;

    public static final int DEFAULT_TEXT_COLOR = Color.parseColor("#4c1a03");

    public static final int DEFAULT_BLUE_COLOR = Color.parseColor("#0000ff");

    /**
     * Tab 按鈕
     */
    private ImageButton tab1Btn = null;

    private ImageButton tab2Btn = null;

    private ImageButton tab3Btn = null;

    private ImageButton tab4Btn = null;

    /**
     * layoutResource
     */
    //protected int layoutResource = R.productvetifydetail_layout.main;
    protected int layoutResource = 0;

    private boolean hasListView = false;

    private boolean hasTitle = false;


    /**
     * mainLayout
     */
    protected ViewGroup mainLayout = null;

    protected CustomerBaseActivity(int layoutResourceId, boolean hasTitle, boolean hasListView) {
        super();
        this.layoutResource = layoutResourceId;
        this.hasListView = hasListView;
        this.hasTitle = hasTitle;
    }

    protected CustomerBaseActivity(int layoutResourceId, boolean hasTitle) {
        super();
        this.layoutResource = layoutResourceId;
        this.hasListView = false;
        this.hasTitle = hasTitle;
    }

    protected CustomerBaseActivity(int layoutResourceId) {
        super();
        this.layoutResource = layoutResourceId;
        this.hasListView = false;
        this.hasTitle = false;
    }

    protected CustomerBaseActivity() {
        this.hasListView = false;
        this.hasTitle = false;
    }


    /**
     * getDisplayWidth
     *
     * @return
     */
    protected int getDisplayWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * getDisplayWidth
     *
     * @return
     */
    protected int getDisplayHeight() {
//        Rect rectangle= new Rect();
//        Window window= getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight= rectangle.top;
//        int contentViewTop= window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //Log.i("*** getDisplayHeight :: ", "StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight+"rectangle=["+rectangle.height()+"] metrics.heightPixels=["+metrics.heightPixels+"]");
        return metrics.heightPixels;
    }



    /**
     * 取得ScrollView 的高度
     *
     * @return
     */
    protected int getNoTitleHeight() {
        return getDisplayHeight() - getButtonBarHeight();
    }


    protected int getButtonBarWidth() {
        return getDisplayWidth() / 4;
    }

    /**
     * 用 16:9 計算
     * @return
     */
    protected int getButtonBarHeight() {
        return getButtonBarWidth()*9/16;
    }




    /**
     * Called when the activity is first created.
     * 首次建立
     */
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //0 不使用底層

    }

    /**
     * Called when the activity is first created.
     * 首次建立
     */
    protected void onResume() {
        super.onResume();
    }



    /**
     *
     *
     * @param message
     */
    public void showMessageDialog(String message) {
        cloasDialog();
        CustomerAndroidUtils.openAlertDialog(this,
                this.getString(R.string.business_common_message), message, false,0);
    }

    /**
     *
     *
     * @param message
     */
    public void showFinishMessageDialog(String message) {
        cloasDialog();
        CustomerAndroidUtils.openAlertDialog(this,
                this.getString(R.string.business_common_message), message, true,0);
    }


    /**
     *
     *
     * @param message
     */
    public void showFinishResultMessageDialog(String message, int resultCode) {
        cloasDialog();
        CustomerAndroidUtils.openAlertDialog(this,
                this.getString(R.string.business_common_message), message, true,resultCode);
    }


    /**
     * showErrorDialog
     *
     * @param message
     */
    public void showErrorDialog(String message) {
        cloasDialog();
        CustomerAndroidUtils.openAlertDialog(this,
                this.getString(R.string.business_common_error), message, false,0);
    }

    /**
     * showFinishErrorDialog
     *
     * @param message
     */
    public void showFinishErrorDialog(String message) {
        cloasDialog();
        CustomerAndroidUtils.openAlertDialog(this,
                this.getString(R.string.business_common_error), message, true,0);
    }

    @Override
    public void showProcessDialog() {
        if (mProgress == null) {
            mProgress = new ProgressDialog(this);
            mProgress.setTitle(AndroidContextUtils.getInstance().getBundleString(R.string.business_common_dataTitle));
            mProgress.setMessage(AndroidContextUtils.getInstance().getBundleString(R.string.business_common_dataDownLoad));
            mProgress.setIndeterminate(false);
            mProgress.setMax(100);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setCancelable(false);
            mProgress.show();
        } else {
            mProgress.show();
        }
    }

    /**
     * setListeners
     */
    private void setListeners() {
//        if (tab1Btn != null) {
//            tab1Btn.setOnClickListener(onTab1Btn);
//        }

//        if (tab2Btn != null) {
//            tab2Btn.setOnClickListener(onTab2Btn);
//        }
//
//        if (tab3Btn != null) {
//            tab3Btn.setOnClickListener(onTab3Btn);
//        }

//        if (tab4Btn != null) {
//            tab4Btn.setOnClickListener(onTab4Btn);
//        }

    }


    /**
     *
     */
//    private View.OnClickListener onTab1Btn = new View.OnClickListener() {
//        public void onClick(View v) {
//            CustomerAndroidUtils.startActivity(
//                    CustomerBaseActivity.this,
//                    ProductVerifyActivity.class,
//                    null,
//                    Intent.FLAG_ACTIVITY_CLEAR_TOP,
//                    CustomerConfigData.ACTION_TAB_1);
//        }
//    };



    /**
     *
     */
//    private View.OnClickListener onTab2Btn = new View.OnClickListener() {
//        public void onClick(View v) {
//                CustomerAndroidUtils.startActivity(
//                        CustomerBaseActivity.this,
//                        ProductInfoActivity.class,
//                        null,
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP,
//                        CustomerConfigData.ACTION_TAB_2);
//        }
//    };

    /**
     *
     */
//    private View.OnClickListener onTab3Btn = new View.OnClickListener() {
//        public void onClick(View v) {
//            CustomerAndroidUtils.startActivity(
//                    CustomerBaseActivity.this,
//                    ProductUseActivity.class,
//                    null,
//                    Intent.FLAG_ACTIVITY_CLEAR_TOP,
//                    CustomerConfigData.ACTION_TAB_3);
//        }
//    };




}
