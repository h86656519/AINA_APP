package com.example.user.aina_app.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.user.aina_app.action.HttpDataHandler2;
import com.qumedia.android.core.activity.QuMediaActivity;
import com.qumedia.android.core.common.BaseMobileResponseListVO;
import com.qumedia.android.core.common.HttpDataHandler;
import com.qumedia.android.core.common.HttpListener;
import com.qumedia.android.core.common.QuMediaConfigData;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.common.QuMediaSystemStartup;
import com.qumedia.android.core.exception.MobileException;
import com.qumedia.android.core.util.QuMediaAndroidUtils;
import com.qumedia.android.core.util.ReponseMessage;
import com.qumedia.android.core.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;

//import com.example.user.aina_app.common.BaseReponseVO2;
//import com.example.user.aina_app.common.HttpConnectThread2;
//import com.example.user.aina_app.common.HttpDataHandler2;

//import com.qumedia.android.core.common.BaseReponseVO;

public abstract class QuMediaBaseActivity2 extends AppCompatActivity implements QuMediaActivity, HttpListener {
    private static final String TAG = QuMediaBaseActivity2.class.getName();
    protected Object instance = null;
    protected int addFlags = 0;
    protected String doAction = null;
    protected BaseReponseVO2 reponseVO = null;
    protected ProgressDialog mProgress = null;
    protected Handler progressHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String messageType = data.getString("HTTP_MESSAGE_TYPE");
            if ("ERROR".equals(messageType)) {
                if ("9999".equals(data.getString("REPONSE_DATA"))) {
                    if (!QuMediaBaseActivity2.this.doRetryHttpConnectUrl()) {
                        QuMediaBaseActivity2.this.onError(new MobileException(data.getString("REPONSE_DATA"), data.getString("REPONSE_MESSAGE")));
                    }
                } else {
                    QuMediaBaseActivity2.this.onError(new MobileException(data.getString("REPONSE_DATA"), data.getString("REPONSE_MESSAGE")));
                }
            } else if ("FINISH".equals(messageType)) {
             //   QuMediaBaseActivity2.this.onProgress(150);
                QuMediaBaseActivity2.this.onFinish(data.getByteArray("REPONSE_DATA"));
            } else if ("RESULT_STRING_FINISH".equals(messageType)) {
                QuMediaBaseActivity2.this.onProgress(150);
                QuMediaBaseActivity2.this.onFinish(data.getString("REPONSE_DATA"));
            } else if ("STRING_FINISH".equals(messageType)) {
                QuMediaBaseActivity2.this.doHttpCallBack(data.getString("REPONSE_DATA"));
            } else {
               // QuMediaBaseActivity2.this.onProgress(data.getInt("REPONSE_DATA"));
            }
        }
    };

    protected static final int MENU_BACK = 1;
    protected static final int MENU_SUBMIT = 2;

    public boolean doRetryHttpConnectUrl() {
        if (QuMediaConfigData.RE_TRY_COUNT >= 4) {
            return false;
        } else {
            ++QuMediaConfigData.RE_TRY_COUNT;
            HttpConnectThread2 downloader = new HttpConnectThread2(QuMediaConfigData.REQUEST_URL_CACHE, this.progressHandler);
            downloader.start();
            return true;
        }
    }

    protected QuMediaBaseActivity2() {
    }

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setup();
        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            this.doAction = bundle.getString("QU_MEDIA_ACTION");
        }

    }

    protected void setup() {
        QuMediaSystemStartup.setupSystem(this);
        if (StringUtils.isEmpty(QuMediaConfigData.CUSTOMERID)) {
            SharedPreferences settings = this.getSharedPreferences("CusetomrPREF", 0);
            DisplayMetrics metrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            QuMediaConfigData.SCREEN_HEIGHT = metrics.heightPixels;
            QuMediaConfigData.SCREEN_WIDTH = metrics.widthPixels;
            int screenHeight = QuMediaConfigData.SCREEN_HEIGHT - this.getTitleBarHeight();
            QuMediaConfigData.HEIGHT_SCALE = BigDecimal.valueOf((long)screenHeight).divide(BigDecimal.valueOf((long)QuMediaConfigData.BACKGORUND_BASE_HEIGHT), 4, 4);
            QuMediaConfigData.WIDTH_SCALE = BigDecimal.valueOf((long)QuMediaConfigData.SCREEN_WIDTH).divide(BigDecimal.valueOf((long)QuMediaConfigData.BACKGORUND_BASE_WIDTH), 4, 4);
            QuMediaConfigData.SCREEN_DEVICE_DENSITY = metrics.densityDpi;
            QuMediaConfigData.SCREEN_LAYOUT_SIZE = this.getResources().getConfiguration().screenLayout & 15;
            QuMediaConfigData.CUSTOMERID = settings.getString(QuMediaConfigData.CUSTOMER_ID_PREF, "");
            QuMediaConfigData.MACHINE_ID = settings.getString("CUSTOMER_MACHINE_PREF", "");
            QuMediaConfigData.LOGINID = settings.getString("CUSTOMER_LOGIN_PREF", "");
            QuMediaConfigData.PHONENUMBER = settings.getString("CUSTOMER_PHONENUMBER_PREF", "");
            QuMediaConfigData.CUSTOMER_NAME = settings.getString("CUSTOMER_NAME_PREF", "");
        }

    }

    protected void onResume() {
        super.onResume();
        this.setup();
    }

    public Activity getActivityInstance() {
        return this;
    }

    public void updateImage(Bitmap data, String imageKey) {
    }

    public void doHttpCallBack(Serializable doAction) {
        this.cloasDialog();
    }

    public void startHttpRequestDiaLog(String requestUrl, Class actionHandler, Object[] parameters, int addFlags, String doAction) {
        try {
//            if (!this.haveInternet()) {
//             //   Toast.makeText(this.getBaseContext(), "No network connection , please check your setup.", 0).show();
//                return;
//            }

            if (parameters == null) {
                Constructor theConstructor = actionHandler.getConstructor();
                this.instance = theConstructor.newInstance();
            } else {
                Class[] types = new Class[parameters.length];

                for(int i = 0; i < parameters.length; ++i) {
                    types[i] = parameters[i].getClass();
                }

                Constructor theConstructor = actionHandler.getConstructor(types);
                this.instance = theConstructor.newInstance(parameters);
            }
        } catch (Exception var8) {
            QuMediaLogger.error(TAG, var8);
            this.showErrorDialog(ReponseMessage.getReponseMessage("9999"));
            return;
        }

       // this.reponseVO = new BaseReponseVO(this, addFlags, doAction);
        this.reponseVO = new BaseReponseVO2(this, addFlags, doAction);
        this.showProcessDialog();
      //  HttpConnectThread downloader = new HttpConnectThread(requestUrl, this.progressHandler);
        HttpConnectThread2 downloader = new HttpConnectThread2(requestUrl, this.progressHandler);
        downloader.start();
    }

    public abstract void showProcessDialog();

    public void doHttpNextPageCallBack(BaseMobileResponseListVO newReponseVO) {
        this.cloasDialog();
    }

    public void onFinish(byte[] buffer) {
        DataInputStream input = null;

        try {
            if (buffer != null) {
                QuMediaLogger.debug(TAG, "buffer length=" + buffer.length);
                input = new DataInputStream(new ByteArrayInputStream(buffer));
                if (this.instance instanceof HttpDataHandler2) {
                   // ((HttpDataHandler)this.instance).setData(input, this.reponseVO);
                    ((HttpDataHandler2)this.instance).setData(input, this.reponseVO);
                    QuMediaLogger.debug(TAG, "close mProgress");
                } else {
                    this.showErrorDialog("[WARNING] Displayable object cannot handle data.");
                }

                return;
            }

            QuMediaLogger.debug(TAG, "buffer is null");
            if (this.instance instanceof HttpDataHandler2) {
         //       ((HttpDataHandler)this.instance).setData((Object)null, this.reponseVO);
                ((HttpDataHandler2)this.instance).setData((Object)null, this.reponseVO);
                return;
            }

            System.err.println("[WARNING] Displayable object cannot handle data.");
        } catch (MobileException var16) {
            this.onError(var16);
            return;
        } catch (Exception var17) {
            QuMediaLogger.error(TAG, var17.getMessage(), var17);
            this.onError(new MobileException("9999", var17));
            return;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception var15) {
                QuMediaLogger.error(TAG, var15.getMessage(), var15);
                this.onError(new MobileException("9999", var15));
            }

        }

    }

    public void onFinish(String result) {
        Object input = null;

        try {
            if (result != null) {
                if (this.instance instanceof HttpDataHandler2) {
              //      ((HttpDataHandler)this.instance).setData(result, this.reponseVO);
                    ((HttpDataHandler2)this.instance).setData(result, this.reponseVO);
                    QuMediaLogger.debug(TAG, "close mProgress");
                } else {
                    this.showErrorDialog("[WARNING] Displayable object cannot handle data.");
                }

                return;
            }

            QuMediaLogger.debug(TAG, "buffer is null");
            if (this.instance instanceof HttpDataHandler2) {
            //    ((HttpDataHandler)this.instance).setData((Object)null, this.reponseVO);
                ((HttpDataHandler2)this.instance).setData((Object)null, this.reponseVO);
                return;
            }

            System.err.println("[WARNING] Displayable object cannot handle data.");
        } catch (MobileException var16) {
            this.onError(var16);
            return;
        } catch (Exception var17) {
            QuMediaLogger.error(TAG, var17.getMessage(), var17);
            this.onError(new MobileException("9999", var17));
            return;
        } finally {
            try {
                if (input != null) {
                    ((DataInputStream)input).close();
                }
            } catch (Exception var15) {
                QuMediaLogger.error(TAG, var15.getMessage(), var15);
                this.onError(new MobileException("9999", var15));
            }

        }

    }

    public void onError(MobileException ex) {
        this.cloasDialog();
        QuMediaLogger.error(TAG, ex.getErrorMessage(), ex);
        if (StringUtils.isEmpty(ex.getErrorMessage())) {
            this.showErrorDialog(ReponseMessage.getReponseMessage("common_response_9999"));
        } else {
            this.showErrorDialog(ex.getErrorMessage());
        }

        this.doErrorCallBack();
    }

    public void onProgress(int percent) {
        if (percent == 100) {
            this.mProgress.setProgress(100);
            this.mProgress.setSecondaryProgress(100);
        } else if (percent > 100) {
            this.mProgress.setIndeterminate(true);
        } else {
            this.mProgress.setProgress(percent);
        }

    }

    public String getDoAction() {
        return this.doAction;
    }

    public void cloasDialog() {
        if (this.mProgress != null) {
            QuMediaLogger.debug(TAG, "close mProgress");
            this.mProgress.dismiss();
        }

    }

    protected int getTitleBarHeight() {
        return QuMediaAndroidUtils.getTitleBarHeight();
    }

    protected int getPopupActivityHeight() {
        return QuMediaConfigData.SCREEN_HEIGHT * 3 / 4;
    }

    protected int getPopupActivityWidth() {
        return QuMediaConfigData.SCREEN_WIDTH * 4 / 5;
    }

    protected void doErrorCallBack() {
    }

//    protected boolean haveInternet() {
//        boolean result = false;
//       // ConnectivityManager connManager = (ConnectivityManager)this.getSystemService("connectivity");
//        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connManager.getActiveNetworkInfo();
//        if (info != null && info.isConnected()) {
//            if (!info.isAvailable()) {
//                result = false;
//            } else {
//                result = true;
//            }
//        } else {
//            result = false;
//        }
//
//        return result;
//    }
}
