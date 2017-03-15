package com.lsw.demo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;

/**
 * LruCache的用法
 */
public class LruCacheActivity extends AppCompatActivity {

    private static final String TAG = "LruCacheActivity";
    private LruCache<String,Bitmap> mLruCache;
    private String key;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        key = String.valueOf(R.mipmap.ic_launcher);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        int maxCacheSize = (int)(Runtime.getRuntime().maxMemory()/1024)/8;//单位kb
        mLruCache = new LruCache<>(maxCacheSize);
        Button button = (Button)findViewById(R.id.test_lrucache_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getBitmapFromCache(key)==null){
                    addBitmapToCache(key,bitmap);
                    Log.d(TAG, "onCreate: 缓存中没有bitmap");
                }else{
                    Log.d(TAG, "onCreate: 从缓存中读取bitmap");
                }
            }
        });
    }

    /**
     * 从缓存中取位图
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 将位图存在缓存中
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mLruCache.put(key, bitmap);
        }
    }
}
