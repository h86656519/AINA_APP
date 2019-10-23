package com.example.user.aina_app.common;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.aina_app.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private final String TAG = "MyFragment";
    private View view;
    private String[] items = new String[]{"從圖庫中選擇", "拍照"};
    private ImageView userpic;
    private static final int IMAGE_REQUEST_CODE = 0x000;
    private static final int CAMERA_REQUEST_CODE = 0x001;
    private static final int CODE_RESULT_REQUEST = 123;
    private static final int CODE_WRITE_EXTERNAL_STORAGE_REQUEST = 123;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Uri mUri, mCutUri;
    //    private File path;
    private File ainaFile;
    private Uri imageUri;
    private Uri cropImageUri;
    private String folderName = "AINA_APP";  //文件夹名称
    private File fileUri = new File(Environment.getExternalStorageDirectory() + "/" + folderName + "/" + "photo.jpg"); //拍照的原圖
    private File fileCropUri = new File(Environment.getExternalStorageDirectory() + "/" + folderName + "/" + "crop_photo.jpg"); //相機裁修過後的圖片


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
       // checkPermissions();
        initPath();
        initView();

        return view;
    }

    private void checkPermissions() {
//        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,};
//        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
//            EasyPermissions.requestPermissions(getActivity(), "沒有讀取權限會讀不到大頭貼喔", 100, perms);
//        }
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initView();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "沒有讀取權限會讀不到大頭貼喔",
                    CODE_WRITE_EXTERNAL_STORAGE_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }

    public static MyFragment newInstance() {
        MyFragment f = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("index", 4);
        f.setArguments(args);
        return f;
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(this.getString(R.string.my_account));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null); //隱藏 app name

        userpic = view.findViewById(R.id.userpic_imageview);
        //大頭照預設判斷，有裁過就用裁過的圖
        if (fileCropUri.exists()) {
            //   String s = Environment.getDataDirectory().getPath() + "/" + folderName + "/" + "crop_photo.jpg";
            String s = Environment.getExternalStorageDirectory() + "/" + folderName + "/" + "crop_photo.jpg";
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            userpic.setImageBitmap(bitmap);
        } else {
            userpic.setImageResource(R.drawable.pic_user);
        }

        userpic.setOnClickListener(listener);
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openPic(IMAGE_REQUEST_CODE); //啟動相簿
                                break;
                            case 1:
                                checkPermissionAndOpen();

                                break;
                        }
                    }
                }).show();
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.userpic_imageview:
                    showDialog();
                    break;
            }
        }
    };


    //建存放的資料夾
    private void initPath() {
        ainaFile = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
        //  Environment.getExternalStorageDirectory() 抓的是 SD卡的位置
        if (!ainaFile.exists()) {
            ainaFile.mkdirs();
        }

//        Log.i(TAG, "path.mkdirs() : " + ainaFile.mkdirs());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    mUri = data.getData();  // 要被裁剪图片
                    CutForPhoto(mUri, CODE_RESULT_REQUEST);
                    break;

                case CAMERA_REQUEST_CODE:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    cropImageUri(imageUri, cropImageUri, 1, 1, 480, 480, CODE_RESULT_REQUEST);
                    break;

                case CODE_RESULT_REQUEST:
                    if (data != null) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(mCutUri));
                            userpic.setImageBitmap(bitmap); // 不能用 setImageURI 因為加載一次後就不更新， userpic.setImageURI(mCutUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fileUri.delete();
                    }
                    break;
            }
        }
    }

    private void CutForPhoto(Uri uri, int requestCode) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(ainaFile, "crop_photo.jpg"); //1.建立目錄物件，路徑，檔名 2.用同檔名直接硬蓋過去
            // File cutfile = new File(path, System.currentTimeMillis() + ".png"); //建立目錄物件，路徑，檔名
//            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
//                //cutfile.delete();
//            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            outputUri = Uri.fromFile(cutfile); //裁過後的圖
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);   //發送裁剪信息
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1); // 水平的比例
            intent.putExtra("aspectY", 1); // 垂直的比例
            intent.putExtra("outputX", 150); //裁剪的寬
            intent.putExtra("outputY", 150); //裁剪的高
            intent.putExtra("scale", true);  //保留比例
            intent.putExtra("circleCrop", true); // 是否是圆形裁剪区域，设置了也不一定有效
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);  // 是否将数据保留在Bitmap中返回，萬一內存不足的話會 OOM
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true); // 是否取消人臉辨識功能
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 用輸出格式壓縮圖片

            startActivityForResult(intent, requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //啟動相簿
    public void openPic(int requestCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, requestCode);
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA);
    }

    //被@AfterPermissionGranted注解的方法会在请求码中的所有权限申请成功之后 自動 被调用，
    // 就不用在 onPermissionsGranted 裡寫switch 比對，要二選一，不然會調用二次
    @AfterPermissionGranted(CAMERA_REQUEST_CODE)
    private void openCamera() {
        //通过FileProvider创建一个content类型的Uri
        imageUri = FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".fileprovider", fileUri);

        //调用系统相机
        Intent intentCamera = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
    }

    public void checkPermissionAndOpen() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            openCamera();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "相機權限呢?",
                    CAMERA_REQUEST_CODE, Manifest.permission.CAMERA);
        }


    }

    //     * @param orgUri      剪裁原图的Uri
//     * @param desUri      剪裁后的图片的Uri
    public void cropImageUri(Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        mCutUri = desUri;

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult requestCode: " + requestCode);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.i(TAG, "onPermissionsGranted requestCode: " + requestCode);
        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:
//                openCamera(); //這邊呼叫是想實現按完允許後就開相機的流程
//                break;
            case CODE_WRITE_EXTERNAL_STORAGE_REQUEST:
                initView(); //刷新頁面才會顯示大頭貼
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show(); //引導user 去setting 手動打開
        }

    }

}
