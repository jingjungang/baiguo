package com.mimi.baiguo.activity;

/**
 * 找回密码
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.util.JudgeInternet;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GetPasswordActivity extends Activity implements OnClickListener {

	/** 手机号，验证码，密码 **/
	private EditText et_mobile, et_yzm, et_password;
	/** 获取验证码，提交 **/
	private Button btn_yzm, btn_register;
	/** 提交密码URL **/
	String commitUrl = API.GET_BACK_PASSWOED;
	/** 获取验证码URL **/
	String verifyUrl = API.GET_BACK_PASSWOED_YZM;
	private TextView tv_title;
	private ImageButton ib_left_handle01;
	String result;
	Handler handler;
	private Timer timer;
	private int timeCount = 60;
	private static SystemBarTintManager tintManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		// getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_password);
		init();

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					LoginActivity.stopProgressDialog();
					try {
						JSONObject json = new JSONObject(result);
						int status = json.getInt("status");
						switch (status) {
						case 0:
							Toast.makeText(GetPasswordActivity.this, "手机号格式错误",
									Toast.LENGTH_SHORT).show();
							break;
						case -1:
							Toast.makeText(GetPasswordActivity.this, "验证码为空",
									Toast.LENGTH_SHORT).show();
							break;
						case -2:
							Toast.makeText(GetPasswordActivity.this,
									"信息填写与获取验证码时候不一致", Toast.LENGTH_SHORT)
									.show();
							break;
						case -3:
							Toast.makeText(GetPasswordActivity.this, "密码为空",
									Toast.LENGTH_SHORT).show();
							break;
						case -4:
							Toast.makeText(GetPasswordActivity.this, "更新失败",
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Toast.makeText(GetPasswordActivity.this, "修改成功",
									Toast.LENGTH_SHORT).show();
							finish();
							break;
						}
					} catch (JSONException e) {
					}
					break;
				case 2:
					LoginActivity.stopProgressDialog();
					try {
						JSONObject json = new JSONObject(result);
						int status = json.getInt("status");
						switch (status) {
						case 0:
							Toast.makeText(GetPasswordActivity.this, "手机号格式错误",
									Toast.LENGTH_SHORT).show();
							break;
						case -1:
							Toast.makeText(GetPasswordActivity.this,
									"手机号不存在（未注册）", Toast.LENGTH_SHORT).show();
							break;
						case -2:
							Toast.makeText(GetPasswordActivity.this,
									"一分钟只能发送一次", Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Toast.makeText(GetPasswordActivity.this, "发送成功",
									Toast.LENGTH_SHORT).show();
							break;
						}
					} catch (JSONException e) {
					}
					break;
				}
			};
		};

	}

	private void init() {
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		et_yzm = (EditText) findViewById(R.id.et_yzm);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_yzm = (Button) findViewById(R.id.btn_yzm);
		btn_register = (Button) findViewById(R.id.btn_register);
		tv_title = (TextView) findViewById(R.id.tv_title);
		ib_left_handle01 = (ImageButton) findViewById(R.id.ib_left_handle01);
		tv_title.setText("找回密码");
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		ib_left_handle01.setOnClickListener(this);
		btn_yzm.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String mobile = et_mobile.getText().toString();
		String yzm = et_yzm.getText().toString();
		String password = et_password.getText().toString();
		switch (arg0.getId()) {
		case R.id.btn_yzm:
			if (mobile.trim().length() < 11) {
				Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
				return;
			}
			LoginActivity.startProgressDialog(this);
			new Verification(mobile).start();
			startTimer();
			break;
		case R.id.btn_register:
			if (mobile.trim().length() < 11) {
				Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
			} else if (yzm.trim().length() < 6) {
				Toast.makeText(this, "验证码长度不够", Toast.LENGTH_SHORT).show();
			} else if (password.trim().length() < 6) {
				Toast.makeText(this, "验证码格式", Toast.LENGTH_SHORT).show();
			} else if (JudgeInternet.isNetworkAvailable(this)) {
				LoginActivity.startProgressDialog(this);
				new Commit(mobile, yzm, password).start();
			}
			break;
		case R.id.ib_left_handle01:
			finish();
			break;
		}
	}

	/** 提交密码请求 */
	class Commit extends Thread {
		String mobile;
		String code;
		String password;

		public Commit(String mobile, String code, String password) {
			super();
			this.mobile = mobile;
			this.code = code;
			this.password = password;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("phone", mobile));
			parameters.add(new BasicNameValuePair("code", code));
			parameters.add(new BasicNameValuePair("password", password));
			parameters.add(new BasicNameValuePair("type", "2"));
			result = URLConnectionUtil.HttpClientPost(commitUrl, parameters);
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
			super.run();
		}
	}

	/** 验证码请求 */
	class Verification extends Thread {
		String mobile;

		public Verification(String mobile) {
			super();
			this.mobile = mobile;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("phone", mobile));
			parameters.add(new BasicNameValuePair("type", "2"));
			result = URLConnectionUtil.HttpClientPost(verifyUrl, parameters);
			Message msg = new Message();
			msg.what = 2;
			handler.sendMessage(msg);
			super.run();
		}
	}

	/** 验证button计时器 */
	private void startTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timeCount--;
				timeHandler.sendEmptyMessage(0);
			}
		}, 0, 1000);
	}

	private Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			btn_yzm.setEnabled(false);
			btn_yzm.setText(timeCount + "秒");
			if (timeCount < 0) {
				timeCount = 60;
				timer.cancel();
				timer = null;
				btn_yzm.setEnabled(true);
				btn_yzm.setText("验证码");
			}
		}
	};
}
