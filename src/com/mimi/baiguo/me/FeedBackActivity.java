package com.mimi.baiguo.me;

import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 意见反馈
 * 
 * @author AAA
 * 
 */
public class FeedBackActivity extends Activity implements OnClickListener {
	private TextView tv_title;
	private EditText feedback_content;
	private Button confirm;
	private ImageButton ib_left_handle01;
	private static SystemBarTintManager tintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.activity_feed_back);
		init();
	}

	public void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		feedback_content = (EditText) findViewById(R.id.feedback_content);
		confirm = (Button) findViewById(R.id.confirm);
		tv_title.setText(R.string.feed_back);
		ib_left_handle01 = (ImageButton) findViewById(R.id.ib_left_handle01);
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		ib_left_handle01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0==confirm){
			if("".equals(feedback_content.getText().toString()) == false){
				Toast.makeText(FeedBackActivity.this, "提交成功", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
}
