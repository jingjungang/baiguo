package com.mimi.baiguo.adapter;

import java.util.List;
import java.util.Map;

import com.mimi.baiguo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhiShiGridViewAdapter extends BaseAdapter {

	private Context context;
	List<Map<String, String>> list;
	private int clickTemp = -1;

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	public ZhiShiGridViewAdapter(Context context, List<Map<String, String>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.zhishi_gridview_time, null);
		final TextView gridview_tv = (TextView) convertView
				.findViewById(R.id.gridview_tv);
		final TextView gridview_img = (TextView) convertView
				.findViewById(R.id.img);
		final TextView lable = (TextView) convertView.findViewById(R.id.lable);
		lable.setText(list.get(position).get("lable"));
		gridview_tv.setText(list.get(position).get("name"));
		if (clickTemp == position) {
			gridview_tv.setTextColor(context.getResources().getColor(
					R.color.common_text));
		} else {
			gridview_tv.setTextColor(context.getResources().getColor(
					R.color.text_gray));
		}
		String id = (list.get(position)).get("id");
		int i_id = Integer.valueOf(id);
		switch (i_id) {
		case 8:
			gridview_img.setBackground(context.getResources().getDrawable(
					R.drawable.knowled_01));
			break;
		case 9:
			gridview_img.setBackground(context.getResources().getDrawable(
					R.drawable.knowled_02));
			break;
		case 10:
			gridview_img.setBackground(context.getResources().getDrawable(
					R.drawable.knowled_03));
			break;
		}
		return convertView;
	}
}
