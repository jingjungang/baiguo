package com.mimi.baiguo.adapter;

/**
 * 互动-详情
 * jjg
 * 2016年4月28日 14:49:01
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.CustomerDialog_1;
import com.mimi.baiguo.util.CustomerDialog_1.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class QADetailsAdapter extends BaseAdapter {

	private Context context;
	JSONArray ja;

	public QADetailsAdapter(Context context, JSONArray ja) {
		super();
		this.context = context;
		this.ja = ja;
	}

	public JSONArray getJa() {
		return ja;
	}

	public void setJa(JSONArray ja) {
		this.ja = ja;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (ja != null) {
			return ja.length();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler hodler;
		hodler = new ViewHodler();
		try {
			JSONObject jo = (JSONObject) ja.get(position);
			String type = jo.getString("type");
			if (type.equals("dtype")) {
				arg1 = LayoutInflater.from(context).inflate(
						R.layout.adpter_item_qa_1, null);
			} else {
				arg1 = LayoutInflater.from(context).inflate(
						R.layout.adpter_item_qa_2, null);
				hodler.icon = (TextView) arg1.findViewById(R.id.icon);
				String joo_str = jo.getString("description");
				if (!TextUtils.isEmpty(joo_str)) {
					final JSONObject joo = new JSONObject(joo_str);
					hodler.icon.setVisibility(View.VISIBLE);
					arg1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							showDialog(joo);
						}
					});
				} else {
					hodler.icon.setVisibility(View.INVISIBLE);
				}
			}
			hodler.tv1 = (TextView) arg1.findViewById(R.id.tv_content);
			hodler.tv1.setText(jo.getString("content"));
			hodler.user_icon = (ImageView) arg1.findViewById(R.id.user_icon);
			if (!TextUtils.isEmpty(API.avatar_address)) {
				ImageLoader imageLoader = AsyncLoadingImg
						.getImageLoader(context);
				imageLoader.displayImage(API.avatar_address, hodler.user_icon,
						AsyncLoadingImg.getDefaultOptions());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arg1;
	}

	class ViewHodler {
		TextView tv1;
		TextView icon;
		ImageView user_icon;
	}

	/**
	 * 展示咨询内容
	 * 
	 * @param joo
	 */
	void showDialog(JSONObject joo) {
		try {
			Builder dialog = new CustomerDialog_1.Builder(context);
			View v = LayoutInflater.from(context).inflate(
					R.layout.dialog_item_descript, null);
			((TextView) v.findViewById(R.id.network_bingqingmiaoshu))
					.setText("病情描述：" + joo.getString("content1"));
			((TextView) v.findViewById(R.id.network_huayanjieguo))
					.setText("化验、检查结果：" + joo.getString("content2"));
			((TextView) v.findViewById(R.id.network_zhiliaoqingkuang))
					.setText("曾经治疗情况和效果：" + joo.getString("content3"));
			((TextView) v.findViewById(R.id.network_huodebangzhu))
					.setText("想得到怎样的帮助：" + joo.getString("content4"));
			dialog.setContentView(v);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface d, int arg1) {
							// TODO Auto-generated method stub
							d.dismiss();
						}
					});
			Dialog dia = dialog.create();
			dia.show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
