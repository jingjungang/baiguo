package com.mimi.baiguo.stepcounter;

/**
 *记步DEMO
 */
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.mimi.baiguo.R;

@SuppressLint("HandlerLeak")
public class StepCounterActivity extends Activity {

	// 定义文本框控件
	private TextView tv_show_step;// 步数

	private TextView tv_distance;// 行程
	private TextView tv_calories;// 卡路里

	private boolean isRun = false;

	private long timer = 0;// 运动时间
	private long startTimer = 0;// 开始时间

	private long tempTime = 0;

	private Double distance = 0.0;// 路程：米
	private Double calories = 0.0;// 热量：卡路里

	private int step_length = 0; // 步长
	private int weight = 0; // 体重
	private int total_step = 0; // 走的总步数

	private Thread thread; // 定义线程对象

	private TableRow hide1, hide2;
	private TextView step_counter;
	Editor editor;
	Boolean flag; // 是否加入之前时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main_step); // 设置当前屏幕
		if (SettingsActivity.sharedPreferences == null) {
			SettingsActivity.sharedPreferences = this.getSharedPreferences(
					SettingsActivity.SETP_SHARED_PREFERENCES,
					Context.MODE_PRIVATE);
		}
		addView();
		init();
		Bundle extras = getIntent().getExtras();
		isRun = extras.getBoolean("run");
		if (thread == null) {
			thread = new Thread() {// 子线程用于监听当前步数的变化
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					int temp = 0;
					while (true) {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (StepCounterService.FLAG) {
							Message msg = new Message();
							if (temp != StepDetector.CURRENT_SETP) {
								temp = StepDetector.CURRENT_SETP;
							}
							if (startTimer != System.currentTimeMillis()) {
								timer = tempTime + System.currentTimeMillis()
										- startTimer;
							}
							handler.sendMessage(msg);// 通知主线程
						}
					}
				}
			};
			thread.start();
		}
		start();
	}

	Handler handler = new Handler() {
		@Override
		// 这个方法是从父类/接口 继承过来的，需要重写一次
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg); // 此处可以更新UI

			countDistance(); // 调用距离方法，看一下走了多远

			if (timer != 0 && distance != 0.0) {

				// 体重、距离
				// 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
				calories = weight * distance * 0.001 * 1.036;
				// 速度velocity
			} else {
				calories = 0.0;
			}

			countStep(); // 调用步数方法

			tv_show_step.setText(total_step + "");// 显示当前步数

			tv_distance.setText(formatDouble(distance));// 显示路程
			tv_calories.setText(formatDouble(calories));// 显示卡路里

			long temp = System.currentTimeMillis() - StepDetector.start_time;
			editor.putLong("TIMER", temp);
			editor.commit();
		}
	};

	/**
	 * 获取Activity相关控件
	 */
	private void addView() {
		tv_show_step = (TextView) this.findViewById(R.id.show_step);

		tv_distance = (TextView) this.findViewById(R.id.distance);
		tv_calories = (TextView) this.findViewById(R.id.calories);

		hide1 = (TableRow) findViewById(R.id.hide1);
		hide2 = (TableRow) findViewById(R.id.hide2);
		step_counter = (TextView) findViewById(R.id.step_counter);

		if (isRun) {
			hide1.setVisibility(View.GONE);
			hide2.setVisibility(View.GONE);
			step_counter.setText("次数");
		}

		tv_show_step.setText("0");
		tv_distance.setText(formatDouble(0.0));
		tv_calories.setText(formatDouble(0.0));

		handler.removeCallbacks(thread);

	}

	/**
	 * 初始化界面
	 */
	private void init() {
		tempTime = SettingsActivity.sharedPreferences.getLong("TIMER", 0);
		editor = SettingsActivity.sharedPreferences.edit();
		step_length = SettingsActivity.sharedPreferences.getInt(
				SettingsActivity.STEP_LENGTH_VALUE,
				SettingsActivity.Default_Step_Value);
		weight = SettingsActivity.sharedPreferences.getInt(
				SettingsActivity.WEIGHT_VALUE,
				SettingsActivity.Default_Weight_Value);

		countDistance();
		countStep();
		if ((timer += tempTime) != 0 && distance != 0.0) { // tempTime记录运动的总时间，timer记录每次运动时间

			// 体重、距离
			// 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036，换算一下
			calories = weight * distance * 0.001 * 1.036;

		} else {
			calories = 0.0;
		}

		tv_distance.setText(formatDouble(distance));
		tv_calories.setText(formatDouble(calories));
		tv_show_step.setText(total_step + "");

	}

	/**
	 * 计算并格式化doubles数值，保留两位有效数字
	 * 
	 * @param doubles
	 * @return 返回当前路程
	 */
	private String formatDouble(Double doubles) {
		DecimalFormat format = new DecimalFormat("####.##");
		String distanceStr = format.format(doubles);
		return distanceStr.equals(getString(R.string.zero)) ? getString(R.string.double_zero)
				: distanceStr;
	}

	private void start() {
		Intent intent = new Intent(this, StepCounterService.class);
		startService(intent);
		startTimer = System.currentTimeMillis();
		tempTime = timer;
	}

	/**
	 * 得到一个格式化的时间
	 */
	public static String getFormatTime(long time) {
		time = time / 1000;
		long second = time % 60;
		long minute = (time % 3600) / 60;
		long hour = time / 3600;
		// 秒显示两位
		String strSecond = ("00" + second)
				.substring(("00" + second).length() - 2);
		// 分显示两位
		String strMinute = ("00" + minute)
				.substring(("00" + minute).length() - 2);
		// 时显示两位
		String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
		return strHour + ":" + strMinute + ":" + strSecond;
	}

	/**
	 * 计算行走的距离
	 */
	private void countDistance() {
		if (StepDetector.CURRENT_SETP % 2 == 0) {
			distance = (StepDetector.CURRENT_SETP / 2) * 3 * step_length * 0.01;
		} else {
			distance = ((StepDetector.CURRENT_SETP / 2) * 3 + 1) * step_length
					* 0.01;
		}
	}

	/**
	 * 实际的步数
	 */
	private void countStep() {
		if (StepDetector.CURRENT_SETP % 2 == 0) {
			total_step = StepDetector.CURRENT_SETP;
		} else {
			total_step = StepDetector.CURRENT_SETP + 1;
		}

		total_step = StepDetector.CURRENT_SETP;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		flag = true;
	}
}
