package com.mimi.baiguo.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class NewsAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater mInflater;
	List<Map<String, Object>> mData;
	
	public ImageLoader imageLoader;
    
    public NewsAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
    }
    
    public NewsAdapter(Context context,List<Map<String, Object>> mData){
    	this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData=mData;
        
        imageLoader = AsyncLoadingImg.getImageLoader(context);
    }

	@Override
	public int getCount() {
		return mData.size();
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
		ViewHolder holder = null;
        if (convertView == null) {
             
            holder=new ViewHolder();  
             
            convertView = mInflater.inflate(R.layout.item_one, null);
            holder.img = (ImageView)convertView.findViewById(R.id.iv_news_thumb);
            holder.title = (TextView)convertView.findViewById(R.id.tv_news_title);
            convertView.setTag(holder);
             
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        
        imageLoader.displayImage((String)mData.get(position).get("img"), holder.img,AsyncLoadingImg.getDefaultOptions());
        holder.title.setText((String)mData.get(position).get("title"));
        return convertView;
	}
	
	public final class ViewHolder{
        public ImageView img;
        public TextView title;
    }
	
}
