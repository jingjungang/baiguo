package com.mimi.baiguo.mydoctor;

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
/**
 * 关注
 * 景俊钢
 * 2016年4月26日 17:24:23
 */
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.adapter.FocusAdapter;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 我的医生-关注 2016年5月23日 14:08:29
 * 
 * @author Administrator
 * 
 */
public class FocusFragment extends Fragment implements IXListViewListener {

	private XListView focus_listView;
	private List<Map<String, String>> list;
	Handler handler;
	String result;
	FocusAdapter adapter;
	int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View friendView = inflater.inflate(R.layout.focus, container, false);
		handler = new Handler();
		focus_listView = (XListView) friendView
				.findViewById(R.id.focus_listView);
		focus_listView.setPullLoadEnable(true);
		focus_listView.setXListViewListener(this);
		LoginActivity.startProgressDialog(getActivity());

		list = new ArrayList<Map<String, String>>();
		adapter = new FocusAdapter(getActivity(), list);
		focus_listView.setAdapter(adapter);

		getResult();
		return friendView;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				LoginActivity.stopProgressDialog();
				showListByResulttg(result);
			}
		};
	};

	private void getResult() {

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("token", API.token));
				parameters.add(new BasicNameValuePair("page", String
						.valueOf(page)));
				parameters.add(new BasicNameValuePair("pagesize", String
						.valueOf("50")));
				result = URLConnectionUtil.HttpClientPost(API.ATTENTION_URL,
						parameters);
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				super.run();
			}
		}.start();
	}

	private void showListByResulttg(String result) {
		try {
			LoginActivity.stopProgressDialog();
			JSONObject json = new JSONObject(result);
			String temp = json.getString("info");
			json = new JSONObject(temp);
			JSONArray array = json.getJSONArray("info");
			if (array.length() < 0 && page > 1) {
				page = page - 1;
			} else {
				// list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < array.length(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject js = array.getJSONObject(i);
					String userid = js.has("userid") ? js.getString("userid")
							: "";
					String username = js.has("username") ? js
							.getString("username") : "";
					String nickname = js.has("nickname") ? js
							.getString("nickname") : "";
					String job = js.has("job") ? js.getString("job") : "";
					String hospital = js.has("hospital") ? js
							.getString("hospital") : "";
					String subject = js.has("subject") ? js
							.getString("subject") : "";
					String avatar = js.has("avatar") ? js.getString("avatar")
							: "";
					map.put("userid", userid);
					map.put("username", username);
					map.put("nickname", nickname);
					map.put("job", job);
					map.put("hospital", hospital);
					map.put("subject", subject);
					map.put("avatar", avatar);
					if (!list.contains(map)) {// 重复添加
						list.add(map);
					}
				}
				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postAtTime(new Runnable() {
			public void run() {
				page = 1;
				list = new ArrayList<Map<String, String>>();
				getResult();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		LoginActivity.startProgressDialog(getActivity());
		page++;
		getResult();
		onLoad();
	}

	private void onLoad() {
		focus_listView.stopRefresh();
		focus_listView.stopLoadMore();
		focus_listView.setRefreshTime("刚刚");
	}

}
