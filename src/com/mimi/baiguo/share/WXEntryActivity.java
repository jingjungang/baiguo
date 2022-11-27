package com.mimi.baiguo.share;

import android.os.Bundle;
import android.widget.Toast;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by ntop on 15/9/4.
 */
public class WXEntryActivity extends WXCallbackActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			Toast.makeText(WXEntryActivity.this, " 分享成功啦", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
