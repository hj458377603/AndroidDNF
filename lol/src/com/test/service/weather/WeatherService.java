package com.test.service.weather;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.test.entity.weather.Weather;
import com.test.entity.weather.WeatherInfo;
import com.test.service.http.GsonRequest;
import com.test.utils.ICallback;

public class WeatherService {

	public void getWeatherInfo(final ICallback<Weather> callback,
			Context context) {
		RequestQueue mQueue = Volley.newRequestQueue(context);
		GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
				"http://www.weather.com.cn/data/sk/101010100.html",
				Weather.class, new Response.Listener<Weather>() {
					@Override
					public void onResponse(Weather weather) {
						WeatherInfo weatherInfo = weather.getWeatherinfo();
						// callback
						if (callback != null) {
							callback.doCallback(weather);
						}
						Log.d("TAG", "city is " + weatherInfo.getCity());
						Log.d("TAG", "temp is " + weatherInfo.getTemp());
						Log.d("TAG", "time is " + weatherInfo.getTime());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		mQueue.add(gsonRequest);
	}
}
