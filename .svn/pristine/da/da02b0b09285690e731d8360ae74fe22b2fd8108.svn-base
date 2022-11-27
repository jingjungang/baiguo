package com.mimi.baiguo.main;

/**
 * 知识
 * 景俊钢
 * 2016年5月25日 10:14:12
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.adapter.Knowledge_news_ViewAdapter;
import com.mimi.baiguo.adapter.ZhiShiGridViewAdapter;
import com.mimi.baiguo.mydoctor.KnowledgeDetailsActivity;
import com.mimi.baiguo.news.NewsInfosActivity;
import com.mimi.baiguo.util.CustomProgressDialog;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

public class OneFragment_new extends Fragment implements IXListViewListener,
		OnClickListener {
	CustomProgressDialog progressDialog;
	private TextView tv_title;
	private GridView zhishi_gv;
	private XListView zhishi_lv;
	ZhiShiGridViewAdapter GridViewadapter;
	Knowledge_news_ViewAdapter ListViewadapter;
	String result;
	String KNOWLEDGEDETALIS_HOT = API.KNOWLEDGEDETALIS_HOT;
	String TITLE_URL = API.ZHISHI_TITLE_URL;
	String TITLELIST_URL = API.ZHISHI_TITLELIST_URL;
	String title_id;
	List<Map<String, String>> Title;
	List<Map<String, String>> HotList;
	int page = 1;
	int selected = 0;
	Handler Ui_handler = new Handler();// 用于加载UI
	Handler handler; // 用于处理请求数据
	RelativeLayout rl_hot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View nurseRecordView = inflater.inflate(R.layout.fragment_one,
				container, false);
		progressDialog = CustomProgressDialog.createDialog(getActivity());
		progressDialog.setMessage("加载中···");

		tv_title = (TextView) nurseRecordView.findViewById(R.id.tv_title);
		zhishi_gv = (GridView) nurseRecordView.findViewById(R.id.zhishi_gv);
		zhishi_lv = (XListView) nurseRecordView.findViewById(R.id.zhishi_lv);
		rl_hot = (RelativeLayout) nurseRecordView.findViewById(R.id.rl_hot);
		rl_hot.setOnClickListener(this);
		Ui_handler = new Handler();
		zhishi_lv.setPullLoadEnable(true);
		zhishi_lv.setXListViewListener(this);
		if (JudgeInternet.isNetworkAvailable(getActivity())) {
			new Title().start();
			progressDialog.show();
		} else {
			Judement();
		}
		// title菜单点击事件
		zhishi_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (JudgeInternet.isNetworkAvailable(getActivity())) {
					selected = arg2;
					// HotList.clear();
					title_id = Title.get(arg2).get("id");
					// new HotList().start();
					// GridViewadapter.setSeclection(arg2);
					// GridViewadapter.notifyDataSetChanged();
					// ListViewadapter.notifyDataSetChanged();
					Intent i = new Intent(getActivity(),
							KnowledgeDetailsActivity.class);
					i.putExtra("title_id", title_id);
					i.putExtra("title_name", Title.get(arg2).get("name"));
					startActivity(i);
				} else {
					Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		// List列表点击事件
		zhishi_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						NewsInfosActivity.class);
				intent.putExtra("id", HotList.get(arg2 - 1).get("id"));
				startActivity(intent);
			}
		});
		tv_title.setText(R.string.tab_1);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 知识标题的数据处理
				case 1:
					try {
						JSONObject json = new JSONObject(result);
						JSONArray js = json.getJSONArray("info");
						Title = new ArrayList<Map<String, String>>();
						for (int i = 0; i < js.length(); i++) {
							Map<String, String> map = new HashMap<String, String>();
							JSONObject item = js.getJSONObject(i);
							String id = item.has("id") ? item.getString("id")
									: "";
							String name = item.has("name") ? item
									.getString("name") : "";
							String lable = item.has("lable") ? item
									.getString("lable") : "";
							map.put("id", id);
							map.put("name", name);
							map.put("lable", lable);
							Title.add(map);
						}
						title_id = Title.get(0).get("id");
						zhishi_gv.setNumColumns(2);// Title.size()
						GridViewadapter = new ZhiShiGridViewAdapter(
								getActivity(), Title);
						GridViewadapter.setSeclection(-1);
						zhishi_gv.setAdapter(GridViewadapter);
						new getHotList().start();
					} catch (JSONException e) {
					}
					break;
				// 根据标题ID获取的列表数据
				case 2:
					progressDialog.dismiss();
					showListByResulttg(result);
					break;
				// 上拉加载的数据处理
				case 3:
					addListByResulttg(result);
					// onLoadMore();
					break;
				}
			}
		};
		return nurseRecordView;
	}

	/**
	 * 网络不好的情况下默认2个
	 */
	protected void Judement() {
		// TODO Auto-generated method stub
		if (Title.size() > 0) {
			title_id = Title.get(selected).get("id");
			zhishi_gv.setNumColumns(2);// Title.size()
			GridViewadapter = new ZhiShiGridViewAdapter(getActivity(), Title);
			GridViewadapter.setSeclection(selected);
			zhishi_gv.setAdapter(GridViewadapter);
			new getHotList().start();
		}
	}

	/**
	 * 标题
	 */
	class Title extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", "1"));
			try {
				result = URLConnectionUtil
						.HttpClientPost(TITLE_URL, parameters);
				Message m = new Message();
				m.what = 1;
				// 发送消息到Handler
				handler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}

	/**
	 * 列表
	 */
	class getHotList extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			HotList = new ArrayList<Map<String, String>>();
			page = 1;
			parameters
					.add(new BasicNameValuePair("page", String.valueOf(page)));
			try {
				result = URLConnectionUtil.HttpClientPost(KNOWLEDGEDETALIS_HOT,
						parameters);
				Message m = new Message();
				m.what = 2;
				handler.sendMessage(m);
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
			JSONArray array = js.getJSONArray("info");
			HotList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject item = array.getJSONObject(i);
				String id = item.has("id") ? item.getString("id") : "";
				String title = item.has("title") ? item.getString("title") : "";
				String logo = item.has("newlogo") ? item.getString("newlogo")
						: "";
				String updatetime = item.has("updatetime") ? item
						.getString("updatetime") : "";
				map.put("id", id);
				map.put("title", title);
				map.put("logo", logo);
				map.put("updatetime", updatetime);
				HotList.add(map);
			}
			ListViewadapter = new Knowledge_news_ViewAdapter(getActivity(),
					HotList);
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
			JSONArray array = js.getJSONArray("info");
			if (array.length() == 0) {
				if (page != 1) {
					page--;
				}
				return;
			}
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
				HotList.add(map);
			}
			ListViewadapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class LoadMore extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			page++;
			parameters
					.add(new BasicNameValuePair("page", String.valueOf(page)));
			try {
				result = URLConnectionUtil.HttpClientPost(KNOWLEDGEDETALIS_HOT,
						parameters);
				Message m = new Message();
				m.what = 3;
				handler.sendMessage(m);
				// onLoad();
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.run();
		}
	}

	/** 下拉刷新 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		// onLoad();
		// new getHotList().start();
		Ui_handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				new getHotList().start();
				onLoad();
			}
		}, 2000);
	}

	/** 上拉加载数据 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new LoadMore().start();
		onLoad();
	}

	/** 加载完成 */
	private void onLoad() {
		zhishi_lv.stopRefresh();
		zhishi_lv.stopLoadMore();
		zhishi_lv.setRefreshTime("刚刚");
	}

	@Override
	public void onClick(View view_clicked) {
		// TODO Auto-generated method stub
		switch (view_clicked.getId()) {
		case R.id.rl_hot:
			BackHead();
			break;
		}
	}

	/**
	 * 双击函数
	 * 
	 */
	private static Boolean isBackHead = false;

	private void BackHead() {
		Timer tExit = null;
		if (isBackHead == false) {
			isBackHead = true;
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isBackHead = false;
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else if (HotList.size() > 0) {
			zhishi_lv.setSelection(0);
		}
	}
}
