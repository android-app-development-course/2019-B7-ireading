package com.example.ireading.ui.activity;

/**
 * =============================================================================
 * Describe :文章对象
 * ==============================================================================
 */

public class NovelContent {
    private String title;
    private String novel_name;
    private String author;
    private String nv_content;
    private String pre_link;
    private String next_link;

    public NovelContent() {
    }

    //作者 下一章链接 小说名称 本章内容 上一章名称 章节标题 更新时间 文章字数
    public NovelContent(String author, String next_link, String novel_name, String nv_content, String pre_link, String title) {
        this.author = author;
        this.next_link = next_link;
        this.novel_name = novel_name;
        this.nv_content = nv_content;
        this.pre_link = pre_link;
        this.title = title;
    }

    public String getNovel_name() {
        return novel_name;
    }

    public void setNovel_name(String novel_name) {
        this.novel_name = novel_name;
    }

    public String getNv_content() {
        return nv_content;
    }

    public void setNv_content(String nv_content) {
        this.nv_content = nv_content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPre_link() {
        return pre_link;
    }

    public void setPre_link(String pre_link) {
        this.pre_link = pre_link;
    }

    public String getNext_link() {
        return next_link;
    }

    public void setNext_link(String next_link) {
        this.next_link = next_link;
    }

    @Override
    public String toString() {
        return "NovelContent{" +
                "author='" + author + '\'' +
                ", novel_name='" + novel_name + '\'' +
                ", title='" + title + '\'' +
                ", nv_content=" + nv_content +
                ", pre_link='" + pre_link + '\'' +
                ", next_link='" + next_link + '\'' +
                '}';
    }
}