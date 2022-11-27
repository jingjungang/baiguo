package com.mimi.baiguo.activity;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
/***
 * 随访通知
 * @author AAA
 *
 */
public class VisitInfomActivity extends Activity {
	
	private static SystemBarTintManager tintManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
		setContentView(R.layout.activity_visit_infom);

	}
}
