package com.mimi.baiguo.adapter;

/**
 * 互动Adapter
 * jjg
 * 2016年7月18日 15:01:50
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mimi.baiguo.R;
import com.mimi.baiguo.mydoctor.InteractionDetailsActivity;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.widget.imageview.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;

public class InteractionMessageAdapter extends BaseAdapter {

	private Context context;
	JSONArray ja;
	ImageLoader imageLoader;

	public InteractionMessageAdapter(Context context, JSONArray ja) {
		super();
		this.context = context;
		this.ja = ja;
		imageLoader = AsyncLoadingImg.getImageLoader(context);
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
		if (arg1 == null) {
			hodler = new ViewHodler();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.item_attention, null);
			hodler.tv1 = (TextView) arg1.findViewById(R.id.name_job);
			hodler.tv2 = (TextView) arg1.findViewById(R.id.hospital_dept);
			hodler.tv3 = (TextView) arg1.findViewById(R.id.reply);
			hodler.tv4 = (TextView) arg1.findViewById(R.id.job);
			hodler.img = (CircularImage) arg1.findViewById(R.id.img);
			arg1.setTag(hodler);
		} else {
			hodler = (ViewHodler) arg1.getTag();
		}
		try {
			JSONObject jo = (JSONObject) ja.get(position);
			hodler.tv4.setText(jo.getString("job"));
			hodler.tv1.setText(jo.getString("nickname"));
			if (!TextUtils.isEmpty(jo.getString("subject"))) {
				hodler.tv2.setText(jo.getString("hospital") + " - "
						+ jo.getString("subject"));
			} else {
				hodler.tv2.setText(jo.getString("hospital"));
			}
			String temp = jo.has("title") ? jo.getString("title") : "";
			hodler.tv3.setText(temp.replace("null", ""));
			// TitleList.get(arg0).get("logo"), hodler.img,
			// AsyncLoadingImg.getDefaultOptions()
			if (jo.has("avatar")
					&& !TextUtils.isEmpty(jo.getString("avatar").toString())) {
				imageLoader.displayImage(jo.getString("avatar").toString(),
						hodler.img, AsyncLoadingImg.getDefaultOptions());
			} else {
				hodler.img.setImageDrawable(context.getResources().getDrawable(
						R.drawable.icon_man));
			}
			// hodler.tv4.setText("咨询内容(" + jo.getString("nickname") + ")：");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arg1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						InteractionDetailsActivity.class);
				try {
					intent.putExtra(InteractionDetailsActivity.TITLE, ja
							.getJSONObject(position).getString("title"));
					intent.putExtra(InteractionDetailsActivity.DID, ja
							.getJSONObject(position).getString("did"));
					String temp = ja.getJSONObject(position).getString(
							"username");
					if (TextUtils.isEmpty(temp)) {
						temp = ja.getJSONObject(position).getString("nickname");
					}
					intent.putExtra(InteractionDetailsActivity.DOC_NAME, temp);
					intent.putExtra(InteractionDetailsActivity.DOC_JOB, ja
							.getJSONObject(position).getString("job"));
					intent.putExtra(InteractionDetailsActivity.DOC_HOSPITAL, ja
							.getJSONObject(position).getString("hospital"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				context.startActivity(intent);
			}
		});
		return arg1;
	}

	class ViewHodler {
		CircularImage img;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
	}
}
