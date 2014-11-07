package com.test.utils.volley;

import java.io.File;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.test.utils.img.MemoryCache;

public class VolleyApplication {
	// �������
	private RequestQueue mRequestQueue;
	private static VolleyApplication instance;
	// ����ͼƬ����
	private ImageLoader mImageLoader;

	private VolleyApplication(){
		if (instance == null) {
			onCreate();
		}
	}
	// ʹ�õ���ģʽ
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
		// ��ʼ���ڴ滺��Ŀ¼
		File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "volley");
		/**
		 * ��ʼ��RequestQueue,��ʵ���������ʹ��Volley.newRequestQueue������һ��RequestQueue,
		 * ֱ��ʹ�ù��캯�����Զ���������Ҫ��RequestQueue,�����̳߳صĴ�С�ȵ�
		 */
		mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir),
				new BasicNetwork(new HurlStack()), 3);

		instance = this;

		// ��ʼ��ͼƬ�ڴ滺��
		MemoryCache mCache = new MemoryCache();
		// ��ʼ��ImageLoader
		mImageLoader = new ImageLoader(mRequestQueue, mCache);
		// �������Volley.newRequestQueue,��ô���������Բ��õ���
		mRequestQueue.start();
	}
}
