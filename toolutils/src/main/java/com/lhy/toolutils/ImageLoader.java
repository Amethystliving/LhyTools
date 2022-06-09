package com.lhy.toolutils;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.lhy.toolutils.callback.LoadBitmapCallback;


public class ImageLoader {
    private static ImageLoader imageLoader;
    private LruCache lruCache;

    private Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = (Bitmap) lruCache.get(key);
        return bitmap;
    }

    private void setBitmapToCache(String key, Bitmap value) {
        lruCache.put(key, value);
    }

    public void displayImage(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            //执行下载图片并添加到缓存
            loadBitmap(imageView, url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void loadBitmap(final ImageView imageView, final String url) {
        LoadBitmapTask loadBitmapTask = new LoadBitmapTask(new LoadBitmapCallback() {
            @Override
            public void bitmapLoaded(Bitmap bitmap) {
                setBitmapToCache(url, bitmap);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void bitmapLoadBefore() {
                imageView.setImageResource(R.drawable.ic_launcher);
            }
        });
        loadBitmapTask.execute(url);
    }

    private ImageLoader() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache(maxSize);
    }

    public synchronized static ImageLoader getInstance() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoader();
                }
            }
        }
        return imageLoader;
    }
}
