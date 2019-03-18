package com.example.user.aina_app.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qumedia.android.core.common.HttpConnectThread;
import com.qumedia.android.core.common.QuMediaConfigData;
import com.qumedia.android.core.common.QuMediaLogger;
import com.qumedia.android.core.exception.MobileException;
import com.qumedia.android.core.util.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.IOException;
import java.io.InputStream;

//import org.apache.http.HttpResponse;
//import org.apache.http.HttpVersion;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpProtocolParams;

public class HttpConnectThread2 extends Thread {
    private static final String TAG = HttpConnectThread.class.getName();
    public static final String BUNDLE_HTTP_MESSAGE_TYPE_DATA = "HTTP_MESSAGE_TYPE";
    public static final String HTTP_MESSAGE_TYPE_PORCESS = "PORCESS";
    public static final String HTTP_MESSAGE_TYPE_ERROR = "ERROR";
    public static final String HTTP_MESSAGE_TYPE_FINISH = "FINISH";
    public static final String HTTP_MESSAGE_TYPE_STRING_FINISH = "STRING_FINISH";
    public static final String HTTP_MESSAGE_TYPE_RESULT_STRING_FINISH = "RESULT_STRING_FINISH";
    public static final String BUNDLE_REPONSE_DATA = "REPONSE_DATA";
    public static final String BUNDLE_REPONSE_MESSAGE = "REPONSE_MESSAGE";
    private static final int MAX_LENGTH = 102400;
    private boolean cancel = false;
    private String url;
    private byte[] buffer = null;
    private Handler progressHandler = null;

    public HttpConnectThread2(String url, Handler progressHandler) {
        StringBuilder urlBuffer = new StringBuilder();
        urlBuffer.append(url);
        this.url = urlBuffer.toString();
        QuMediaConfigData.REQUEST_URL_CACHE = this.url;
        QuMediaLogger.debug(TAG, "request url=" + this.url);
        this.progressHandler = progressHandler;
    }

    public void cancel() {
        this.cancel = true;
    }

    private void sendProcessMessage(int process) {
        Log.i(TAG,"sendProcessMessage123"+process);
        Message aMessage = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("HTTP_MESSAGE_TYPE", "PORCESS");
        bundle.putInt("REPONSE_DATA", process);
        aMessage.setData(bundle);
        this.progressHandler.sendMessage(aMessage);
    }

    private void sendFinishMessage(byte[] data) {
        Log.i(TAG,"sendFinishMessage123");
        Message aMessage = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("HTTP_MESSAGE_TYPE", "FINISH");
        bundle.putByteArray("REPONSE_DATA", data);
        aMessage.setData(bundle);
        this.progressHandler.sendMessage(aMessage);
    }

    private void sendFinishMessage(String data) {
        Log.i(TAG,"sendFinishMessage123");
        Message aMessage = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("HTTP_MESSAGE_TYPE", "FINISH");
        bundle.putString("REPONSE_DATA", data);
        aMessage.setData(bundle);
        this.progressHandler.sendMessage(aMessage);
    }

    private void sendErrorMessage(String errorCode, String message) {
        Log.i(TAG,"sendErrorMessage123");
        Message aMessage = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("HTTP_MESSAGE_TYPE", "ERROR");
        bundle.putString("REPONSE_DATA", errorCode);
        bundle.putString("REPONSE_MESSAGE", message);
        aMessage.setData(bundle);
        this.progressHandler.sendMessage(aMessage);
    }

    public void run() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        params.setBooleanParameter("http.protocol.expect-continue", false);
        HttpClient httpclient = new DefaultHttpClient(params);
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", QuMediaConfigData.HTTP_TIMEOUT);
        HttpRequestBase httpRequest = null;
        HttpResponse httpResponse = null;
        InputStream inputStream = null;

        try {
            httpRequest = new HttpGet(this.url);
            httpResponse = httpclient.execute(httpRequest);
            inputStream = httpResponse.getEntity().getContent();
            int size = -1;
            int percent = 0;
           // int tmp_percent = false;
            int index = 0;
            if (size != -1) {
                this.buffer = new byte[size];
            } else {
                this.buffer = new byte[102400];
            }

            while(!this.cancel) {
                int len = this.buffer.length - index;
                len = len > 128 ? 128 : len;
                int reads = inputStream.read(this.buffer, index, len);
                if (reads <= 0) {
                    break;
                }

                index += reads;
                if (size > 0) {
                    int tmp_percent = index * 100 / size;
                    if (tmp_percent != percent) {
                        percent = tmp_percent;
                        this.sendProcessMessage(tmp_percent);
                    }
                }
            }

            if (!this.cancel && inputStream.available() > 0) {
                throw new MobileException(String.valueOf(601));
            }

            if (!this.cancel) {
                if (size != -1 && index != size) {
                    throw new MobileException(String.valueOf(102));
                }

                Log.i(TAG,"aaa");
                this.sendProcessMessage(100);
                Log.i(TAG,"bb");
                this.sendFinishMessage(this.buffer);
                Log.i(TAG,"cc");
            }
        } catch (IOException var22) {
            QuMediaLogger.error(TAG, var22);
            this.sendErrorMessage(String.valueOf(9999), var22.getMessage());
        } catch (Exception var23) {
            QuMediaLogger.error(TAG, var23);
            if (StringUtils.isEmpty(var23.getMessage())) {
                this.sendErrorMessage(String.valueOf(9999), "Connection Timeout!");
            } else {
                this.sendErrorMessage(String.valueOf(9999), var23.getMessage());
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var21) {
                    QuMediaLogger.error(TAG, var21);
                }
            }

            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }

        }

    }
}
