package com.mimi.baiguo.activity;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
/***
 * 实验室检查
 * @author AAA
 *
 */
public class TestsActivity extends Activity {
	
	private static SystemBarTintManager tintManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		setContentView(R.layout.activity_tests);

	}
}
