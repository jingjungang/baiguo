package com.mimi.baiguo.me;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
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

/***
 * 修改密码
 * 
 * @author AAA
 * 
 */
public class ModiPwdActivity extends Activity implements OnClickListener {
	private TextView tv_title;
	private EditText old_password, password, re_password;
	private Button bt_mod_pwd;
	private ImageButton ib_left_handle01;
	private static SystemBarTintManager tintManager;

	String oldpassword, pwd, rePwd;
	String Url = API.USER_MOD_PWD_URL;
	String token = API.getToken();
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		// getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.activity_modi_pwd);

		init();
	}

	public void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		old_password = (EditText) findViewById(R.id.old_password);
		password = (EditText) findViewById(R.id.password);
		re_password = (EditText) findViewById(R.id.re_password);
		bt_mod_pwd = (Button) findViewById(R.id.bt_mod_pwd);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("修改密码");
		ib_left_handle01 = (ImageButton) findViewById(R.id.ib_left_handle01);
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		ib_left_handle01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		bt_mod_pwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == bt_mod_pwd) {
			oldpassword = old_password.getText().toString();
			pwd = password.getText().toString();
			rePwd = re_password.getText().toString();
			if (oldpassword.trim().equals("")) {
				Toast.makeText(ModiPwdActivity.this, "当前密码不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if ("".equals(pwd.trim()) || "".equals(rePwd.trim())) {
				Toast.makeText(ModiPwdActivity.this, "密码不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (!pwd.equals(rePwd)) {
				Toast.makeText(ModiPwdActivity.this, "两次输入密码不一致",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (pwd.length() < 6) {
				Toast.makeText(ModiPwdActivity.this, "新密码长度必须大于等于6位",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (JudgeInternet.isNetworkAvailable(ModiPwdActivity.this)) {
				LoginActivity.startProgressDialog(this);
				modPwd();
			}
		}
	}

	public void modPwd() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					LoginActivity.stopProgressDialog();
					try {
						JSONObject jo = new JSONObject(result);
						int status = jo.getInt("status");

						if (status == -1) {
							Toast.makeText(ModiPwdActivity.this, "旧密码同新密码一致",
									Toast.LENGTH_SHORT).show();
						} else if (status == -2) {
							Toast.makeText(ModiPwdActivity.this, "未查找到用户",
									Toast.LENGTH_SHORT).show();
						} else if (status == -3) {
							Toast.makeText(ModiPwdActivity.this, "旧密码错误",
									Toast.LENGTH_SHORT).show();
						} else if (status == -4) {
							Toast.makeText(ModiPwdActivity.this, "更新失败",
									Toast.LENGTH_SHORT).show();
						} else if (status == 1) {
							Toast.makeText(ModiPwdActivity.this, "更新成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else if (status == 0) {
							Toast.makeText(ModiPwdActivity.this, "更新失败，密钥失效，请重新登陆",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(ModiPwdActivity.this, "更新失败",
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		new Thread() {
			public void run() {
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("token", token));
				parameters.add(new BasicNameValuePair("oldpassword",
						oldpassword));
				parameters.add(new BasicNameValuePair("password", pwd));
				parameters.add(new BasicNameValuePair("type", "2"));
				result = URLConnectionUtil.HttpClientPost(Url, parameters);
				// result = Postmessage(Url, token,oldpassword, pwd);
				Message m = new Message();
				m.what = 1;
				handler.sendMessage(m);
			}
		}.start();
	}

	public String Postmessage(String Url, String token, String oldpassword,
			String password) {

		try {
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "utf-8");
			String data = "token=" + URLEncoder.encode(token, "UTF-8")
					+ "&oldpassword=" + URLEncoder.encode(oldpassword, "UTF-8")
					+ "&password=" + URLEncoder.encode(password, "UTF-8")
					+ "&type=" + URLEncoder.encode("2", "UTF-8");
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
}
