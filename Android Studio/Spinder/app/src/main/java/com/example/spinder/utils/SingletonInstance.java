package com.example.spinder.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class SingletonInstance {
    private static SingletonInstance mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    public static final String TAG = SingletonInstance.class.getSimpleName();

    private SingletonInstance(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {

                    // Get max available VM memory, exceeding this amount will throw an
                    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
                    // int in its constructor.
                    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

                    // Use 1/8th of the available memory for this memory cache.
                    final int cacheSize = maxMemory / 8;

                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(cacheSize){
                        @Override
                        protected int sizeOf(String key, Bitmap bitmap) {
                            // The cache size will be measured in kilobytes rather than
                            // number of items.
                            return bitmap.getByteCount() / 1024;
                        }
                    };

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized SingletonInstance getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonInstance(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy( 50000, 5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TAG);
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return  mImageLoader;
    }
}
