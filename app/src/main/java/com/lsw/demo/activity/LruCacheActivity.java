package com.lsw.demo.activity;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;

import com.lsw.demo.R;

/**
 * LruCache的用法
 */
public class LruCacheActivity extends AppCompatActivity {

    private static final String TAG = "LruCacheActivity";
    private LruCache<String,Bitmap> mLruCache;
    private String key;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        key = String.valueOf(R.mipmap.ic_launcher);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        /*
        注意：默认是以缓存大小是以条目数量来衡量的，默认缓存大小int cacheSize = 4 * 1024 * 1024; // 4MiB ,可以通过下面的方式来设置缓存大小
        * */
        int maxCacheSize = (int)(Runtime.getRuntime().maxMemory()/1024)/8;//单位kb
        mLruCache = new LruCache<>(maxCacheSize);
        Button button = (Button)findViewById(R.id.test_lrucache_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getBitmapFromCache(key)==null){
                    addBitmapToCache(key,mBitmap);
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

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
