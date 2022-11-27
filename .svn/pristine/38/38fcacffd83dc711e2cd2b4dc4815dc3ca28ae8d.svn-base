package com.mimi.baiguo.employeecare;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.R.color;
import com.mimi.baiguo.R.drawable;
import com.mimi.baiguo.R.id;
import com.mimi.baiguo.R.layout;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopDetails extends Activity implements OnClickListener {
	String good_id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_details);
		if (null != getIntent().getExtras()) {
			setView(getIntent());
		}
	}

	private void setView(Intent intent) {
		String pic, name, score, desc;
		ImageLoader imageLoader = AsyncLoadingImg
				.getImageLoader(ShopDetails.this);

		pic = intent.getExtras().getString("image");
		name = intent.getExtras().getString("name");
		score = intent.getExtras().getString("score");
		desc = intent.getExtras().getString("desc");
		good_id = intent.getExtras().getString("id");

		imageLoader.displayImage(pic, (ImageView) findViewById(R.id.image),
				AsyncLoadingImg.getDefaultOptions());
		((TextView) findViewById(R.id.name)).setText(name);
		((TextView) findViewById(R.id.score)).setText(score + "分");
		((TextView) findViewById(R.id.desc)).setText(desc);

		Button btn_buy = (Button) findViewById(R.id.buy);
		btn_buy.setOnClickListener(this);

		ImageButton img_back = (ImageButton) findViewById(R.id.ib_left_handle01);
		img_back.setOnClickListener(this);
		img_back.setBackgroundResource(R.drawable.back_a_normal2x);

		((TextView) findViewById(R.id.tv_title)).setText("商品详情");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_left_handle01:
			finish();
			break;
		case R.id.buy:
			new rThread().start();
			break;
		}
	}

	String result = "";

	class rThread extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("id", good_id));
			try {
				result = URLConnectionUtil.HttpClientPost(API.EXCHANGE_GOODS,
						parameters);
				Message m = new Message();
				m.what = 1;
				handler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (!TextUtils.isEmpty(result)) {
					JSONObject json;
					try {

						if (!TextUtils.isEmpty(result)) {
							json = new JSONObject(result);
							String status = json.getString("status");
							if (status.equals("1")) {
								Toast.makeText(getApplicationContext(),
										"兑换成功！", Toast.LENGTH_SHORT).show();
								finish();
							} else if (status.equals("-3")) {
								Toast.makeText(getApplicationContext(),
										"您的积分不足!", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"兑换失败！请重试", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "兑换失败！请重试",
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "兑换失败！请重试",
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
		}
	};

}
