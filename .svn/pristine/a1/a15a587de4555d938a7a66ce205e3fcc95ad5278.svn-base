package com.mimi.baiguo.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.mimi.baiguo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AsyncLoadingImg {

	private static ImageLoader imageLoader;
	
	/**
	 * 获得ImageLoader对象*/
	public static ImageLoader getImageLoader(Context context){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
        .diskCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO).build();
    ImageLoader.getInstance().init(config);
    imageLoader=ImageLoader.getInstance();
    return imageLoader;
	}
	
	/**
	 * 设置默认图片
	 * @return
	 */
	public static DisplayImageOptions getDefaultOptions() {
		//设置图片加载的属性
        com.nostra13.universalimageloader.core.DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageForEmptyUri(R.drawable.no_pic);
        b.showImageOnFail(R.drawable.no_pic);
        b.showImageOnLoading(R.drawable.loading);
        b.resetViewBeforeLoading(Boolean.TRUE);
        b.cacheOnDisk(Boolean.TRUE);
        b.cacheInMemory(Boolean.TRUE);
        b.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        return b.bitmapConfig(Bitmap.Config.RGB_565).build();
    }
}
