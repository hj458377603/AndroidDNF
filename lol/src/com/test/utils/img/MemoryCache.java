package com.test.utils.img;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MemoryCache implements ImageCache   
{  
  private static final String TAG = "MemoryCache";  
  private LruCache<String, Bitmap> mCache;  
    
  public MemoryCache()  
  {  
    //这个取单个应用最大使用内存的1/8  
    int maxSize=(int)Runtime.getRuntime().maxMemory()/8;  
    mCache=new LruCache<String, Bitmap>(maxSize){  
      @Override  
      protected int sizeOf(String key, Bitmap value) {  
      //这个方法一定要重写，不然缓存没有效果  
        return value.getHeight()*value.getRowBytes();  
      }  
    };  
  }  
  
  @Override  
  public Bitmap getBitmap(String key) {  
    return mCache.get(key);  
  }  
  
  @Override  
  public void putBitmap(String key, Bitmap value) {  
    mCache.put(key, value);  
  }  
}  
