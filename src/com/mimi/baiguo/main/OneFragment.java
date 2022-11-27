package com.mimi.baiguo.main;
/**
 * 知识
 * 景俊钢
 * 2016年5月4日 09:42:33
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.adapter.ZhiShiGridViewAdapter;
import com.mimi.baiguo.adapter.ZhiShiListViewAdapter;
import com.mimi.baiguo.news.NewsInfosActivity;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author AAA
 *
 */
public class OneFragment extends Fragment implements IXListViewListener{
	private TextView tv_title;
	private GridView zhishi_gv;
	private XListView zhishi_lv;
	ZhiShiGridViewAdapter GridViewadapter;
	ZhiShiListViewAdapter ListViewadapter;
	String result;
	String TITLE_URL = API.ZHISHI_TITLE_URL;
	String TITLELIST_URL = API.ZHISHI_TITLELIST_URL;
	String title_id;
	List<Map<String,String>> Title;
	List<Map<String,String>> TitleList;
	Handler handler;
	Handler mHandler;
	int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View nurseRecordView = inflater.inflate(R.layout.fragment_one, container, false);

		tv_title = (TextView) nurseRecordView.findViewById(R.id.tv_title);
		zhishi_gv = (GridView) nurseRecordView.findViewById(R.id.zhishi_gv);
		zhishi_lv = (XListView) nurseRecordView.findViewById(R.id.zhishi_lv);
		zhishi_lv.setPullLoadEnable(true);
		zhishi_lv.setXListViewListener(this);
		mHandler = new Handler();
		if(JudgeInternet.isNetworkAvailable(getActivity())){
			new Title().start();
			LoginActivity.startProgressDialog(getActivity());
		}
		//title菜单点击事件
		zhishi_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(JudgeInternet.isNetworkAvailable(getActivity())){
					TitleList.clear();
					title_id = Title.get(arg2).get("id");
					LoginActivity.startProgressDialog(getActivity());
					new TitleList().start();
					GridViewadapter.setSeclection(arg2);
					GridViewadapter.notifyDataSetChanged();
					ListViewadapter.notifyDataSetChanged();
				}
			}
		});
		//List列表点击事件
		zhishi_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NewsInfosActivity.class);
				intent.putExtra("id", TitleList.get(arg2-1).get("id"));
				startActivity(intent);
			}
		});

		tv_title.setText(R.string.tab_1);
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				//知识标题的数据处理
				case 1:
					try {
						JSONObject json = new JSONObject(result);
						JSONArray js = json.getJSONArray("info");
						Title = new ArrayList<Map<String,String>>();
						for (int i = 0; i < js.length(); i++) {
							Map<String,String> map = new HashMap<String,String>();
							JSONObject item = js.getJSONObject(i);
							String id = item.has("id") ? item.getString("id") : "";
							String name = item.has("name") ? item.getString("name") : "";
							map.put("id", id);
							map.put("name", name);
							Title.add(map);
						}
						title_id = Title.get(0).get("id");
						zhishi_gv.setNumColumns(Title.size());
						GridViewadapter = new ZhiShiGridViewAdapter(getActivity(), Title);
						GridViewadapter.setSeclection(0);
						zhishi_gv.setAdapter(GridViewadapter);
						new TitleList().start();
					} catch (JSONException e) {
					}
					break;
					//根据标题ID获取的列表数据
				case 2:
					LoginActivity.stopProgressDialog();
					showListByResulttg(result);
					break;
					//上拉加载的数据处理
				case 3:
					addListByResulttg(result);
					onLoadMore();
					break;
				}
			}
		};
		return nurseRecordView;
	}

	/**
	 * 知识标题请求
	 */
	class Title extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", "1"));
			try {
				result = URLConnectionUtil.HttpClientPost(TITLE_URL, parameters);
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
	 * 知识标题列表请求
	 */
	class TitleList extends Thread {
		@Override
		public void run() {
			// 请求网络
			String strUrl;
			try {
				strUrl = TITLELIST_URL+title_id+"/"+page;
				result = URLConnectionUtil.HttpClientGet(strUrl);
				Message m = new Message();
				m.what = 2;
				// 发送消息到Handler
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
			JSONObject j = js.getJSONObject("info");
			JSONArray array = j.getJSONArray("info");
			TitleList = new ArrayList<Map<String,String>>();
			for (int i = 0; i < array.length(); i++) {
				Map<String,String> map = new HashMap<String,String>();
				JSONObject item = array.getJSONObject(i);
				String id = item.has("id")?item.getString("id"):"";
				String title = item.has("title")?item.getString("title"):"";
				String logo = item.has("newlogo")?item.getString("newlogo"):"";
				map.put("id", id);
				map.put("title", title);
				map.put("logo", logo);
				TitleList.add(map);
			}
			ListViewadapter = new ZhiShiListViewAdapter(getActivity(), TitleList);
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
				Map<String,String> map = new HashMap<String,String>();
				JSONObject item = array.getJSONObject(i);
				String id = item.has("id")?item.getString("id"):"";
				String title = item.has("title")?item.getString("title"):"";
				String logo = item.has("newlogo")?item.getString("newlogo"):"";
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
	/**下拉刷新*/
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
	/**上拉加载数据*/
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new LoadMore().start();
	}
	/**加载完成*/
	private void onLoad() {
		zhishi_lv.stopRefresh();
		zhishi_lv.stopLoadMore();
		zhishi_lv.setRefreshTime("刚刚");
	}
	/**加载数据请求*/
	class LoadMore extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String strUrl;
			try {
				strUrl = TITLELIST_URL + title_id + "/" + ++page;
				result = URLConnectionUtil.HttpClientGet(strUrl);
				page ++;
				Message m = new Message();
				m.what = 3;
				// 发送消息到Handler
				handler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}
}
