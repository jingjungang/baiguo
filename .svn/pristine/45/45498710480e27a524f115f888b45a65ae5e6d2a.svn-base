package com.mimi.baiguo.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.IYWConversationUnreadChangeListener;
import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.sample.LoginSampleHelper;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.mimi.baiguo.util.UpdateManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	public static final String TAG = "FragmentTabs";
	private FragmentTabHost mTabHost;
	public static final String TAB_MESSAGE = "message";
	public static final String TAB_CONTACT = "contact";
	public static final String TAB_TRIBE = "tribe";
	public static final String TAB_MORE = "more";
	public static final String TAB_CARE = "care";
	private YWIMKit mIMKit;
	// bottom图标
	private Drawable mMessagePressed;
	private Drawable mMessageNormal;
	private Drawable mFriendPressed;
	private Drawable mFriendNormal;
	private Drawable mTribePressed;
	private Drawable mTribeNormal;
	private Drawable mMorePressed;
	private Drawable mMoreNormal;
	private Drawable mCarePressed;
	private Drawable mCareNormal;
	// bottom文字
	private TextView mMessageTab;
	private TextView mContactTab;
	private TextView mTribeTab;
	private TextView mMoreTab;
	private TextView mUnread;
	private TextView mCare;
	private IYWConversationService mConversationService;
	private IYWConversationUnreadChangeListener mConversationUnreadChangeListener;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private String result;
	private static SystemBarTintManager tintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		mIMKit = LoginSampleHelper.getInstance().getIMKit();
		if (mIMKit != null) {
			mConversationService = mIMKit.getConversationService();
		}

		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		// 隐藏虚拟键盘
		// getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.activity_main);

		init();
		apkQingQiu();
		// 友盟更新设置
		// UmengUpdateAgent.setUpdateOnlyWifi(false);//非wifi下检查更新
		// UmengUpdateAgent.setDeltaUpdate(false);//设置为全量更新
		// UmengUpdateAgent.update(this);//调用更新方法
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		initListeners();
		super.onStart();
	}

	/**
	 * 初始化相关监听
	 */
	private void initListeners() {
		// 初始化并添加会话变更监听
		initConversationServiceAndListener();
		// 初始化联系人相关的监听
		// initContactListeners();
		// 添加联系人相关的监听
		// addContactListeners();
		// 初始化并添加群变更监听
		// addTribeChangeListener();
		// 初始化自定义会话
		// initCustomConversation();
		// 设置发送消息生命周期监听
		// setMessageLifeCycleListener();
		// 设置发送消息给黑名单中的联系人监听
		// setSendMessageToContactInBlackListListener();
	}

	/** 实例化bottom控件 */
	private void init() {

		// 实例化FragmentTabHost
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.mrealtabcontent);
		mTabHost.getTabWidget().setDividerDrawable(null);
		// 加载fragment页面
		View indicator = getIndicatorView(TAB_MESSAGE);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MESSAGE)
				.setIndicator(indicator), OneFragment_new.class, null);

		indicator = getIndicatorView(TAB_CONTACT);
		// 判断友盟对象是否为空，不为空加载消息页面
		// if (mIMKit != null) {
		// mTabHost.addTab(mTabHost.newTabSpec(TAB_CONTACT).setIndicator(indicator),
		// mIMKit.getConversationFragmentClass(), null);
		// } else {
		// mTabHost.addTab(mTabHost.newTabSpec(TAB_CONTACT).setIndicator(indicator),
		// TwoFragment.class, null);
		// }
		indicator = getIndicatorView(TAB_CARE);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CARE).setIndicator(indicator),
				HealthLife.class, null);
		
		indicator = getIndicatorView(TAB_TRIBE);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_TRIBE).setIndicator(indicator),
				ThreeMenuFragment.class, null);

		indicator = getIndicatorView(TAB_MORE);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator),
				FourFragment.class, null);
		mTabHost.setOnTabChangedListener(listener);
		listener.onTabChanged(TAB_MESSAGE);
		mTabHost.setCurrentTab(1);
	}

	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			if (TAB_MESSAGE.equals(tabId)) {
				setMessageText(true);
				setContactText(false);
				setTribeText(false);
				setMoreText(false);
				setCareText(false);
				return;
			}
			if (TAB_CONTACT.equals(tabId)) {
				setMessageText(false);
				setContactText(true);
				setTribeText(false);
				setMoreText(false);
				setCareText(false);
				return;
			}
			if (TAB_TRIBE.equals(tabId)) {
				setMessageText(false);
				setContactText(false);
				setTribeText(true);
				setMoreText(false);
				setCareText(false);
				return;
			}
			if (TAB_MORE.equals(tabId)) {
				setMessageText(false);
				setContactText(false);
				setTribeText(false);
				setMoreText(true);
				setCareText(false);
				return;
			}
			if (TAB_CARE.equals(tabId)) {
				setMessageText(false);
				setContactText(false);
				setTribeText(false);
				setMoreText(false);
				setCareText(true);
				return;
			}
		}
	};

	private void setMessageText(boolean isSelected) {
		Drawable drawable = null;
		if (isSelected) {
			mMessageTab.setTextColor(getResources().getColor(
					R.color.common_text));
			mMessageTab.setText(R.string.tab_1);
			if (mMessagePressed == null) {
				mMessagePressed = getResources().getDrawable(
						R.drawable.nav_btn01_sel);
			}
			drawable = mMessagePressed;
		} else {
			mMessageTab
					.setTextColor(getResources().getColor(R.color.text_gray));
			mMessageTab.setText(R.string.tab_1);
			if (mMessageNormal == null) {
				mMessageNormal = getResources().getDrawable(
						R.drawable.nav_btn01_nor);
			}
			drawable = mMessageNormal;
		}
		if (drawable != null) {
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			mMessageTab.setCompoundDrawables(null, drawable, null, null);
		}
	}

	private void setContactText(boolean isSelected) {
		Drawable drawable = null;
		if (isSelected) {
			mContactTab.setTextColor(getResources().getColor(
					R.color.common_text));
			mContactTab.setText(R.string.tab_2);
			if (mFriendPressed == null) {
				mFriendPressed = getResources().getDrawable(
						R.drawable.nav_btn02_sel);
			}
			drawable = mFriendPressed;
		} else {
			mContactTab
					.setTextColor(getResources().getColor(R.color.text_gray));
			mContactTab.setText(R.string.tab_2);
			if (mFriendNormal == null) {
				mFriendNormal = getResources().getDrawable(
						R.drawable.nav_btn02_nor);
			}
			drawable = mFriendNormal;
		}
		if (null != drawable) {// 此处出现过NP问题，加保护
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			mContactTab.setCompoundDrawables(null, drawable, null, null);
		}

	}

	private void setTribeText(boolean isSelected) {
		Drawable drawable = null;
		if (isSelected) {
			mTribeTab
					.setTextColor(getResources().getColor(R.color.common_text));
			mTribeTab.setText(R.string.tab_3);
			if (mTribePressed == null) {
				mTribePressed = getResources().getDrawable(
						R.drawable.nav_btn03_sel);
			}
			drawable = mTribePressed;
		} else {
			mTribeTab.setTextColor(getResources().getColor(R.color.text_gray));
			mTribeTab.setText(R.string.tab_3);
			if (mTribeNormal == null) {
				mTribeNormal = getResources().getDrawable(
						R.drawable.nav_btn03_nor);
			}
			drawable = mTribeNormal;
		}
		if (null != drawable) {// 此处出现过NP问题，加保护
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			mTribeTab.setCompoundDrawables(null, drawable, null, null);
		}

	}

	private void setMoreText(boolean isSelected) {
		Drawable drawable = null;
		if (isSelected) {
			mMoreTab.setTextColor(getResources().getColor(R.color.common_text));
			mMoreTab.setText(R.string.tab_4);
			if (mMorePressed == null) {
				mMorePressed = getResources().getDrawable(
						R.drawable.nav_btn04_sel);
			}
			drawable = mMorePressed;
		} else {
			mMoreTab.setTextColor(getResources().getColor(R.color.text_gray));
			mMoreTab.setText(R.string.tab_4);
			if (mMoreNormal == null) {
				mMoreNormal = getResources().getDrawable(
						R.drawable.nav_btn04_nor);
			}
			drawable = mMoreNormal;
		}
		if (null != drawable) {// 此处出现过NP问题，加保护
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			mMoreTab.setCompoundDrawables(null, drawable, null, null);
		}

	}

	private void setCareText(boolean isSelected) {
		Drawable drawable = null;
		if (isSelected) {
			mCare.setTextColor(getResources().getColor(R.color.common_text));
			mCare.setText(R.string.tab_5);
			if (mCarePressed == null) {
				mCarePressed = getResources().getDrawable(
						R.drawable.health2);
			}
			drawable = mCarePressed;
		} else {
			mCare.setTextColor(getResources().getColor(R.color.text_gray));
			mCare.setText(R.string.tab_5);
			if (mCareNormal == null) {
				mCareNormal = getResources().getDrawable(
						R.drawable.health1);
			}
			drawable = mCareNormal;
		}
		if (null != drawable) {// 此处出现过NP问题，加保护
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			mCare.setCompoundDrawables(null, drawable, null, null);
		}

	}

	private View getIndicatorView(String tab) {
		View tabView = View.inflate(this, R.layout.demo_tab_item, null);
		TextView indicator = (TextView) tabView.findViewById(R.id.tab_text);
		Drawable drawable;

		if (tab.equals(TAB_MESSAGE)) {
			indicator.setText(R.string.tab_1);
			drawable = getResources().getDrawable(R.drawable.nav_btn01_nor);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			indicator.setCompoundDrawables(null, drawable, null, null);
			mMessageTab = indicator;
		} else if (tab.equals(TAB_CONTACT)) {
			indicator.setText(R.string.tab_2);
			drawable = getResources().getDrawable(R.drawable.nav_btn02_nor);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			indicator.setCompoundDrawables(null, drawable, null, null);
			mContactTab = indicator;
		} else if (tab.equals(TAB_TRIBE)) {
			indicator.setText(R.string.tab_3);
			drawable = getResources().getDrawable(R.drawable.nav_btn03_nor);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			indicator.setCompoundDrawables(null, drawable, null, null);
			mTribeTab = indicator;
		} else if (tab.equals(TAB_MORE)) {
			indicator.setText(R.string.tab_4);
			drawable = getResources().getDrawable(R.drawable.nav_btn04_nor);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			indicator.setCompoundDrawables(null, drawable, null, null);
			mMoreTab = indicator;
		} else if (tab.equals(TAB_CARE)) {
			indicator.setText(R.string.tab_5);
			drawable = getResources().getDrawable(R.drawable.health1);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			indicator.setCompoundDrawables(null, drawable, null, null);
			mCare = indicator;
		}
		return tabView;
	}

	private void initConversationServiceAndListener() {
		mConversationUnreadChangeListener = new IYWConversationUnreadChangeListener() {

			// 当未读数发生变化时会回调该方法，开发者可以在该方法中更新未读数
			@Override
			public void onUnreadChange() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						LoginSampleHelper loginHelper = LoginSampleHelper
								.getInstance();
						final YWIMKit imKit = loginHelper.getIMKit();
						mConversationService = imKit.getConversationService();
						// 获取当前登录用户的所有未读数
						int unReadCount = mConversationService
								.getAllUnreadCount();
						if (unReadCount > 0) {
							mUnread.setVisibility(View.VISIBLE);
							if (unReadCount < 100) {
								mUnread.setText(unReadCount + "");
							} else {
								mUnread.setText("99+");
							}
						} else {
							mUnread.setVisibility(View.INVISIBLE);
						}
					}
				});
			}
		};
	}

	/**
	 * 开启线程访问网络得到数据更新UI
	 */
	private void apkQingQiu() {
		// 启动线程来执行任务
		new Thread() {

			public void run() {
				// 请求网络
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				// type 1医生2患者
				parameters.add(new BasicNameValuePair("type", "2"));
				// 1正式2测试
				parameters.add(new BasicNameValuePair("test", "1"));
				result = URLConnectionUtil.HttpClientPost(API.APKurl,
						parameters);
				Message m = new Message();
				m.what = 1;
				// 发送消息到Handler
				handler.sendMessage(m);
			}
		}.start();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: // 更新
				try {
					if (!TextUtils.isEmpty(result)) {
						JSONObject json = new JSONObject(result);
						if (json.getInt("vnum") > API.code) {
							String content = json.has("content") ? json
									.getString("content") : "";
							int size = json.has("size") ? json.getInt("size")
									: 0;
							new UpdateManager(MainActivity.this,
									API.APK_DOWN_URL, content, size)
									.checkUpdateInfo();
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {

				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			System.exit(0);
		}
	}
}
