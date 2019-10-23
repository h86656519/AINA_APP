package com.example.user.aina_app.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qumedia.android.core.activity.QuMediaBaseActivity;
import com.qumedia.android.core.common.QuMediaConfigData;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.util.QuMediaAndroidUtils;
import com.qumedia.android.core.util.StringUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QuMediaAndroidUtils2 {
    private static final String TAG = QuMediaAndroidUtils.class.getName();

    public QuMediaAndroidUtils2() {
    }

    public static String getUriFromRequestUrl(String url) {
        return url != null && url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
    }

    public static List<NameValuePair> getValuesFromRequestUrl(String url) {
        List<NameValuePair> aNameValuePairList = new ArrayList();
        if (url != null && url.indexOf("?") > -1) {
            NameValuePair aNameValuePair = null;
            String paramesString;
            String[] nameValues;
            if (url.indexOf("&") > -1) {
                paramesString = url.substring(url.indexOf("?") + 1, url.length());
                nameValues = paramesString.split("&");

                for(int i = 0; i < nameValues.length; ++i) {
                    String[] pairs = nameValues[i].split("=");
                    if (pairs.length == 1) {
                        aNameValuePairList.add(new BasicNameValuePair(pairs[0], ""));
                    } else {
                        aNameValuePairList.add(new BasicNameValuePair(pairs[0], pairs[1]));
                    }
                }
            } else {
                paramesString = url.substring(url.indexOf("?") + 1, url.length());
                nameValues = paramesString.split("=");
                aNameValuePairList.add(new BasicNameValuePair(nameValues[0], nameValues[1]));
            }
        }

        return aNameValuePairList;
    }

    private static String getPaddedHex(int nr, int minLen) {
        String str = Integer.toHexString(nr);
        if (str != null) {
            while(str.length() < minLen) {
                str = "0" + str;
            }
        }

        return str;
    }

    private static String getPaddedInt(int nr, int minLen) {
        String str = Integer.toString(nr);
        if (str != null) {
            while(str.length() < minLen) {
                str = "0" + str;
            }
        }

        return str;
    }

//    public static String getCurrentCellIdInfo(Activity activity) {
//        TelephonyManager tel = (TelephonyManager)activity.getSystemService("phone");
//        StringBuilder result = new StringBuilder();
//
//        try {
//            GsmCellLocation location = (GsmCellLocation)tel.getCellLocation();
//            int cid = location.getCid();
//            int lac = location.getLac();
//            int mcc = 0;
//            int mnc = 0;
//            if (cid == -1 && lac == -1) {
//                return null;
//            } else {
//                String networkOperator = tel.getNetworkOperator();
//                if (networkOperator != null && networkOperator.length() > 0) {
//                    try {
//                        mcc = Integer.parseInt(networkOperator.substring(0, 3));
//                        mnc = Integer.parseInt(networkOperator.substring(3));
//                    } catch (NumberFormatException var10) {
//                        ;
//                    }
//                }
//
//                int cellPadding = false;
//                if (tel.getNetworkType() == 3) {
//                    cellPadding = true;
//                } else {
//                    cellPadding = true;
//                }
//
//                result.append("CellId:");
//                result.append(cid);
//                result.append(";LAC:");
//                result.append(lac);
//                result.append(";MNC:");
//                result.append(mnc);
//                result.append(";MCC:");
//                result.append(mcc);
//                QuMediaLogger.debug("getCurrentCellIdInfo", result.toString());
//                return result.toString();
//            }
//        } catch (Exception var11) {
//            return null;
//        }
//    }

    public static String plusPageNumber(String url) {
        if (url.contains("pageNumber")) {
            StringBuilder newUrl = new StringBuilder();
            int nextPoistio = url.indexOf("&", url.indexOf("pageNumber"));
            if (nextPoistio < 0) {
                nextPoistio = url.length();
            }

            int pageNumber = StringUtils.getIntValue(url.substring(url.indexOf("pageNumber") + "pageNumber".length() + 1, nextPoistio));
            ++pageNumber;
            newUrl.append(url.substring(0, url.indexOf("pageNumber") + "pageNumber".length() + 1));
            newUrl.append(pageNumber);
            newUrl.append(url.substring(nextPoistio, url.length()));
            return newUrl.toString();
        } else {
            return url;
        }
    }

    public static String minusPageNumber(String url) {
        if (url.indexOf("pageNumber") > -1) {
            StringBuilder newUrl = new StringBuilder();
            int nextPoistio = url.indexOf("&", url.indexOf("pageNumber"));
            if (nextPoistio < 0) {
                nextPoistio = url.length();
            }

            int pageNumber = StringUtils.getIntValue(StringUtils.securityDecodeUnicode(url.substring(url.indexOf("pageNumber") + "pageNumber".length() + 1, nextPoistio)));
            ++pageNumber;
            newUrl.append(url.substring(0, url.indexOf("pageNumber") + "pageNumber".length() - 1));
            newUrl.append(StringUtils.securityUnicodeEncoding(String.valueOf(pageNumber)));
            newUrl.append(url.substring(nextPoistio, url.length()));
            return newUrl.toString();
        } else {
            return url;
        }
    }

    public static String getPageNumber(String url) {
        if (url.indexOf("pageNumber") > -1) {
            new StringBuilder();
            int nextPoistio = url.indexOf("&", url.indexOf("pageNumber"));
            if (nextPoistio < 0) {
                nextPoistio = url.length();
            }

            int pageNumber = StringUtils.getIntValue(StringUtils.securityDecodeUnicode(url.substring(url.indexOf("pageNumber") + "pageNumber".length() + 1, nextPoistio)));
            return String.valueOf(pageNumber);
        } else {
            return "1";
        }
    }

    public static boolean isMachineLogin() {
        return !StringUtils.isEmpty(QuMediaConfigData.MACHINE_ID);
    }

//    public static int getLocalScreenScaleImageSize(String storePath, int direction) throws Exception {
//        int scale = 0;
//        int[] size = LocalStorageUtil.readImageSize(storePath);
//        if (size != null) {
//            int width = size[0];
//            int height = size[1];
//            int yScale = BigDecimal.valueOf((long)QuMediaConfigData.SCREEN_HEIGHT).divide(BigDecimal.valueOf((long)height), 2, 4).multiply(BigDecimal.valueOf(100L)).intValue();
//            int xRatio = BigDecimal.valueOf((long)QuMediaConfigData.SCREEN_WIDTH).divide(BigDecimal.valueOf((long)width), 2, 4).multiply(BigDecimal.valueOf(100L)).intValue();
//            if (direction == 0) {
//                if (yScale < xRatio) {
//                    scale = yScale;
//                } else {
//                    scale = xRatio;
//                }
//            } else {
//                scale = yScale;
//            }
//        }
//
//        QuMediaLogger.debug("QuMediaAndroidUtils", "getLocalScreenScaleImageSize scale=[" + scale + "]");
//        return scale;
//    }

//    public static Bitmap getDecodeScaleBitmap(String storePath) {
//        int[] size = LocalStorageUtil.readImageSize(storePath);
//        if (size == null) {
//            return null;
//        } else {
//            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
//            int yRatio = Math.round((float)(size[0] / QuMediaConfigData.SCREEN_HEIGHT));
//            int xRatio = Math.round((float)(size[1] / QuMediaConfigData.SCREEN_WIDTH));
//            if (yRatio <= 1 && xRatio <= 1) {
//                bitmapFactoryOptions.inSampleSize = 1;
//            } else if (yRatio > xRatio) {
//                bitmapFactoryOptions.inSampleSize = xRatio;
//            } else {
//                bitmapFactoryOptions.inSampleSize = yRatio;
//            }
//
//            bitmapFactoryOptions.inJustDecodeBounds = false;
//            return BitmapFactory.decodeFile(storePath, bitmapFactoryOptions);
//        }
//    }

    public static void alertShort(View view, String message) {
     //   Toast.makeText(view.getContext(), message, 0).show();
    }

    public static void alert(View view, String message) {
      //  Toast.makeText(view.getContext(), message, 1).show();
    }

    public static void alert(Activity view, String message) {
    //    Toast.makeText(view.getApplicationContext(), message, 1).show();
    }

    public static void startMainActivity(QuMediaBaseActivity oriActivity, Class toActivity, String doAction) {
       // startActivity(oriActivity, toActivity, (HashMap)null, 0, doAction);
    }

    public static void startActivityForResult(QuMediaBaseActivity oriActivity, Class toActivity, HashMap<String, Serializable> parameterMap, int addFlags, String doAction, int resultCode) {
        oriActivity.cloasDialog();
        Intent intent = new Intent();
        if (parameterMap != null || doAction != null) {
            Bundle bundle = new Bundle();
            if (parameterMap != null) {
                Iterator i$ = parameterMap.keySet().iterator();

                while(i$.hasNext()) {
                    String key = (String)i$.next();
                    bundle.putSerializable(key, (Serializable)parameterMap.get(key));
                }
            }

            bundle.putString("QU_MEDIA_ACTION", doAction);
            intent.putExtras(bundle);
        }

        intent.setClass(oriActivity, toActivity);
        intent.addFlags(addFlags);
        oriActivity.startActivityForResult(intent, resultCode);
    }

    public static void startActivity(QuMediaBaseActivity2 oriActivity, Class toActivity, HashMap<String, Serializable> parameterMap, int addFlags, String doAction) {
        oriActivity.cloasDialog();
        Intent intent = new Intent();
        if (parameterMap != null || doAction != null) {
            Bundle bundle = new Bundle();

            if (parameterMap != null) {
                Iterator i$ = parameterMap.keySet().iterator();

                while(i$.hasNext()) {
                    String key = (String)i$.next();
                    bundle.putSerializable(key, (Serializable)parameterMap.get(key));
                }
            }

            bundle.putString("QU_MEDIA_ACTION", doAction);
            intent.putExtras(bundle);
        }

        intent.setClass(oriActivity, toActivity);
        if (addFlags != 0) {
            intent.addFlags(addFlags);
        }

        oriActivity.startActivity(intent);
    }
    public static void startActivity2(QuMediaBaseActivity2 oriActivity, HashMap<String, Serializable> parameterMap, int addFlags, String doAction) {
        oriActivity.cloasDialog();
        Intent intent = new Intent();
        if (parameterMap != null || doAction != null) {
            Bundle bundle = new Bundle();

            if (parameterMap != null) {
                Log.i("suvini","startActivity541223 : " + Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Iterator i$ = parameterMap.keySet().iterator();

                while(i$.hasNext()) {
                    String key = (String)i$.next();
                    bundle.putSerializable(key, (Serializable)parameterMap.get(key));
                }
            }

            bundle.putString("QU_MEDIA_ACTION", doAction);
            intent.putExtras(bundle);
        }

    }

//    public static void startReplaceTopActivity(QuMediaBaseActivity oriActivity, Class toActivity, HashMap<String, Serializable> parameterMap, String doAction) {
//        startActivity(oriActivity, toActivity, parameterMap, 67108864, doAction);
//    }

    public static void startWebViewLoad(WebView wv, final Activity activity, String requestUrl, String loadingTitle, String loadMessage, final String errorTitle) {
        final ProgressDialog webProgress = new ProgressDialog(activity);
        webProgress.setTitle(loadingTitle);
        webProgress.setMessage(loadMessage);
        webProgress.setIndeterminate(false);
        webProgress.setMax(100);
        webProgress.setProgressStyle(1);
        webProgress.setCancelable(true);

        try {
            if (!webProgress.isShowing()) {
                webProgress.show();
            }
        } catch (Throwable var16) {
            QuMediaLogger.debug(TAG, var16.getMessage());
        }

        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
   //     wv.getSettings().setCacheMode(2);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setScrollbarFadingEnabled(true);
      //  wv.setScrollBarStyle(0);
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                webProgress.setProgress(progress);
                if (progress >= 100 && webProgress.isShowing()) {
                    try {
                        webProgress.dismiss();
                    } catch (Throwable var4) {
//                        QuMediaLogger.debug(QuMediaAndroidUtils.TAG, var4.getMessage());
                    }
                }

            }
        });
        wv.setWebViewClient(new WebViewClient() {
            boolean isProcess = false;

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
             //   Toast.makeText(activity, errorTitle + description, 0).show();
            }

            public void onPageFinished(WebView view, String url) {
//                QuMediaLogger.debug(QuMediaAndroidUtils.TAG, "onPageFinished url=[" + url + "]");
                if (!this.isProcess) {
                    this.doProcessUrl(url);
                }

            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //  QuMediaLogger.debug(QuMediaAndroidUtils.TAG, "shouldOverrideUrlLoading load url=requestUrl=[" + url + "]");
                if (!this.doProcessUrl(url)) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    this.isProcess = true;
                    return true;
                }
            }

            private boolean doProcessUrl(String url) {
                try {
                    Intent intent;
                    if (url.startsWith("vnd.youtube:")) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        activity.startActivity(intent);
                  //      List list = activity.getPackageManager().queryIntentActivities(intent, 65536);
                  //      QuMediaLogger.debug(QuMediaAndroidUtils.TAG, "shouldOverrideUrlLoading list.size()=[" + list.size() + "]");
//                        if (list.size() == 0) {
//                            return false;
//                        } else {
//                            try {
//                                activity.finish();
//                            } catch (Throwable var5) {
//                        //        QuMediaLogger.debug(QuMediaAndroidUtils.TAG, var5.getMessage());
//                            }
//
//                            return true;
//                        }
//                    } else if (url.contains("www.youtube.com")) {
//                        intent = new Intent("android.intent.action.VIEW", Uri.parse(QuMediaAndroidUtils.getYoutubeConvertString(url)));
//                        activity.startActivity(intent);
//                        if (webProgress.isShowing()) {
//                            try {
//                                webProgress.dismiss();
//                            } catch (Throwable var7) {
//                               // QuMediaLogger.debug(QuMediaAndroidUtils.TAG, var7.getMessage());
//                            }
//                        }

                        try {
                            activity.finish();
                        } catch (Throwable var6) {
                          //  QuMediaLogger.debug(QuMediaAndroidUtils.TAG, var6.getMessage());
                        }

                        return true;
                    } else if (url.endsWith(".mp3")) {
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse(url), "audio/*");
                        activity.startActivity(intent);
                        return true;
                    } else if (!url.endsWith(".mp4") && !url.endsWith(".3gp")) {
                        return false;
                    } else {
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse(url), "video/*");
                        activity.startActivity(intent);
                        return true;
                    }
                } catch (Exception var8) {
                    var8.printStackTrace();
                    return false;
                }
            }
        });
        QuMediaLogger.debug(TAG, "web load url=requestUrl=[" + requestUrl + "]");
        Intent intent;
