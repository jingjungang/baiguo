package com.mimi.baiguo.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;

public class RegisterActivity extends Activity implements OnClickListener {
	public TextView tv_title;// 中间title
	public EditText et_username, et_password, et_repassword, register_et_yzm;// 账号，密码，重复密码
	public Button btn_register;// 注册
	private static SystemBarTintManager tintManager;

	private String Url = API.USER_REGISTER_URL;
	String username, password, rePassword;
	String result;
	private Thread mThread;
	private Button register_btn_yzm;// 获取验证码
	private Timer timer;
	private int timeCount = 180;
	private int mcount = 180;// 预设180秒

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		init();
	}

	public void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_repassword = (EditText) findViewById(R.id.et_repassword);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
		tv_title.setText(R.string.register);
		register_btn_yzm = (Button) findViewById(R.id.register_btn_yzm);
		register_btn_yzm.setOnClickListener(this);
		register_et_yzm = (EditText) findViewById(R.id.register_et_yzm);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		username = et_username.getText().toString();
		password = et_password.getText().toString();
		rePassword = et_repassword.getText().toString();
		if (v == btn_register) {
			if ("".equals(username.trim()) || "".equals(password.trim())
					|| "".equals(rePassword)) {
				Toast.makeText(RegisterActivity.this, "请输入用户名和密码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (username.trim().length() < 11) {
				Toast.makeText(RegisterActivity.this, "手机号格式不正确",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!password.equals(rePassword)) {
				Toast.makeText(RegisterActivity.this, "两次输入密码不一致，请重新输入",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.trim().length() < 6) {
				Toast.makeText(RegisterActivity.this, "密码长度不能少于6位",
						Toast.LENGTH_SHORT).show();
				return;
			}
			register();

		} else if (v == register_btn_yzm) {
			if (username.length() < 11) {
				Toast.makeText(RegisterActivity.this, "手机号格式不正确",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// LoginActivity.startProgressDialog(this);
			new Verification(username).start();
		}
	}

	/** 白果注册 */
	public void register() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(result);
						int status = jo.getInt("status");

						if (status == -1) {
							Toast.makeText(RegisterActivity.this, "手机号格式错误",
									Toast.LENGTH_SHORT).show();
						} else if (status == -2) {
							Toast.makeText(RegisterActivity.this, "验证码错误",
									Toast.LENGTH_SHORT).show();
						} else if (status == -3) {
							Toast.makeText(RegisterActivity.this, "验证码过期",
									Toast.LENGTH_SHORT).show();
						} else if (status == -4) {
							Toast.makeText(RegisterActivity.this, "网络出错",
									Toast.LENGTH_SHORT).show();
						} else if (status == -5) {
							Toast.makeText(RegisterActivity.this, "电话号码已存在",
									Toast.LENGTH_SHORT).show();
						} else if (status == 1) {
							Toast.makeText(RegisterActivity.this, "注册成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(RegisterActivity.this, "注册失败",
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		mThread = new Thread() {
			public void run() {
				result = Postmessage(Url, username, password);
				Message m = new Message();
				m.what = 1;
				handler.sendMessage(m);
			}
		};
		mThread.start();
	}

	public String Postmessage(String Url, String str_username,
			String str_password) {

		// String value="";
		try {
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "utf-8");
			String data = "mobile="
					+ URLEncoder.encode(str_username, "UTF-8")
					+ "&password="
					+ URLEncoder.encode(str_password, "UTF-8")
					+ "&code="
					+ URLEncoder.encode(register_et_yzm.getText().toString(),
							"UTF-8") + "&type=" // 1 医生 2患者 3临床实验
					+ URLEncoder.encode("2", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			InputStreamReader is = new InputStreamReader(conn.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(is);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			is.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
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
			result = URLConnectionUtil.HttpClientPost(
					API.USER_REGISTER_URL_YZM, parameters);
			Message msg = new Message();
			msg.what = 1;
			mhandler.sendMessage(msg);
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
			register_btn_yzm.setEnabled(false);
			register_btn_yzm.setText(timeCount + "秒");
			if (timeCount < 0) {
				timeCount = mcount;
				timer.cancel();
				timer = null;
				register_btn_yzm.setEnabled(true);
				register_btn_yzm.setText("验证码");
			}
		}
	};
	Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				try {
					JSONObject json = new JSONObject(result);
					int status = json.getInt("status");
					switch (status) {
					case 0:
						Toast.makeText(RegisterActivity.this, "手机号格式错误",
								Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(RegisterActivity.this, "手机号已经注册",
								Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(RegisterActivity.this, "三分钟只能发送一次",
								Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(RegisterActivity.this, "发送成功",
								Toast.LENGTH_SHORT).show();
						startTimer();
						break;
					}
				} catch (JSONException e) {
				}
				break;
			}
		}
	};
	private ProgressDialog dialog;

	private void showDialog() {
		dialog = new ProgressDialog(RegisterActivity.this);
		dialog.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					System.out.println("onkey ： BACK");
					cancelRequest();
					return true;
				}
				return false;
			}
		});
		dialog.setMessage("正在注册");
		dialog.setCancelable(false);
		dialog.show();
	}

	private void cancelDialog() {
		if (dialog != null)
			dialog.dismiss();
	}

	private void cancelRequest() {
		if (dialog != null)
			dialog.dismiss();
		if (mThread != null)
			mThread.interrupt();
	}
}
