package com.piscesye.storagedescptiondemo.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.piscesye.storagedescptiondemo.R;
import com.piscesye.storagedescptiondemo.ui.fragment.StorageFileManagerFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StorageManagerFeatureActivity extends AppCompatActivity {

    public static final String TAG = "StorageManagerFeatureActivity";
    public static final String DIR_FLAG = "StorageManagerFeatureActivity.dirFlag";

    private ImageView goToShowSystemRootDirActivity;
    private ImageView goToShowInternalAppRootDirActivity;
    private ImageView goToShowInternalAppFilesDirActivity;
    private ImageView goToShowExternalPublicRootDirActivity;
    private ImageView goToShowExternalPublicPicturesDirActivtiy;
    private ImageView goToShowExternalPrivateRootDirActivtiy;
    private ImageView goToShowExternalPrivateFilesDirActivtiy;
    private ImageView goToShowExternalPrivatePicturesDirActivtiy;

    private ProgressBar progressInternalStorage;
    private TextView showInternalStorageMsg;
    private ProgressBar progressExternalStorage;
    private TextView showExternalStorageMsg;

    private TextView pageStorageDescription;

    public static Intent newIntent(Context context) {
        Intent storageManagerFeatureIntent = new Intent(context, StorageManagerFeatureActivity.class);
        return storageManagerFeatureIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_manager_feature);
        setTitle("内存结构显示");

        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 1);
        }
        //------------------ set process bar to show storage messgae ------------------
        long byteToM = 1024 * 1024;
        long byteToG = byteToM * 1024;

        progressExternalStorage = findViewById(R.id.progress_external_storage);
        showExternalStorageMsg = findViewById(R.id.text_external_storage_show);
        StatFs externalStat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long externalBlock = externalStat.getBlockSizeLong();
        long externalTotals = externalStat.getBlockCountLong();
        long externalAvailable = externalStat.getAvailableBlocksLong();
        int externalTotalInt = (int) (externalBlock * externalTotals / byteToG);
        int externalAvailableInt = (int) (externalBlock * externalAvailable / byteToG);
        String externalMsg = String.valueOf(externalAvailableInt)
                + "G/ " + String.valueOf(externalTotalInt) + "G"
                +"\n可用/总内存";
        showExternalStorageMsg.setText(externalMsg);
        int externalResult = (((externalTotalInt - externalAvailableInt) * 100) / externalTotalInt);
        progressExternalStorage.setProgress(externalResult);


        progressInternalStorage = findViewById(R.id.progress_internal_storage);
        showInternalStorageMsg = findViewById(R.id.text_internal_storage_show);
        File internalFile = getFilesDir().getParentFile();
        StatFs internalStat = new StatFs(internalFile.getPath());
        long internalBlock = internalStat.getBlockSizeLong();
        long internalTotals = internalStat.getBlockSizeLong();
        long internalAvailable = internalStat.getAvailableBlocksLong();

        int internalTotalInt = (int) (internalBlock * internalTotals / byteToM);
        int internalAvaliable = (int) (internalBlock * internalAvailable / byteToM);
        String internalResult = String.valueOf(internalAvaliable)
                + "M/ " + String.valueOf(internalTotalInt) + "M"
                +"\n可用/总内存";
        showInternalStorageMsg.setText(internalResult);
        progressInternalStorage.setProgress(100);

        //--------------------- Go To Show System Root Dir Activity --------------------
        goToShowSystemRootDirActivity = findViewById(R.id.img_jarvis_dir_system_root);
        goToShowSystemRootDirActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "系统文件"
                        , StorageFileManagerFragment.FLAG_SYSTEM_ROOT
                ));
            }
        });
        //-------------- Go To Show Internal Application Root Dir Activity -------------
        goToShowInternalAppRootDirActivity = findViewById(R.id.img_jarvis_dir_internal_app_root);
        goToShowInternalAppRootDirActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "应用内部存储根目录"
                        , StorageFileManagerFragment.FLAG_INTERNAL_APP_ROOT
                ));
            }
        });
        //-------------- Go To Show Internal Application Files Dir Activity ------------
        goToShowInternalAppFilesDirActivity = findViewById(R.id.img_jarvis_dir_internal_files);
        goToShowInternalAppFilesDirActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "应用内部存储文件目录"
                        , StorageFileManagerFragment.FLAG_INTERNAL_APP_FILES
                ));
            }
        });
        //--------------------- Go To Show External Root Dir Activity ------------------
        goToShowExternalPublicRootDirActivity = findViewById(R.id.img_jarvis_dir_eaternal_public_root);
        goToShowExternalPublicRootDirActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "SD卡存储根目录"
                        , StorageFileManagerFragment.FLAG_EXTERNAL_PUBLIC_ROOT
                ));
            }
        });
        //-------------- Go To Show External Public Pictures Dir Activity --------------
        goToShowExternalPublicPicturesDirActivtiy = findViewById(R.id.img_jarvis_dir_external_public_pictures);
        goToShowExternalPublicPicturesDirActivtiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "SD卡图片存储"
                        , StorageFileManagerFragment.FLAG_EXTERNAL_PUBLIC_PICTURES
                ));
            }
        });
        //---------------- Go To Show External Private Root Dir Activity ---------------
        goToShowExternalPrivateRootDirActivtiy = findViewById(R.id.img_jarvis_dir_external_private_root);
        goToShowExternalPrivateRootDirActivtiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "Sd卡应用存储根目录"
                        , StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_ROOT
                ));
            }
        });
        //--------------- Go To Show External Private Files Dir Activity ---------------
        goToShowExternalPrivateFilesDirActivtiy = findViewById(R.id.img_jarvis_dir_external_private_files);
        goToShowExternalPrivateFilesDirActivtiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "Sd卡应用存储文件目录"
                        , StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_FILES
                ));
            }
        });
        //-------------- Go To Show External Private Pictures Dir Activity -------------
        goToShowExternalPrivatePicturesDirActivtiy = findViewById(R.id.img_jarvis_dir_external_private_pictures);
        goToShowExternalPrivatePicturesDirActivtiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StorageFileManagerActivity.newIntent(
                        StorageManagerFeatureActivity.this
                        , "Sd卡应用存储图片目录"
                        , StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_PICTURES
                ));
            }
        });

        //------------------ go to description page --------------------------
        pageStorageDescription = findViewById(R.id.text_go_to_description);
        pageStorageDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(Uri.parse("https://www.jianshu.com/p/f5baf970c843"));
                startActivity(browser);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) { //安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {

                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) { //这个是权限拒绝
                            String s = permissions[i];
                            Toast.makeText(this, s + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        } else { //授权成功了
                            //do Something
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_jarvis_file_manager_show, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_jarvis_file_manager_return:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
