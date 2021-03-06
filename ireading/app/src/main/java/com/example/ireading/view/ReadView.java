package com.example.ireading.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class ReadView extends AppCompatTextView {

    public ReadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resize();
    }

    /**
     * 取出当前页无法显示的字
     *
     * @return 去掉的字数
     */
    public int resize() {
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0, getCharNum());
        setText(newContent);
        return oldContent.length() - newContent.length();
    }

    /**
     * 计算出这行最后一个字是第几个字
     */
    public int getCharNum() {
        return getLayout().getLineEnd(getLineNum());//getLineEnd(int line)返回指定行中最后一个字在整个字符串中的位置
    }

    /**
     * 计算行号 最下面一行是第几行 获取当前页的总行数
     */
    public int getLineNum() {
        Layout layout = getLayout();
        int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        return layout.getLineForVertical(topOfLastLine);
    }
}
