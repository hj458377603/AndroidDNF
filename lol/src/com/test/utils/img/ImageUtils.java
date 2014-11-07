package com.test.utils.img;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.test.lol.R;
import com.test.utils.volley.VolleyApplication;

public class ImageUtils {
	private static final String TAG = "ImageUtils";

	public static void loadImage(final String url,
			final PullToRefreshListView listView) {
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		ImageListener listener = new ImageListener() {
			NetworkImageView tmpImg = (NetworkImageView) listView
					.findViewWithTag(url);

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// ���������˵��������ʾ���򵥴��������׼��һ�ų���ͼƬ
				//tmpImg.setImageBitmap(null);
				tmpImg.setImageResource(R.drawable.default_img);
			}

			@Override
			public void onResponse(ImageContainer container, boolean arg1) {

				if (container != null) {
					tmpImg = (NetworkImageView) listView.findViewWithTag(url);
					if (tmpImg != null) {
						if (container.getBitmap() == null) {
							tmpImg.setImageResource(R.drawable.default_img);
						} else {
							tmpImg.setImageBitmap(container.getBitmap());
						}
					}
				}
			}
		};
		ImageContainer newContainer = imageLoader.get(url, listener, 128, 128);
	}

	/**
	 * ȡ��ͼƬ����
	 */
	public static void cancelAllImageRequests() {		
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		RequestQueue requestQueue = VolleyApplication.getInstance()
				.getRequestQueue();
		if (imageLoader != null && requestQueue != null) {
			// int num = requestQueue.getSequenceNumber();
			// ������������Լ�д�ģ�Volley������û�еģ�����ֻ��ʹ���Ҹ���Volley.jar�����������
			// imageLoader.drain(num);
			// requestQueue.cancelAll(null);
		}
	}

}