package com.example.ireading;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Unbinder unbinder;
    final static int TXT_CODE = 1;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MainActivityFragment.TXT_CODE) {
//                获取文件的路径
                Uri uri = data.getData();
                Log.e("文件路径", uri.getPath().toString());
// todo: 将路径和书名保存到数据库，用recyclerview展示出来
//                调整小说书架布局
//                todo: showActivity展示小说文本
//                todo：打开小说文件

                try {
                    String txt;
                    InputStream fis;
                    fis = getContext().getContentResolver().openInputStream(uri);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    txt = new String(buffer, "UTF-8");
                    fis.close();
                    tvShowSelectedFile.setText(txt);
//                    Log.v("txt内容",txt);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "没有找到指定文件", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }

    }

    @BindView(R.id.choose_txt_btn)
    Button btnChooseTxt;
    @BindView(R.id.show_selected_file)
    TextView tvShowSelectedFile;

    @OnClick(R.id.choose_txt_btn)
    public void onClick() {
        Log.e(getClass().getName(), "点击了按钮");
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置选择类型为txt类型
        intent.setType("text/plain");
        // 打开文本选择
        startActivityForResult(intent, TXT_CODE);


    }


}
