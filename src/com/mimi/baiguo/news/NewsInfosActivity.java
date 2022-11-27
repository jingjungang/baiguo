package com.mimi.baiguo.news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

/***
 * 消息详情
 * 
 * @author AAA
 * 
 */
public class NewsInfosActivity extends Activity implements OnClickListener {
	// 改变 WebView上显示网页内容的CSS样式
	public String CSS_STYLE = "<style>* {font-size:50px;line-height:55px;}p {color:#000000;}</style>";

	/** 标题，时间，来源，概要，详细 */
	private TextView tv_news_titles, tv_news_times, tv_news_froms, tv_title;
	private WebView wv_news_content;
	private ImageButton ib_left_handle01;
	private static SystemBarTintManager tintManager;
	String Url = API.NEWS_INFOS_URL;
	String result;
	String contentUrl;
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		// getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_infos);

		init();
		if (JudgeInternet.isNetworkAvailable(NewsInfosActivity.this)) {
			LoginActivity.startProgressDialog(this);
			showList();
		}
		// 获取屏幕高度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		float density = metric.density / 2;
		Log.i("-->", density + "");
		if (density == 1.0) {
			CSS_STYLE = "<style>* {font-size:30px;line-height:55px;}p {color:#000000;}</style>";
		}
	}

	private void init() {
		tv_news_titles = (TextView) findViewById(R.id.tv_news_titles);
		tv_news_times = (TextView) findViewById(R.id.tv_news_times);
		tv_news_froms = (TextView) findViewById(R.id.tv_news_froms);
		wv_news_content = (WebView) findViewById(R.id.wv_news_content);
		wv_news_content.setInitialScale(100);
		tv_title = (TextView) findViewById(R.id.tv_title);
		ib_left_handle01 = (ImageButton) findViewById(R.id.ib_left_handle01);

		tv_title.setText("知识详情");
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		ib_left_handle01.setOnClickListener(this);
	}

	/**
	 * 开启线程访问网络得到数据更行UI
	 */
	private void showList() {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					showListByResulttg(result);
				}
			}
		};
		// 启动线程来执行任务
		new Thread() {
			public void run() {
				// 请求网络
				id = getIntent().getStringExtra("id");
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("token", "1"));
				result = URLConnectionUtil.HttpClientPost(Url + id, parameters);
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
			// 构建JSON数组对象
			JSONObject oo = o.getJSONObject("info");
			String title = oo.has("title") ? oo.getString("title") : "";
			long time = (Long) (oo.has("addtime") ? oo.getLong("addtime") : "");
			String from = oo.has("source") ? oo.getString("source") : "";
			String content = oo.has("content") ? oo.getString("content") : "";
			tv_news_titles.setText(title);
			String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(
					time * 1000));
			tv_news_times.setText(date);
			tv_news_froms.setText(from);
			contentUrl = content;
			wv_news_content.getSettings().setSupportZoom(true);
			wv_news_content.getSettings().setBuiltInZoomControls(true);
			// initWebView();
			// SetFontSize(wv_news_content);
			// WebSettings settings = wv_news_content.getSettings();
			// settings.setTextSize(WebSettings.TextSize.LARGEST);
			// 字体颜色设为白色, “p”标签内的字体颜色 “*”定义了字体大小以及行高；
			// data是要显示的内容
			wv_news_content.loadDataWithBaseURL(null, CSS_STYLE + content,
					"text/html", "utf-8", null);
			wv_news_content
					.loadUrl("javascript:getDataToH5('" + "toStr" + "')");
			// wv_news_content
			// .loadDataWithBaseURL(null, content, null, null, null);
			wv_news_content.setWebViewClient(new WebViewClient() {

				/** 开始载入页面 */
				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					setProgressBarIndeterminateVisibility(true);// 设置标题栏的滚动条开始
					Log.d("YM", "setProgressBarIndeterminateVisibility");
					contentUrl = url;
					super.onPageStarted(view, url, favicon);
				}

				/** 捕获点击事件 */
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					wv_news_content.loadUrl(url);
					return true;
				}

				/** 错误返回 */
				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					super.onReceivedError(view, errorCode, description,
							failingUrl);
				}

				/** 页面载入完毕 */
				@Override
				public void onPageFinished(WebView view, String url) {
					setProgressBarIndeterminateVisibility(false);// ֹ
					super.onPageFinished(view, url);
					LoginActivity.stopProgressDialog();
				}

			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化webview
	 */
	private void initWebView() {
		// 支持缩放
		wv_news_content.getSettings().setBuiltInZoomControls(true);
		wv_news_content.getSettings().setSaveFormData(true);
		wv_news_content.clearCache(false);
		// 联网载入
		wv_news_content.loadUrl(contentUrl);
		// 设置
		wv_news_content.setWebViewClient(new WebViewClient() {

			/** 开始载入页面 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				setProgressBarIndeterminateVisibility(true);// 设置标题栏的滚动条开始
				Log.d("YM", "setProgressBarIndeterminateVisibility");
				contentUrl = url;
				super.onPageStarted(view, url, favicon);
			}

			/** 捕获点击事件 */
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				wv_news_content.loadUrl(url);
				return true;
			}

			/** 错误返回 */
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			/** 页面载入完毕 */
			@Override
			public void onPageFinished(WebView view, String url) {
				setProgressBarIndeterminateVisibility(false);// ֹ
				super.onPageFinished(view, url);
				LoginActivity.stopProgressDialog();
			}

		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}
}
