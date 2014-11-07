package com.test.utils.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.test.entity.news.NewsInfo;
import com.test.lol.R;
import com.test.utils.volley.VolleyApplication;

public class LazyAdapter extends BaseAdapter{
	private Activity activity;
	private List<NewsInfo> data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, List<NewsInfo> d,
			PullToRefreshListView listView) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.news_item, null);

		TextView title = (TextView) vi.findViewById(R.id.title); // ±ÍÃ‚
		final NetworkImageView imageView = (NetworkImageView) vi
				.findViewById(R.id.pic); // Àı¬‘Õº

		NewsInfo news = data.get(position);
		title.setText(news.title);
		Log.d("getView", "position:" + position);

//		ImageLoader imageLoader = new ImageLoader(mQueue, new ImageCache() {
//			@Override
//			public void putBitmap(String url, Bitmap bitmap) {
//			}
//
//			@Override
//			public Bitmap getBitmap(String url) {
//				return null;
//			}
//		});
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		imageView.setDefaultImageResId(R.drawable.default_img);
		imageView.setErrorImageResId(R.drawable.ic_launcher);
		imageView.setImageUrl(news.pic_url, imageLoader);
		return vi;
	}

}
