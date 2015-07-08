package com.frojas.francisco.hilos;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.frojas.francisco.activities.MyApplication;

/**
 * Created by Francisco on 24/03/2015.
 */
public class VolleySingleton {

    private static VolleySingleton instanciaVolley =null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader=new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
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
    public static VolleySingleton getInstance(){
        if(instanciaVolley ==null)
        {
            instanciaVolley =new VolleySingleton();
        }
        return instanciaVolley;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
