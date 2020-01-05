package com.example.ireading.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ireading.R;

import java.util.List;

/**
 * 为主页面ListVie加载爬取下来的数据
 */

public class NewsAdapter extends BaseAdapter {

    private List<News> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public NewsAdapter(Context mContext, List<News> newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.news_item,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.newsTitle = (TextView) view
                    .findViewById(R.id.news_title);
            viewHolder.newsDesc = (TextView) view.findViewById(R.id.news_desc);
            viewHolder.newsTime = (TextView) view.findViewById(R.id.news_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.newsTitle.setText(newsList.get(position).getNewsTitle());
        viewHolder.newsDesc.setText(newsList.get(position).getDesc());
        //viewHolder.newsDesc.setTextColor(#D81B60);
        //viewHolder.newsTime.setText("来自 : "+newsList.get(position).getNewsTime());
        return view;
    }

    class ViewHolder {
        TextView newsTitle;
        TextView newsDesc;
        TextView newsTime;
    }

}
