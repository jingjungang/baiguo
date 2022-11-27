package com.mimi.baiguo.mydoctor;

/**
 * 互动Fragment
 * 景俊钢
 * 2016年4月26日 17:24:23
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.BaiGuoApplication;
import com.mimi.baiguo.R;
import com.mimi.baiguo.adapter.InteractionMessageAdapter;
import com.mimi.baiguo.util.CustomProgressDialog;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.util.XThread;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

public class InteractionFragment extends Fragment implements IXListViewListener {
	String url = API.DOC_HELP_URL; // 请求路径
	String result = ""; // 返回结果
	BaiGuoApplication mapp;
	Context mContent;
	XThread rThread;
	XListView lv;
	View root;
	String oldDate = "";
	int page = 1;
	InteractionMessageAdapter adapter;
	JSONArray js = new JSONArray();
	int DefaultpageSize = 10;// 默认加载10条数据

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_help, container, false);
		mapp = (BaiGuoApplication) getActivity().getApplication();
		mContent = getActivity();

		lv = (XListView) root.findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);

		// 获取数据
		if (JudgeInternet.isNetworkAvailable(mContent)) {
			startProgressDialog(getActivity());
			new MThread().start();
		}
		return root;
	}

	class MThread extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("pid", API.UserId));
			parameters
					.add(new BasicNameValuePair("page", String.valueOf(page)));
			parameters.add(new BasicNameValuePair("pagesize", String
					.valueOf(DefaultpageSize)));
			try {
				result = URLConnectionUtil.HttpClientPost(url, parameters);
				Message m = new Message();
				m.what = 1;
				// 发送消息到Handler
				mHandler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				stopProgressDialog();
				if (paramAnonymousMessage.what == 1) {
					JSONObject json = new JSONObject(result);
					// 考虑page 通知
					String status = json.getString("status").toString();
					if (status.equals("1")) {
						JSONArray js1 = json.getJSONArray("info");
						if (page == 1) {
							js = new JSONArray();
						}
						for (int i = 0; i < js1.length(); i++) {
							js.put(js1.get(i));
						}
						adapter = new InteractionMessageAdapter(mContent, js);
						lv.setAdapter(adapter);
						// adapter.notifyDataSetChanged();
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						oldDate = format.format(date);
						page++;
					} else if (status.equals("0")) {// 未获取到数据

					}
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
				Toast.makeText(mContent, "获取数据异常！", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(oldDate);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
				page = 1;
				startProgressDialog(getActivity());
				new MThread().start();
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
				startProgressDialog(getActivity());
				new MThread().start();
			}
		}, 2000);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter = new InteractionMessageAdapter(mContent, js);
		lv.setAdapter(adapter);
	}

	private static CustomProgressDialog progressDialog = null;

	public static void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			progressDialog.setMessage("加载中···");
		}

		progressDialog.show();
	}

	public static void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
