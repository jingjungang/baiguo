package com.mimi.baiguo.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.ScreenShot;
import com.mimi.baiguo.util.SystemBarTintManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.Log;

public class ShareActivity extends Activity {
	private SHARE_MEDIA share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
	// UMImage image = new UMImage(ShareActivity.this, Defaultcontent.imageurl);
	UMImage image = null;// R.drawable.ic_logo
	UMusic music = new UMusic(Defaultcontent.musicurl);
	UMVideo video = new UMVideo(Defaultcontent.videourl);
	private static SystemBarTintManager tintManager;

	protected void onCreate(Bundle savedInstanceState) {

		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_share);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		findViewById(R.id.finish).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareAction shareAction = new ShareAction(ShareActivity.this);
				// 图、音乐、视频
				image = new UMImage(ShareActivity.this, ScreenShot
						.takeScreenShot1(ShareActivity.this, 400, 600));
				shareAction.withMedia(image);// music/vedio
				// 标题
				// shareAction.withTitle(Defaultcontent.title);
				// 链接
				// shareAction.withTargetUrl(Defaultcontent.url);
				shareAction.setPlatform(share_media)
						.setCallback(umShareListener).share();
				finish();
			}
		});
		TextView tv_step = (TextView) findViewById(R.id.step);
		TextView tv_mile = (TextView) findViewById(R.id.mile);
		TextView tv_cal = (TextView) findViewById(R.id.cal);
		try {
			Intent i = getIntent();
			tv_step.setText(i.getExtras().getInt("step") + "步");
			tv_mile.setText(i.getExtras().getDouble("miles") + "米");
			int temp = (int) i.getExtras().getDouble("cal");
			tv_cal.setText(temp + "卡");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Log.d("plat", "platform" + platform);
			if (platform.name().equals("WEIXIN_FAVORITE")) {
				Toast.makeText(ShareActivity.this, platform + " 收藏成功啦",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(ShareActivity.this, platform + " 分享成功啦",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(ShareActivity.this, platform + " 分享失败啦",
					Toast.LENGTH_SHORT).show();
			if (t != null) {
				Log.d("throw", "throw:" + t.getMessage());
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(ShareActivity.this, platform + " 分享取消了",
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this **/
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
		Log.d("result", "onActivityResult");
	}

}
