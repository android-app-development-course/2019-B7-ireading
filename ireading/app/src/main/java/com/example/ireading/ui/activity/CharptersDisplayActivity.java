package com.example.ireading.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ireading.R;
import com.example.ireading.utils.News;
import com.example.ireading.utils.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import com.hty.testbrowser.R;

public class CharptersDisplayActivity extends AppCompatActivity {

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    private static Map<String, String> headersMap = new HashMap<>();

    //private static String baseUrl ="https://www.biquge.info/10_10582/";
    //private static String addUrl ="http://www.xbiquge.la";
    static {
        headersMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters_display);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);
        String url = getIntent().getStringExtra("charpter_url"); //从MainActivity获取小说目录的链接
        final String news_url = url.replace("https://m", "http://www");
        //Toast.makeText(CharptersDisplayActivity.this, news_url, Toast.LENGTH_SHORT).show();
        getNews(news_url);//getNews()获取到数据后，在handlerMessage()里接受到子线程获取完数据的消息，开始为ListView加载数据。并为ListView的item设置点击事件，
        //当点击item时，将该item所对应的新闻的网址传递给NewsDisplayActivity，NewsDisplayActivity得到网址后用WebView加载该网址，便可看到新闻

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) { //获取数据成功，更新listview
                    adapter = new NewsAdapter(CharptersDisplayActivity.this, newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(CharptersDisplayActivity.this, NovelsDisplayActivity.class);
                            intent.putExtra("news_url", news.getNewsUrl()); //存储具体章节的连接并传给NewsDisplayActvivity
                            //Toast.makeText(CharptersDisplayActivity.this, news.getNewsUrl(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });
                }
            }
        };

    }

    private void getNews(final String url) {

        Log.e("Jsoup", "Test");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    //for(int i = 6753003;i<=6753022;i++) {

                    Document doc = Jsoup.connect(url).headers(headersMap).get();
                    Elements titleLinks = doc.select("div#list dd");    //解析来获取每章节的标题与链接地址

                    Log.e("title", Integer.toString(titleLinks.size()));
                    for (int j = 0; j < titleLinks.size(); j++) {
                        String title = titleLinks.get(j).select("a").text();    //章节标题
                        String uri = url + titleLinks.get(j).select("a").attr("href");//章节链接

                        News news = new News(title, uri, null, null);//将数据封装到实体类里
                        newsList.add(news);
                    }

                    Message msg = new Message();
                    msg.what = 1;   //获取数据成功标志
                    handler.sendMessage(msg);//将子线程的消息发送到主线程，通知主线程数据已加载完成，可以将数据加载到ListView上显示出来

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
