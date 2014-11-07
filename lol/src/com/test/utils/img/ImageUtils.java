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
				// 如果出错，则说明都不显示（简单处理），最好准备一张出错图片
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
	 * 取消图片请求
	 */
	public static void cancelAllImageRequests() {		
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		RequestQueue requestQueue = VolleyApplication.getInstance()
				.getRequestQueue();
		if (imageLoader != null && requestQueue != null) {
			// int num = requestQueue.getSequenceNumber();
			// 这个方法是我自己写的，Volley里面是没有的，所以只能使用我给的Volley.jar才有这个函数
			// imageLoader.drain(num);
			// requestQueue.cancelAll(null);
		}
	}

}