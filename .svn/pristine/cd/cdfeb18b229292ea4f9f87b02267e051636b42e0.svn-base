package com.mimi.baiguo.adapter;

import java.util.List;
import java.util.Map;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Knowledge_news_ViewAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> TitleList;
	ImageLoader imageLoader;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (TitleList != null) {
			return TitleList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Knowledge_news_ViewAdapter(Context context,
			List<Map<String, String>> TitleList) {
		super();
		this.context = context;
		this.TitleList = TitleList;
		imageLoader = AsyncLoadingImg.getImageLoader(context);
	}

	public List<Map<String, String>> getList() {
		return TitleList;
	}

	public void setList(List<Map<String, String>> TitleList) {
		this.TitleList = TitleList;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HasText hastext;
		if (arg1 == null) {
			hastext = new HasText();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.zhishi_news_item, null);
			hastext.zhishilist_tv = (TextView) arg1.findViewById(R.id.tv);
			hastext.zhishilist_iv = (ImageView) arg1
					.findViewById(R.id.zhishilist_iv);
			hastext.tv_sj = (TextView) arg1.findViewById(R.id.sj);
			arg1.setTag(hastext);
		} else {
			hastext = (HasText) arg1.getTag();
		}
		hastext.tv_sj.setText(TitleList.get(arg0).get("updatetime"));
		hastext.zhishilist_tv.setText(TitleList.get(arg0).get("title"));
		String temp = TitleList.get(arg0).get("logo");
		if (!TextUtils.isEmpty(temp) && !temp.equals("null")) {
			imageLoader.displayImage(temp, hastext.zhishilist_iv,
					AsyncLoadingImg.getDefaultOptions());
		}
		return arg1;
	}

	class HasText {
		TextView zhishilist_tv;
		TextView tv_sj;
		ImageView zhishilist_iv;
	}
}
