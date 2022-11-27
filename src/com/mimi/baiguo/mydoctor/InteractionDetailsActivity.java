package com.mimi.baiguo.mydoctor;

/**
 * 互动详情
 * 景俊钢
 * 2016年4月28日14:01:51
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.adapter.QADetailsAdapter;
import com.mimi.baiguo.util.CustomProgressDialog;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.xlistview.XListView;
import com.mimi.baiguo.xlistview.XListView.IXListViewListener;

public class InteractionDetailsActivity extends Activity implements
		OnClickListener, IXListViewListener {
	String url = API.HELP_DETAIL_URL, // 请求路径URL
			url1 = API.HELP_DETAIL_ADD_URL; // 追加提问URL
	String result = "", result1 = ""; // 返回结果
	public static String TITLE = "title";
	public static String DESCRIPT = "descript";
	public static String QA = "reply_answer";
	public static String DID = "did";

	public static String DOC_NAME = "DOC_NAME";
	public static String DOC_JOB = "DOC_JOB";
	public static String DOC_HOSPITAL = "DOC_HOSPITAL";

	String did = "", doc_name = "", doc_job = "", doc_hospital = "";

	String Title = "", // 标题
			Descript = "", // 4个描述
			_QA = "";// 回复和提问
	JSONArray JArray = new JSONArray();
	QADetailsAdapter adapter;
	XListView lv;
	TextView tv_doc;
	private static SystemBarTintManager tintManager;
	int DefaultpageSize = 10;// 默认加载10条数据

	int page = 1, size = 10; // 加载页和每页数量
	JSONArray js = new JSONArray();
	String oldDate = "";
	// view
	TextView tv_mtitle, tv_descript, tv_answer;
	Button btn_comment; // 提问
	EditText et_comment;// 提问内容
	LinearLayout l_body;
	LinearLayout l_reply;
	ImageButton ib_show_grids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		setContentView(R.layout.activity_interaction_details);
		try {
			Intent i = getIntent();
			did = i.getStringExtra("did");
			doc_name = i.getStringExtra(DOC_NAME);
			doc_job = i.getStringExtra(DOC_JOB);
			doc_hospital = i.getStringExtra(DOC_HOSPITAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Init();
		startProgressDialog(this);
		new MThread().start();
	}

	private void Init() {
		ib_show_grids = (ImageButton) findViewById(R.id.ib_show_grids);
		l_reply = (LinearLayout) findViewById(R.id.l_reply);
		l_body = (LinearLayout) findViewById(R.id.l_body);
		tv_doc = (TextView) findViewById(R.id.doc);
		String temp = doc_name + " " + doc_job + " " + doc_hospital;
		temp = temp.length() > 25 ? temp.substring(0, 25) + "..." : temp;
		tv_doc.setText(temp);
		if (TextUtils.isEmpty(temp.trim())) {
			l_body.setVisibility(View.GONE);
		} else {
			tv_doc.setOnClickListener(this);
		}
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(R.string.interaction_details);
		ImageButton ibtn_back = (ImageButton) findViewById(R.id.ib_left_handle01);
		ibtn_back.setBackgroundResource(R.drawable.back_a_normal2x);
		ibtn_back.setOnClickListener(this);
		lv = (XListView) findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		tv_mtitle = (TextView) findViewById(R.id.mtitle);
		tv_mtitle.setOnClickListener(this);
		tv_descript = (TextView) findViewById(R.id.descript);
		tv_answer = (TextView) findViewById(R.id.answer);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		btn_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
					startProgressDialog(InteractionDetailsActivity.this);
					new MThread1().start();
				} else {
					Toast.makeText(InteractionDetailsActivity.this,
							API.NO_DATA_EDIT, Toast.LENGTH_SHORT).show();
				}
			}
		});
		et_comment = (EditText) findViewById(R.id.et_comment);
	}

	// 追加列表
	class MThread extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("did", did));
			parameters
					.add(new BasicNameValuePair("page", String.valueOf(page)));
			parameters
					.add(new BasicNameValuePair("pagesize", String.valueOf(size)));
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

	// 追加列表_all
	class MThread_all extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("did", did));
			parameters
					.add(new BasicNameValuePair("page", String.valueOf(page)));
			parameters.add(new BasicNameValuePair("pagesize", String
					.valueOf(2147483647)));// 2^31 - 1
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

	// 追加
	class MThread1 extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("did", did));
			parameters.add(new BasicNameValuePair("content", et_comment
					.getText().toString()));
			try {
				result1 = URLConnectionUtil.HttpClientPost(url1, parameters);
				Message m = new Message();
				m.what = 2;
				// 发送消息到Handler
				mHandler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_left_handle01:
			InteractionDetailsActivity.this.finish();
			break;
		case R.id.doc:
			Intent intent = new Intent(InteractionDetailsActivity.this,
					DoctorDetailActivity.class);
			Bundle b = new Bundle();
			b.putString("id", did);
			intent.putExtras(b);
			startActivity(intent);
			break;
		case R.id.mtitle:
			int i = tv_descript.getVisibility();
			if (i == View.VISIBLE) {
				ib_show_grids.setImageDrawable(getResources().getDrawable(
						R.drawable.open_android));
				tv_descript.setVisibility(View.GONE);
				l_reply.setVisibility(View.GONE);
			} else {
				ib_show_grids.setImageDrawable(getResources().getDrawable(
						R.drawable.close_android));
				tv_descript.setVisibility(View.VISIBLE);
				l_reply.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				stopProgressDialog();
				if (paramAnonymousMessage.what == 1) {
					JSONObject json = new JSONObject(result);
					String status = json.getString("status").toString();
					if (status.equals("1")) {
						String str = json.getString("info");
						json = new JSONObject(str);
						String temp_relist = json.getString("relist");
						if (!TextUtils.isEmpty(temp_relist)) {
							JSONArray JA = new JSONArray(temp_relist);
							if (JA != null && JA.length() > 0) {
								for (int i = 0; i < JA.length(); i++) {
									JArray.put(JA.getJSONObject(i));
								}
							}
							adapter = new QADetailsAdapter(
									InteractionDetailsActivity.this, JArray);
							lv.setAdapter(adapter);
							lv.setSelection(JArray.length() - 1);
						} else if (page != 1) {
							page--;
						}
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						oldDate = format.format(date);
					} else if (status.equals("0")) {// 未获取到数据
						Toast.makeText(InteractionDetailsActivity.this,
								API.NO_MORE_DATA, Toast.LENGTH_SHORT).show();
					}
				} else if (paramAnonymousMessage.what == 2) {
					JSONObject json = new JSONObject(result1);
					String status = json.getString("status").toString();
					if (status.equals("1")) {
						Toast.makeText(InteractionDetailsActivity.this, "提交完成",
								Toast.LENGTH_SHORT).show();
						et_comment.setText("");
						JArray = new JSONArray();
						page = 1;
						new MThread_all().start();
					} else {
						Toast.makeText(InteractionDetailsActivity.this, "提交失败",
								Toast.LENGTH_SHORT).show();
					}
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
				Toast.makeText(InteractionDetailsActivity.this, "获取数据异常！",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime("");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
				page = 1;
				JArray = new JSONArray();
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
				page++;
				new MThread().start();
			}
		}, 2000);
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
