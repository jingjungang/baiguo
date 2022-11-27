package com.mimi.baiguo.main;

/**
 * 我的医生
 * 景俊钢
 * 2016年5月4日 09:10:39
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.BaiGuoApplication;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.mydoctor.NetworkConsultingActivity;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ThreeFragment extends Fragment implements OnClickListener {

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
	View root;
	BaiGuoApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.activity_doctordetail, container,
				false);
		init();
		application = (BaiGuoApplication) getActivity().getApplication();
		if (!TextUtils.isEmpty(application.User_doctor)) {
			id = application.User_doctor;
		} else {
			id = "16";
		}
		if (JudgeInternet.isNetworkAvailable(getActivity())
				&& !API.getYouke().equals("游客账号")) {
			LoginActivity.startProgressDialog(getActivity());
			showList();
		} else {
			// startActivity(new Intent(getActivity(), LoginActivity.class));
			Toast.makeText(getActivity(), "请登录！", Toast.LENGTH_SHORT).show();
		}
		return root;
	}

	private void init() {
		chitchat_btn = (ImageView) root.findViewById(R.id.chitchat_btn);
		iv_detail_thumb = (ImageView) root.findViewById(R.id.iv_detail_thumb);
		tv_detail_name = (TextView) root.findViewById(R.id.tv_detail_name);
		tv_detail_job = (TextView) root.findViewById(R.id.tv_detail_job);
		tv_detail_hospital = (TextView) root
				.findViewById(R.id.tv_detail_hospital);
		doctor_content = (TextView) root.findViewById(R.id.doctor_content);
		doctor_hospital = (TextView) root.findViewById(R.id.doctor_hospital);
		doctor_techang = (TextView) root.findViewById(R.id.doctor_techang);
		tv_title = (TextView) root.findViewById(R.id.tv_title);
		ib_left_handle01 = (ImageButton) root
				.findViewById(R.id.ib_left_handle01);
		ib_left_handle01.setVisibility(View.INVISIBLE);
		tv_title.setText(R.string.tab_3);
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		iv_detail_thumb.setImageResource(R.drawable.icon_man);
		chitchat_btn.setOnClickListener(this);
		chitchat_btn.setVisibility(View.INVISIBLE);
		ib_left_handle01.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.chitchat_btn:
			Intent intent = new Intent(getActivity(),
					NetworkConsultingActivity.class);
			startActivity(intent);
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
			id = oo.has("yname") ? oo.getString("yname") : "";
			String username = oo.has("username") ? oo.getString("username")
					: "";

			if (username.equals("null")) {
				username = "未填写姓名";
			}
			String nickname = oo.has("nickname") ? oo.getString("nickname")
					: "";
			if (nickname.equals("null")) {
				nickname = "";
			}
			String avatar = oo.has("avatar") ? oo.getString("avatar") : "";
			String job = oo.has("job") ? oo.getString("job") : "";
			if (job.equals("null")) {
				job = "未填写职称";
			}
			String hospital = oo.has("hospital") ? oo.getString("hospital")
					: "";
			if (hospital.equals("null")) {
				hospital = "未填写医院";
			}
			String subject = oo.has("subject") ? oo.getString("subject") : "";
			if (subject.equals("null")) {
				subject = "未填写科室";
			}
			String description = oo.has("description") ? oo
					.getString("description") : "";
			if (description.equals("null")) {
				description = "未填写描述";
			}
			String jobplace = oo.has("jobplace") ? oo.getString("jobplace")
					: "";
			if (jobplace.equals("null")) {
				jobplace = "未填写医院";
			}
			String jobgood = oo.has("jobgood") ? oo.getString("jobgood") : "";
			if (jobgood.equals("null")) {
				jobgood = "未填写特长";
			}
			if ((nickname != null && !nickname.equals(""))) {
				tv_detail_name.setText(nickname);
				API.setDoctorName(nickname);
			} else {
				tv_detail_name.setText(username);
				API.setDoctorName(username);
			}
			tv_detail_hospital.setText(hospital);
			tv_detail_job.setText(subject + "\t\t" + job);
			doctor_content.setText(Html.fromHtml(description));
			doctor_hospital.setText(Html.fromHtml(jobplace));
			doctor_techang.setText(Html.fromHtml(jobgood));
			if (avatar != null && !avatar.equals("")) {
				imageLoader = AsyncLoadingImg.getImageLoader(getActivity());
				imageLoader.displayImage(API.AVATAR + avatar, iv_detail_thumb,
						AsyncLoadingImg.getDefaultOptions());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
