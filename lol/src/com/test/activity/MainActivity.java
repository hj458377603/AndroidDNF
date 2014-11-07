package com.test.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.test.entity.news.NewsInfo;
import com.test.entity.news.NewsResult;
import com.test.lol.R;
import com.test.service.news.NewsService;
import com.test.utils.ICallback;
import com.test.utils.adapters.LazyAdapter;

public class MainActivity extends Activity implements ICallback<NewsResult>{
	private PullToRefreshListView listView;
	private boolean isLoading = false;
	private List<NewsInfo> newsList = null;
	private LazyAdapter adapter = null;
	private ProgressDialog dialog;
	/**
	 * 当前页码
	 */
	private int pageIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dialog=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
		dialog.show();
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				// 资讯列表刷新
				if (listView.isHeaderShown()) {
					Log.d("pull", "refresh");
					String label = DateUtils.formatDateTime(
							getApplicationContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					// 更新上次刷新时间
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
							label);
					pageIndex = 1;
					getNewsResult();
				} else if (listView.isFooterShown()) {
					// 加载更多
					pageIndex++;
					getNewsResult();
				}
			}
		});

		getNewsResult();
	}

	/**
	 * 获取资讯列表
	 */
	private void getNewsResult() {
		try {
			if (!isLoading) {
				isLoading = true;
				NewsService newsService = new NewsService();
				newsService.getNewsResult(this, new ICallback<VolleyError>() {
					@Override
					public void doCallback(VolleyError t) {
						Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
						listView.onRefreshComplete();
					}
				},pageIndex, this);
				isLoading = false;
			}
		} catch (Exception e) {
			Log.d("err", e.getMessage());
			Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
			listView.onRefreshComplete();
		}
	}

	@Override
	public void doCallback(NewsResult t) {
		// 如果是首页
		if (pageIndex == 1) {
			newsList = t.data;
			adapter = new LazyAdapter(this, newsList, listView);
			listView.setAdapter(adapter);
			dialog.hide();
		} else {
			newsList.addAll(t.data);
		}
		adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
}
