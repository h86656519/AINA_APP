package com.example.user.aina_app.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.CustomerConfigData;
import com.example.user.aina_app.common.QuMediaAndroidUtils2;
import com.example.user.aina_app.common.QuMediaBaseActivity2;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class StartActivity extends QuMediaBaseActivity2  implements EasyPermissions.PermissionCallbacks{
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        //當所有許可權都滿足的時候，返回true，否則返回false
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            Log.i("StartActivity", "有權限");
//        } else {
//            Log.i("StartActivity", "沒有權限");
//            EasyPermissions.requestPermissions(this, "相機權限請求 才可以客製個人頭貼喔",
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS, perms);
//        }
    }

    @Override
    public void showProcessDialog() {

    }


    public void onclick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.entertop:
                intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.vertifyproduct:
                //  Toast.makeText(StartActivity.this, "功能尚待補齊中", Toast.LENGTH_SHORT).show();
//                intent = new Intent(StartActivity.this, ProductVerifyActivity.class);
//                startActivity(intent);
                QuMediaAndroidUtils2.startActivity(StartActivity.this,
                        ProductVerifyActivity.class,
                        null,
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        //                       Intent.FLAG_ACTIVITY_CLEAR_TOP
                        , CustomerConfigData.ACTION_TAB_1);
                break;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
//    用戶授權成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("StartActivity", "onPermissionsGranted 有權限");
    }

//    用戶授權失敗
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i("StartActivity", "onPermissionsDenied 沒權限");
        /**
         　　* 若是在權限彈窗中，用戶勾選了'NEVER ASK AGAIN.'或者'不在提示'，且拒絕權限。
         　　* 這時候，需要跳轉到設置界面去，讓用戶手動開啓。
         　　*/
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


}
