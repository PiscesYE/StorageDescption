package com.piscesye.storagedescptiondemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.piscesye.storagedescptiondemo.R;
import com.piscesye.storagedescptiondemo.ui.fragment.StorageFileManagerFragment;

import java.io.File;

public class StorageFileManagerActivity extends AppCompatActivity {
    public static final String TAG = "测试可连接：";

    public static final String FILE_MANAGER_FEATURE_TITLE = "StorageFileManagerActivity.title";
    public static final String DIR_FLAG = "StorageFileManagerActivity.dirFlag";

    private Fragment showFilesFragment;
    private File parentFile;

    public static Intent newIntent(Context context, String title, int dirFlag) {
        Intent storageFileManagerIntent = new Intent(context, StorageFileManagerActivity.class);
        storageFileManagerIntent.putExtra(FILE_MANAGER_FEATURE_TITLE, title);
        storageFileManagerIntent.putExtra(DIR_FLAG, dirFlag);
        return storageFileManagerIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_file_manager);
        setTitle(getIntent().getStringExtra(FILE_MANAGER_FEATURE_TITLE));
        //---------------- Add show files fragment ----------------
        //使用标记定义Fragment的parentFile
        setParentFile(getIntent().getIntExtra(DIR_FLAG, 4));
        showFilesFragment = StorageFileManagerFragment.newInstance(parentFile);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_jarvis_file_manager_show_files, showFilesFragment)
                .addToBackStack(null).commit();

    }

    public void setParentFile(int flag) {
        switch (flag) {
            case StorageFileManagerFragment.FLAG_SYSTEM_ROOT:
                parentFile = Environment.getRootDirectory();
                break;
            case StorageFileManagerFragment.FLAG_INTERNAL_APP_ROOT:
                parentFile = getFilesDir().getParentFile();
                break;
            case StorageFileManagerFragment.FLAG_INTERNAL_APP_FILES:
                parentFile = getFilesDir();
                break;
            case StorageFileManagerFragment.FLAG_EXTERNAL_PUBLIC_ROOT:
                parentFile = Environment.getExternalStorageDirectory();
                break;
            case StorageFileManagerFragment.FLAG_EXTERNAL_PUBLIC_PICTURES:
                parentFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                break;
            case StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_ROOT:
                parentFile = getExternalFilesDir(null).getParentFile();
                break;
            case StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_FILES:
                parentFile = getExternalFilesDir(null);
                break;
            case StorageFileManagerFragment.FLAG_EXTERNAL_PRIVATE_PICTURES:
                parentFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_jarvis_file_manager_show, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
