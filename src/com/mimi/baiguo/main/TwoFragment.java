package com.mimi.baiguo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mimi.baiguo.R;
import com.mimi.baiguo.adapter.MessageAdapter;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class TwoFragment extends Fragment implements IXListViewListener {

	private TextView tv_title;
	private XListView mListView;
	private List<Map<String, Object>> list;
	private Handler mHandler;

	String[] title = { "综合功能评估", "偏瘫综合训练", "颈肩腰椎综合症康复", "中老年颈肩康复", "小儿脑瘫综合训练" };
	String[] money = { "￥168.0", "￥268.0", "￥231.0", "￥188.0", "￥399.0" };
	String[] time = { "45分钟", "60分钟", "45分钟", "50分钟", "30分钟" };
	int[] img = { R.drawable.msg1, R.drawable.msg2, R.drawable.msg3, R.drawable.msg4, R.drawable.msg5 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View friendView = inflater.inflate(R.layout.fragment_two, container, false);
		tv_title = (TextView) friendView.findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.tab_2));
		mListView = (XListView) friendView.findViewById(R.id.message_listView);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
			}
		});
		mHandler = new Handler();
		setList();
		return friendView;
	}

	private void setList() {
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title[i]);
			map.put("money", money[i]);
			map.put("time", time[i]);
			map.put("img", img[i]);
			list.add(map);
		}
		mListView.setAdapter(new MessageAdapter(getActivity(), list));
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

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

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();

			}
		}, 2000);
	}

}