//        if (requestUrl.startsWith("vnd.youtube:")) {
//            intent = new Intent("android.intent.action.VIEW", Uri.parse(requestUrl));
//            activity.startActivity(intent);
//            List list = activity.getPackageManager().queryIntentActivities(intent, 65536);
//            QuMediaLogger.debug(TAG, "shouldOverrideUrlLoading list.size()=[" + list.size() + "]");
//            if (list.size() != 0) {
//                if (webProgress.isShowing()) {
//                    try {
//                        webProgress.dismiss();
//                    } catch (Throwable var11) {
//                        QuMediaLogger.debug(TAG, var11.getMessage());
//                    }
//                }
//
//                try {
//                    activity.finish();
//                } catch (Throwable var10) {
//                    QuMediaLogger.debug(TAG, var10.getMessage());
//                }
//
//                return;
//            }
//        } else {
//            if (requestUrl.contains("www.youtube.com")) {
//                intent = new Intent("android.intent.action.VIEW", Uri.parse(getYoutubeConvertString(requestUrl)));
//                activity.startActivity(intent);
//                if (webProgress.isShowing()) {
//                    try {
//                        webProgress.dismiss();
//                    } catch (Throwable var13) {
//                        QuMediaLogger.debug(TAG, var13.getMessage());
//                    }
//                }
//
//                try {
//                    activity.finish();
//                } catch (Throwable var12) {
//                    QuMediaLogger.debug(TAG, var12.getMessage());
//                }
//
//                return;
//            }

            if (requestUrl.endsWith(".mp3")) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(Uri.parse(requestUrl), "audio/*");
                wv.getContext().startActivity(intent);
                if (webProgress.isShowing()) {
                    try {
                        webProgress.dismiss();
                    } catch (Throwable var14) {
                        QuMediaLogger.debug(TAG, var14.getMessage());
                    }
                }

                return;
            }

