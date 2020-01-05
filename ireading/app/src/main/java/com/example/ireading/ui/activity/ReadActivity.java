package com.example.ireading.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ireading.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReadActivity extends AppCompatActivity {
    InputStream fis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        ButterKnife.bind(this);
        try {
            fis = getContentResolver().openInputStream(uri);
            byte[] buf = new byte[fis.available()];
            fis.read(buf);
            String txt = new String(buf, "UTF-8");
            TextView textView = findViewById(R.id.show_content);
            textView.setText(txt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }


    @BindView(R.id.ivBack)
    ImageView mIvBack;

    @BindView(R.id.flReadWidget)
    FrameLayout flReadWidget;

    @BindView(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;

    @BindView(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @BindView(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;

    @BindView(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @BindView(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @BindView(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;





}


