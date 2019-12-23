package com.example.ireading;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ireading.adapter.BookShelfAdapter;
import com.example.ireading.model.database.AppDatabase;
import com.example.ireading.model.entity.Book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListBookFragment extends Fragment {
    final static int INIT = 1;
    final static int REFRESH = 2;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    // todo:设置点击打开阅读，长按选择删除
                    mAdapter.setOnItemClickListener(new BookShelfAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int pos) {
                            Intent intent = new Intent(getActivity(), ReadActivity.class);
                            Uri uri = Uri.parse(mDataset.get(pos).getUri());
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    mAdapter.setOnItemLongClickListener(new BookShelfAdapter.OnItemLongClickListener() {
                        @Override
                        public void onLongClick(int pos) {
                            new Thread() {
                                public void run() {
                                    mDatabase.bookDao().delete(mDataset.get(pos));
                                    mDataset.remove(pos);
                                    Message msg = new Message();
                                    msg.what = REFRESH;
                                    mHandler.sendMessage(msg);
                                }
                            }.start();


                        }

                    });

                    mRecyclerView.setAdapter(mAdapter);
                    break;
                case REFRESH:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    List<Book> mDataset;
    Unbinder unbinder;
    RecyclerView mRecyclerView;
    BookShelfAdapter mAdapter;
    AppDatabase mDatabase;

    final static int TXT_CODE = 1;


    public ListBookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = view.findViewById(R.id.rv_bookshelf);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // 设置recyclerview
        super.onActivityCreated(savedInstanceState);
        new Thread() {
            public void run() {
                mDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                        AppDatabase.class, "ireading.db").build();
                mDataset = mDatabase.bookDao().queryAll();// 获取数据库的书名数据
                Log.i("livedata是否为空", (mDataset == null) ? "是的" : "非空");
                mAdapter = new BookShelfAdapter(mDataset);
                Message msg = new Message();
                msg.what = INIT;
                mHandler.sendMessage(msg);
            }
        }.start();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ListBookFragment.TXT_CODE) { // 从文件管理选择文本文件
                // 获取文件的uri
                Uri uri = data.getData();
                Log.i("文件路径", uri.getPath().toString());

                try {
                    InputStream fis;
                    fis = getContext().getContentResolver().openInputStream(uri);// 如果没报异常就说明文件存在

                    fis.close();
                    // 添加书本，更新ui
                    String name = uri.getPath().toString();
                    name = new String(name.substring(name.lastIndexOf("/") + 1, name.length()));
                    Book book = new Book(name, uri.toString());
                    mDataset.add(book);
                    mAdapter.notifyDataSetChanged();
                    new Thread() {
                        public void run() {
                            mDatabase.bookDao().insert(book);
                        }
                    }.start();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "没有找到指定文件", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }

    }


    @OnClick(R.id.fab)
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
