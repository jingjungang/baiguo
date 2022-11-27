package com.mimi.baiguo.mydoctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorDetailActivity extends Activity implements OnClickListener {

	String Url = API.DOCTOR_INFO_URL;
	String result;
	String id;
	String token;
	String contentUrl;
	Intent intent;
	ImageLoader imageLoader;

	private ImageView chitchat_btn;
	private ImageView iv_detail_thumb;
	private TextView tv_detail_name, tv_detail_job, tv_detail_hospital,
			doctor_content, doctor_hospital, doctor_techang, tv_title;
	private ImageButton ib_left_handle01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		setContentView(R.layout.activity_doctordetail);
		init();
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		id = b.getString("id");
		if (JudgeInternet.isNetworkAvailable(this)
				&& !API.getYouke().equals("游客账号")) {
			LoginActivity.startProgressDialog(this);
			showList();
		}
		super.onCreate(savedInstanceState);
	}

	private void init() {
		chitchat_btn = (ImageView) findViewById(R.id.chitchat_btn);
		iv_detail_thumb = (ImageView) findViewById(R.id.iv_detail_thumb);
		tv_detail_name = (TextView) findViewById(R.id.tv_detail_name);
		tv_detail_job = (TextView) findViewById(R.id.tv_detail_job);
		tv_detail_hospital = (TextView) findViewById(R.id.tv_detail_hospital);
		doctor_content = (TextView) findViewById(R.id.doctor_content);
		doctor_hospital = (TextView) findViewById(R.id.doctor_hospital);
		doctor_techang = (TextView) findViewById(R.id.doctor_techang);
		tv_title = (TextView) findViewById(R.id.tv_title);
		ib_left_handle01 = (ImageButton) findViewById(R.id.ib_left_handle01);

		tv_title.setText(R.string.tab_3);
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		iv_detail_thumb.setImageResource(R.drawable.icon_man);
		chitchat_btn.setOnClickListener(this);
		ib_left_handle01.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.chitchat_btn:
			Intent intent = new Intent(DoctorDetailActivity.this,
					NetworkConsultingActivity.class);
			Bundle b = new Bundle();
			b.putString("id", id);
			intent.putExtras(b);
			startActivity(intent);
			break;
		case R.id.ib_left_handle01:
			finish();
			break;
		}
	}

	/**
	 * 开启线程访问网络得到数据更行UI
	 */
	private void showList() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					LoginActivity.stopProgressDialog();
					showListByResulttg(result);
					break;
				}
			}
		};
		// 启动线程来执行任务
		new Thread() {
			public void run() {
				// 请求网络
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("token", API.getToken()));
				parameters.add(new BasicNameValuePair("did", id));
				parameters.add(new BasicNameValuePair("pid", API.UserId));
				result = URLConnectionUtil.HttpClientPost(Url, parameters);
				Message m = new Message();
				m.what = 1;
				// 发送消息到Handler
				handler.sendMessage(m);
			}
		}.start();
	}

	/**
	 * 根据返回结果json解析展示list
	 * 
	 * @param re
	 */
	public void showListByResulttg(String json) {
		JSONObject o = null;
		try {
			o = new JSONObject(json);
			JSONObject oo = o.getJSONObject("info");
			// id = oo.has("yname") ? oo.getString("yname") : "";
			String username = oo.has("username") ? oo.getString("username")
					: "";
			String nickname = oo.has("nickname") ? oo.getString("nickname")
					: "";
			String avatar = oo.has("avatar") ? oo.getString("avatar") : "";
			String job = oo.has("job") ? oo.getString("job") : "";
			String hospital = oo.has("hospital") ? oo.getString("hospital")
					: "";
			String subject = oo.has("subject") ? oo.getString("subject") : "";
			String description = oo.has("description") ? oo
					.getString("description") : "";
			String jobplace = oo.has("jobplace") ? oo.getString("jobplace")
					: "";
			String jobgood = oo.has("jobgood") ? oo.getString("jobgood") : "";
			if (nickname != null && !nickname.equals("")) {
				tv_detail_name.setText(nickname);
				API.setDoctorName(nickname);
			} else {
				tv_detail_name.setText(username);
				API.setDoctorName(username);
			}
			tv_detail_hospital.setText(hospital);
			if (!TextUtils.isEmpty(job)) {
				switch (Integer.valueOf(job)) {
				case 1:
					job = "住院医师";
					break;
				case 2:
					job = "主治医师";
					break;
				case 3:
					job = "副主任医师";
					break;
				case 4:
					job = "主任医师";
					break;
				}
			}
			tv_detail_job.setText(subject + "\t\t" + job);
			doctor_content.setText(Html.fromHtml(description));
			doctor_hospital.setText(Html.fromHtml(jobplace));
			doctor_techang.setText(Html.fromHtml(jobgood));
			if (avatar != null && !avatar.equals("")) {
				imageLoader = AsyncLoadingImg.getImageLoader(this);
				imageLoader.displayImage(avatar, iv_detail_thumb,
						AsyncLoadingImg.getDefaultOptions());// API.AVATAR +
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
