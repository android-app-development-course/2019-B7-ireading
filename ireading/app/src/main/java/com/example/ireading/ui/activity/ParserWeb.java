package com.example.ireading.ui.activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParserWeb {
    private static final String TAG = ParserWeb.class.getSimpleName();
    private static NovelContent novelContent;

    //解析文章内容,返回完整的文章对象
    public static NovelContent parser_nol(String url) {
        NovelContent novalcon = new NovelContent();
        // List<String> con = new ArrayList<>(); //小说具体内容
        try {
            Document novel = Jsoup.connect(url).get();

            Elements read_con = novel.select("div.box_con");

            Elements select = read_con.select("div.con_top");
            String novel_name = select.first().getElementsByTag("a").get(2).text(); //第三个a标签
            novalcon.setNovel_name(novel_name);
            //  e(TAG, "parser_nol:novel_name" + novel_name);

            String title = read_con.first().getElementsByTag("h1").text();
            novalcon.setTitle(title);
            // e(TAG, "parser_nol" + title);

            // TODO 处理具体的小说文本内容
            Elements select1 = read_con.select("div#content");
            String content = select1.get(0).text();
            //格式美化 换行和空格
            content = content.replace(Jsoup.parse("&nbsp&nbsp&nbsp&nbsp;").text(), "\n      ");
            //content = content.replaceAll(" 　　 　　", "\n\n");
            //content = content.replaceAll("    ", "");
            novalcon.setNv_content(content);
            // e(TAG, "parser_nol" + con.toString() + "\n");

            Elements select2 = read_con.select("div.bottem");
            Elements elements = select2.first().getElementsByTag("a");

            String pre = "";
            String next = "";
            //判断是否存在上一章
            pre = elements.get(1).attr("href");//第二个a标签的href值

            //判断是否存在下一章
            next = elements.get(3).attr("href");
            // e(TAG, "parser_nol" + "pre"+pre+"next"+next);
            if (next != url) {
                novalcon.setNext_link(next);
            }
            novalcon.setPre_link(pre);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return novalcon;
    }
}
