package com.example.ireading.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ireading.R;
import com.example.ireading.view.ReadView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelsDisplayActivity extends Activity {

    private NovelContent novalContentBean;
    private TextView tv_title;
    private TextView tv_name;

    //private TextView tv_con;
    private ReadView readView;
    private Button bt_pre;
    private Button bt_next;

    BufferedReader reader;
    CharBuffer buffer = CharBuffer.allocate(8000); //新建一个缓冲区
    int position;

    //更新文章内容
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {

                tv_title.setText(novalContentBean.getTitle());
                tv_name.setText(novalContentBean.getNovel_name());

                String str = novalContentBean.getNv_content();//getNovel_content返回的是小说具体内容string文本
                loadBook(str);
                loadPage(1);
                //如果上一章不存在
                Pattern pre_p = Pattern.compile(".*html.*");  //具体章节内容的网址
                Matcher pre_m = pre_p.matcher(novalContentBean.getPre_link());
                boolean pre_b = pre_m.matches();
                if (pre_b == false || novalContentBean.getPre_link() == null) {
                    bt_pre.setVisibility(View.INVISIBLE);
                    bt_pre.setClickable(false);
                }
                //如果下一章不存在
                Pattern next_p = Pattern.compile(".*html.*");  //具体章节内容的网址
                Matcher next_m = next_p.matcher(novalContentBean.getNext_link());
                boolean next_b = next_m.matches();
                if (next_b == false || novalContentBean.getNext_link() == null) {
                    bt_next.setVisibility(View.INVISIBLE);
                    bt_next.setClickable(false);
                }
                //上一章的点击事件
                bt_pre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NovelsDisplayActivity.this, NovelsDisplayActivity.class);
                        intent.putExtra("news_url", novalContentBean.getPre_link());
                        startActivity(intent);
                        finish();
                    }
                });
                //下一章的点击事件
                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(NovelsDisplayActivity.this, novalContentBean.getNext_link(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NovelsDisplayActivity.this, NovelsDisplayActivity.class);
                        intent.putExtra("news_url", novalContentBean.getNext_link());
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_novels_display);
        tv_name = (TextView) findViewById(R.id.novel_name);
        tv_title = (TextView) findViewById(R.id.title);

        //tv_author = (TextView) findViewById(R.id.tv_author);

        readView = (ReadView) findViewById(R.id.tv_con);
        //tv_con.setMovementMethod(new ScrollingMovementMethod()); //textview上下滚动
        bt_pre = (Button) findViewById(R.id.bt_pre);
        bt_next = (Button) findViewById(R.id.bt_next);

        //Intent intent = getIntent();
        final String news_url = getIntent().getStringExtra("news_url"); //从MainActivity获取具体章节内容的链接

        //final String news_url=url;
        //Toast.makeText(NovelsDisplayActivity.this, news_url, Toast.LENGTH_LONG).show();

        new Thread() {
            @Override
            public void run() {
                novalContentBean = ParserWeb.parser_nol(news_url);
                if (novalContentBean != null) {
                    mhandler.sendEmptyMessage(111);
                }
            }
        }.start();
    }

    /**
     * 将电子书都一部分到缓冲区
     */
    private void loadBook(String str) {
        try {
            InputStream in = new ByteArrayInputStream(str.getBytes());
            Charset charset = CharsetDetector.detect(in);    //获取可能性最高的编码格式
            reader = new BufferedReader(new InputStreamReader(in, charset));
            reader.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定位置开始载入一页
     */
    private void loadPage(int position) {

        buffer.position(position);
        //if(readView.getCharNum() ==0){
        //Toast.makeText(NovelsDisplayActivity.this, "没有下一页了", Toast.LENGTH_LONG).show();
        //return;
        //}
        //else {
        readView.setText(buffer);
        //}
    }

    /**
     * 上一页按钮
     */
    public void previewPageBtn(View view) {

        if (position <= 1) {
            return;
        } else {
            position -= readView.getCharNum();
            loadPage(position);
            readView.resize();
        }
    }

    /**
     * 下一页按钮
     */
    public void nextPageBtn(View view) {

        position += readView.getCharNum();
        loadPage(position);
        readView.resize();
    }
}
