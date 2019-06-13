package com.piscesye.storagedescptiondemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.piscesye.storagedescptiondemo.R;

import java.io.File;

public class StorageFileManagerFragment extends Fragment {

    private TextView showFileManagerFilePath;
    private ListView showFileManagerFiles;

    private File parentFile;

    public static final int FLAG_SYSTEM_ROOT = 1;
    public static final int FLAG_INTERNAL_APP_ROOT = 2;
    public static final int FLAG_INTERNAL_APP_FILES = 3;
    public static final int FLAG_EXTERNAL_PUBLIC_ROOT = 4;
    public static final int FLAG_EXTERNAL_PUBLIC_PICTURES = 5;
    public static final int FLAG_EXTERNAL_PRIVATE_ROOT = 6;
    public static final int FLAG_EXTERNAL_PRIVATE_FILES = 7;
    public static final int FLAG_EXTERNAL_PRIVATE_PICTURES = 8;



    public static StorageFileManagerFragment newInstance(File rootFile) {

        Bundle args = new Bundle();

        StorageFileManagerFragment fragment = new StorageFileManagerFragment();
        fragment.setArguments(args);
        fragment.setParentFile(rootFile);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jarvis_storage_show_files, container, false);
        showFileManagerFilePath = root.findViewById(R.id.text_show_file_path);
        showFileManagerFilePath.setText(parentFile.getAbsolutePath());
        showFileManagerFiles = root.findViewById(R.id.list_file_manager_show_files);
        FileManagerAdapter adapter = new FileManagerAdapter(parentFile);
        showFileManagerFiles.setAdapter(adapter);
        return root;
    }

    public void setParentFile(File parentFile) {
        this.parentFile = parentFile;
    }

    class FileManagerAdapter extends BaseAdapter {
        private File parent;
        private File[] childFiles;
        private String[] childFileNames;

        FileManagerAdapter(File parentFile) {
            parent = parentFile;
            childFiles = parentFile.listFiles();
            childFileNames = parentFile.list();
        }

        @Override
        public int getCount() {
            return childFiles == null ? 0 : childFiles.length;
        }

        @Override
        public Object getItem(int position) {
            return childFiles == null ? -1 : childFiles[position];
        }

        @Override
        public long getItemId(int position) {
            return position + 1;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View item = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_adapter_jarvis_file_manager_item, parent, false);
            TextView fileName = item.findViewById(R.id.list_adapter_jarvis_file_manager_item_name);
            TextView chileFileNum = item.findViewById(R.id.list_adapter_jarvis_file_manager_child_file_num);
            ImageView showTypeImg = item.findViewById(R.id.list_adapter_jarvis_file_manager_type_img);

            if (childFiles != null) {
                String name = childFileNames[position];
                fileName.setText(name);

                boolean type = true;

                if (!childFiles[position].isDirectory()) {
                    showTypeImg.setImageDrawable(getActivity().getDrawable(R.drawable.jarvis_icon_storage_file));
                    type = false;
                }

                File[] childFilesC = childFiles[position].listFiles();
                if (childFilesC != null && type) {
                    String numResult = String.valueOf(childFiles[position].list().length) + "项";
                    chileFileNum.setText(numResult);
                }else if (!type){
                    chileFileNum.setText("");
                }
            }
            //----------------点击事件，实现进入子菜单-----------------------------
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File[] childs = childFiles[position].listFiles();
                    if (childs != null) {
                        Fragment goToChildFragment = StorageFileManagerFragment.newInstance(childFiles[position]);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_jarvis_file_manager_show_files, goToChildFragment)
                                .addToBackStack(null).commit();
                    }
                }
            });
            return item;
        }
    }
}
