package com.mimi.baiguo.employeecare;

/**
 * 商城列表
 * 景俊钢
 * 2016年8月10日 16:29:05
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.adapter.Rank_Adapter;
import com.mimi.baiguo.employeecare.Rank.getHotList;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreShop extends Fragment implements OnClickListener {
	View root;
	ImageView v1, v2, v3;// 商品图
	TextView tv1, tv2, tv3;// 商品图
	ImageLoader imageLoader;
	JSONArray js = null;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_scoreshop, container, false);
		init();
		new rThread().start();
		return root;
	}

	private void init() {
		// TODO Auto-generated method stub
		v1 = (ImageView) root.findViewById(R.id.v_gift_1);
		v2 = (ImageView) root.findViewById(R.id.v_gift_2);
		v3 = (ImageView) root.findViewById(R.id.v_gift_3);
		tv1 = (TextView) root.findViewById(R.id.scrol1);
		tv2 = (TextView) root.findViewById(R.id.scrol2);
		tv3 = (TextView) root.findViewById(R.id.scrol3);
		((LinearLayout) root.findViewById(R.id.ll1)).setOnClickListener(this);
		((LinearLayout) root.findViewById(R.id.ll2)).setOnClickListener(this);
		((LinearLayout) root.findViewById(R.id.ll3)).setOnClickListener(this);
		imageLoader = AsyncLoadingImg.getImageLoader(getActivity());
	}

	String result = "";

	class rThread extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("page", "1"));
			parameters.add(new BasicNameValuePair("pagesize", "3"));
			try {
				result = URLConnectionUtil.HttpClientPost(API.GIFT_LIST,
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
						json = new JSONObject(result);
						if (!TextUtils.isEmpty(result)) {
							js = json.getJSONArray("info");// temp
							setGoods(js);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
		}
	};

	/**
	 * 商品展示
	 */
	private void setGoods(JSONArray js) {
		if (js.length() > 0) {
			JSONObject jo = null;
			String score = "", goods_path = "";
			for (int i = 0; i < js.length(); i++) {
				try {
					jo = js.getJSONObject(i);
					score = jo.getString("goods_score");
					goods_path = jo.getString("goods_path");
					if (i == 0) {
						imageLoader.displayImage(goods_path, v1,
								AsyncLoadingImg.getDefaultOptions());
						tv1.setText(score + "分");
					} else if (i == 1) {
						tv2.setText(score + "分");
						imageLoader.displayImage(goods_path, v2,
								AsyncLoadingImg.getDefaultOptions());
					} else if (i == 2) {
						tv3.setText(score + "分");
						imageLoader.displayImage(goods_path, v3,
								AsyncLoadingImg.getDefaultOptions());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(), ShopDetails.class);
		if (js != null & js.length() > 0) {
			JSONObject jo = null;
			try {
				switch (v.getId()) {
				case R.id.ll1:
					jo = js.getJSONObject(0);
					break;
				case R.id.ll2:
					jo = js.getJSONObject(1);
					break;
				case R.id.ll3:
					jo = js.getJSONObject(2);
					break;
				}
				i.putExtra("id", jo.getString("id"));
				i.putExtra("image", jo.getString("goods_path"));
				i.putExtra("name", jo.getString("goods_name"));
				i.putExtra("score", jo.getString("goods_score"));
				i.putExtra("desc", jo.getString("goods_desc"));
				getActivity().startActivity(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