//            if (requestUrl.endsWith(".mp4") || requestUrl.endsWith(".3gp")) {
//                intent = new Intent("android.intent.action.VIEW");
//                intent.setDataAndType(Uri.parse(requestUrl), "video/*");
//                wv.getContext().startActivity(intent);
//                if (webProgress.isShowing()) {
//                    try {
//                        webProgress.dismiss();
//                    } catch (Throwable var15) {
//                        QuMediaLogger.debug(TAG, var15.getMessage());
//                    }
//                }
//
//                return;
//            }
//
//            wv.loadUrl(requestUrl);
//        }

    }

    public static String getYoutubeConvertString(String input) {
        String newUrl = null;
        if (input.startsWith("http://www.youtube.com/embed/")) {
            String playId = input.substring(input.lastIndexOf("/") + 1, input.lastIndexOf("?"));
            newUrl = "http://www.youtube.com/watch?v=" + playId;
            return newUrl;
        } else {
            return input;
        }
    }

    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = (float)w / (float)width;
        float scaleHeight = (float)h / (float)height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    public static int getListViewHeight(int minHeight, ArrayList<HashMap<String, Object>> listMap, String key, int wordHeight, int lineWidth, int paddingHeight) {
        int resultHeight = 0;
   //     int dataHeight = false;
        Iterator i$ = listMap.iterator();

        while(i$.hasNext()) {
            Map data = (HashMap)i$.next();
            int lineCount = StringUtils.getLineCount(data.get(key).toString(), lineWidth / wordHeight);
            int dataHeight = lineCount * wordHeight + paddingHeight;
            if (dataHeight > minHeight) {
                resultHeight += dataHeight;
            } else {
                resultHeight += minHeight;
            }
        }

        return resultHeight;
    }

    public static int getTitleBarHeight() {
        if (QuMediaConfigData.SCREEN_HEIGHT > 1000) {
            if (QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 400) {
                return 120;
            } else if (QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 240) {
                return 80;
            } else {
                return QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 160 ? 60 : 80;
            }
        } else if (QuMediaConfigData.SCREEN_HEIGHT >= 800) {
            if (QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 240) {
                return 80;
            } else if (QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 160) {
                return 65;
            } else {
                return QuMediaConfigData.SCREEN_DEVICE_DENSITY >= 120 ? 45 : 80;
            }
        } else {
            return QuMediaConfigData.SCREEN_HEIGHT > 600 ? 50 : 40;
        }
    }

    public static String convertSDKToOSVersion(int p_intSDK) {
        String strOS = "Unknown";

        try {
            switch(p_intSDK) {
                case 1:
                    strOS = "BASE (Android 1.0)";
                    break;
                case 2:
                    strOS = "BASE (Android 1.1)";
                    break;
                case 3:
                    strOS = "CupCake (Android 1.5)";
                    break;
                case 4:
                    strOS = "Donut (Android 1.6)";
                    break;
                case 5:
                    strOS = "Eclair (Android 2.0)";
                    break;
                case 6:
                    strOS = "Eclair (Android 2.0.1)";
                    break;
                case 7:
                    strOS = "Eclair_MR1 (Android 2.1)";
                    break;
                case 8:
                    strOS = "Froyo (Android 2.2)";
                    break;
                case 9:
                    strOS = "Gingerbread (Android 2.3)";
                    break;
                case 10:
                    strOS = "Gingerbread_MR1 ((Android 2.3.3)";
                    break;
                case 11:
                    strOS = "Honeycomb (Android 3.0)";
                    break;
                case 12:
                    strOS = "Honeycomb_MR1 (Android 3.1)";
                    break;
                case 13:
                    strOS = "Honeycomb_MR2 (Android 3.2)";
                    break;
                default:
                    if (p_intSDK > 13) {
                        strOS = "OS Version above Honeycomb (Android 3.2)";
                    }
            }
        } catch (Exception var3) {
            strOS = "Unknown SDK " + p_intSDK;
        }

        return strOS;
    }
}
