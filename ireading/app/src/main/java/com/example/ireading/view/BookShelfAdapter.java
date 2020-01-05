
package com.example.ireading.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ireading.R;
import com.example.ireading.model.entity.Book;

import java.util.List;

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.BookViewHolder> {
    OnItemClickListener mClickListener;
    OnItemLongClickListener mLongListener;
    private List<Book> mDataSet;

    public BookShelfAdapter(List<Book> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建一个item的实例
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        BookViewHolder vh = new BookViewHolder(v);
        return vh;
    }

    // 此处编写数据展示代码
    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position).getName());// 每一项显示书名
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(position);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mLongListener.onLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tvBookName);

        }
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener lis) {
        mClickListener = lis;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener lis) {
        mLongListener = lis;
    }
}

