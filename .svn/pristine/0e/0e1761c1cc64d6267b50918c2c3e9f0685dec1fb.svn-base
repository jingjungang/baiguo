package com.mimi.baiguo.mydoctor;

/**
 * 知识详情
 * 景俊钢
 * 2016年5月25日 10:14:12
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.adapter.Knowledge_news_ViewAdapter;
import com.mimi.baiguo.adapter.ZhiShiListViewAdapter;
import com.mimi.baiguo.news.NewsInfosActivity;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

public class KnowledgeDetailsActivity extends Activity implements
		IXListViewListener, OnClickListener {
	private TextView tv_title;
	private XListView zhishi_lv;
	ZhiShiListViewAdapter ListViewadapter;
	String result;
	String ZHISHI_TITLELIST_URL = API.ZHISHI_TITLELIST_URL;
	String title_id = "", title_name = "咨询";
	List<Map<String, String>> Title;
	List<Map<String, String>> TitleList;
	Handler handler;
	Handler mHandler;
	int page = 1;
	ImageButton imgb_back;
	private static SystemBarTintManager tintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		setContentView(R.layout.activity_knowledge_details);
		if (null != getIntent().getExtras()) {
			title_id = getIntent().getExtras().getString("title_id");
			title_name = getIntent().getExtras().getString("title_name");
		}
		tv_title = (TextView) findViewById(R.id.tv_title);
		zhishi_lv = (XListView) findViewById(R.id.zhishi_lv);

		zhishi_lv.setPullLoadEnable(true);
		zhishi_lv.setXListViewListener(this);
		mHandler = new Handler();
		imgb_back = (ImageButton) findViewById(R.id.ib_left_handle01);
		imgb_back.setBackgroundResource(R.drawable.back_a_normal2x);
		imgb_back.setOnClickListener(this);
		// List列表点击事件
		zhishi_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KnowledgeDetailsActivity.this,
						NewsInfosActivity.class);
				intent.putExtra("id", TitleList.get(arg2 - 1).get("id"));
				startActivity(intent);
			}
		});

		tv_title.setText(title_name);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 根据标题ID获取的列表数据
				case 2:
					LoginActivity.stopProgressDialog();
					showListByResulttg(result);
					break;
				// 上拉加载的数据处理
				case 3:
					addListByResulttg(result);
					onLoadMore();
					break;
				}
			}
		};
		if (JudgeInternet.isNetworkAvailable(KnowledgeDetailsActivity.this)) {
			new TitleList().start();
			LoginActivity.startProgressDialog(KnowledgeDetailsActivity.this);
		}
	}

	/**
	 * 列表请求
	 */
	class TitleList extends Thread {
		@Override
		public void run() {
			try {
				String strUrl = ZHISHI_TITLELIST_URL + title_id + "/" + page;
				result = URLConnectionUtil.HttpClientGet(strUrl);
				Message m = new Message();
				m.what = 2;
				handler.sendMessage(m);// 发送消息到Handler
			} catch (Exception e) {
			}
			super.run();
		}
	}

	/**
	 * 根据返回结果json解析展示list
	 * 
	 * @param re
	 */
	public void showListByResulttg(String json) {
		try {
			JSONObject js = new JSONObject(json);
			String j = js.getString("info");
			js = new JSONObject(j);
			JSONArray array = js.getJSONArray("info");
			TitleList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject item = array.getJSONObject(i);
				String id = item.has("id") ? item.getString("id") : "";
				String title = item.has("title") ? item.getString("title") : "";
				String logo = item.has("newlogo") ? item.getString("newlogo")
						: "";
				map.put("id", id);
				map.put("title", title);
				map.put("logo", logo);
				TitleList.add(map);
			}
			ListViewadapter = new ZhiShiListViewAdapter(
					KnowledgeDetailsActivity.this, TitleList);
			zhishi_lv.setAdapter(ListViewadapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 上拉加载数据解析
	 * 
	 * @param re
	 */
	public void addListByResulttg(String json) {
		try {
			JSONObject js = new JSONObject(json);
			JSONObject j = js.getJSONObject("info");
			JSONArray array = j.getJSONArray("info");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject item = array.getJSONObject(i);
				String id = item.has("id") ? item.getString("id") : "";
				String title = item.has("title") ? item.getString("title") : "";
				String logo = item.has("newlogo") ? item.getString("newlogo")
						: "";
				map.put("id", id);
				map.put("title", title);
				map.put("logo", logo);
				TitleList.add(map);
			}
			ListViewadapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 下拉刷新 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	/** 上拉加载数据 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new LoadMore().start();
	}

	/** 加载完成 */
	private void onLoad() {
		zhishi_lv.stopRefresh();
		zhishi_lv.stopLoadMore();
		zhishi_lv.setRefreshTime("刚刚");
	}

	/** 加载数据请求 */
	class LoadMore extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String strUrl = ZHISHI_TITLELIST_URL + title_id + "/" + page;
				result = URLConnectionUtil.HttpClientGet(strUrl);
				page++;
				Message m = new Message();
				m.what = 3;
				// 发送消息到Handler
				handler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			KnowledgeDetailsActivity.this.finish();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_left_handle01:
			KnowledgeDetailsActivity.this.finish();
			break;
		}
	}
}
