package com.mimi.baiguo.adapter;

import java.util.List;
import java.util.Map;

import com.mimi.baiguo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;

	public MessageAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list != null) {
			return list.size();
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler hodler;
		if (arg1 == null) {
			hodler = new ViewHodler();
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_two, null);
			hodler.msg_title_tv = (TextView) arg1.findViewById(R.id.msg_title_tv);
			hodler.msg_money_tv = (TextView) arg1.findViewById(R.id.msg_money_tv);
			hodler.msg_time_tv = (TextView) arg1.findViewById(R.id.msg_time_tv);
			hodler.msg_iv = (ImageView) arg1.findViewById(R.id.msg_iv);
			arg1.setTag(hodler);
		} else {
			hodler = (ViewHodler) arg1.getTag();
		}
		hodler.msg_title_tv.setText(list.get(arg0).get("title").toString());
		hodler.msg_money_tv.setText(list.get(arg0).get("money").toString());
		hodler.msg_time_tv.setText(list.get(arg0).get("time").toString());
		hodler.msg_iv.setImageResource(Integer.valueOf(list.get(arg0).get("img").toString()));
		return arg1;
	}

	class ViewHodler {
		TextView msg_title_tv;
		TextView msg_money_tv;
		TextView msg_time_tv;
		ImageView msg_iv;
	}
}
