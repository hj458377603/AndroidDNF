package com.test.service.news;

import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.test.entity.news.NewsResult;
import com.test.service.config.ServiceConfig;
import com.test.service.http.GsonRequest;
import com.test.utils.ICallback;

public class NewsService {
	public void getNewsResult(final ICallback<NewsResult> successCallback,
			final ICallback<VolleyError> errorCallback, int pageIndex,
			Context context) {
		RequestQueue mQueue = Volley.newRequestQueue(context);

		String url = ServiceConfig.NewsListServiceUrl;
		long time = new Date().getTime();
		long p = 2345L + 3L * (time % 10000L);
		url = String.format(url, pageIndex, time, p);

		GsonRequest<NewsResult> gsonRequest = new GsonRequest<NewsResult>(url,
				NewsResult.class, new Response.Listener<NewsResult>() {
					@Override
					public void onResponse(NewsResult newsResult) {
						// callback UIÖ÷Ïß³Ì
						if (successCallback != null) {
							successCallback.doCallback(newsResult);
						}
						Log.d("TAG", "code is " + newsResult.code);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (successCallback != null) {
							errorCallback.doCallback(error);
						}
					}
				});
		mQueue.add(gsonRequest);
	}
}
