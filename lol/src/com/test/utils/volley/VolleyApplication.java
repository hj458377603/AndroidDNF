package com.test.utils.volley;

import java.io.File;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.test.utils.img.MemoryCache;

public class VolleyApplication {
	// 请求队列
	private RequestQueue mRequestQueue;
	private static VolleyApplication instance;
	// 用于图片请求
	private ImageLoader mImageLoader;

	private VolleyApplication(){
		if (instance == null) {
			onCreate();
		}
	}
	// 使用单例模式
	public static VolleyApplication getInstance() {
		if (instance == null) {
			instance=new VolleyApplication();
		}
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public void onCreate() {
		// 初始化内存缓存目录
		File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "volley");
		/**
		 * 初始化RequestQueue,其实这里你可以使用Volley.newRequestQueue来创建一个RequestQueue,
		 * 直接使用构造函数可以定制我们需要的RequestQueue,比如线程池的大小等等
		 */
		mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir),
				new BasicNetwork(new HurlStack()), 3);

		instance = this;

		// 初始化图片内存缓存
		MemoryCache mCache = new MemoryCache();
		// 初始化ImageLoader
		mImageLoader = new ImageLoader(mRequestQueue, mCache);
		// 如果调用Volley.newRequestQueue,那么下面这句可以不用调用
		mRequestQueue.start();
	}
}
