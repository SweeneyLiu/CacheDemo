package com.lsw.demo.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsw.demo.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //跳转到测试LruCache的Activity
        Button LruCacheButton = (Button)findViewById(R.id.btn_lru_cache);
        LruCacheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LruCacheActivity.class);
                startActivity(intent);
            }
        });

        //跳转到测试DiskLruCache的Activity
        Button button = (Button)findViewById(R.id.btn_disk_cache);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DiskCacheActivity.class);
                startActivity(intent);
            }
        });
    }
}
