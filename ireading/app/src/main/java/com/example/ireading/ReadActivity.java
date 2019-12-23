package com.example.ireading;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        try {
            InputStream fis = getContentResolver().openInputStream(uri);
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
}
