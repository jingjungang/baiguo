package com.mimi.baiguo.main;

/**
 * 健康生活
 * 景俊钢
 * 2016年8月2日 16:37:45
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.employeecare.Rank;
import com.mimi.baiguo.employeecare.ScoreShop;
import com.mimi.baiguo.lunbo.HealthLifeLunboFragment;
import com.mimi.baiguo.share.ShareActivity;
import com.mimi.baiguo.stepcounter.SettingsActivity;
import com.mimi.baiguo.stepcounter.StepCounterService;
import com.mimi.baiguo.stepcounter.StepDetector;
import com.mimi.baiguo.util.DateUtilities;
import com.mimi.baiguo.util.RoundProgressBar;
import com.mimi.baiguo.util.URLConnectionUtil;

public class HealthLife extends Fragment implements OnClickListener {
	private RoundProgressBar mRoundProgressBar;
	View root;
	TextView tv_date; // 年月日 星期几
	TextView tv_rank;// 排名
	TextView tv_real_steps;
	TextView tv_miles_cal;
	TextView tv_total;
	Button btn_store; // 商城
	/**
	 * 记步
	 */

	private TextView tv_show_step;// 步数
	private TextView tv_distance;// 行程
	private TextView tv_calories;// 卡路里
	private long timer = 0;// 运动时间
	private long startTimer = 0;// 开始时间
	private long tempTime = 0;
	private Double distance = 0.0;// 路程：米
	private Double calories = 0.0;// 热量：卡路里
	private int step_length = 0; // 步长
	private int weight = 0; // 体重
	private int total_step = 0; // 走的总步数
	private Thread thread; // 定义线程对象
	Editor editor;
	Boolean flag; // 是否加入之前时间
	TextView tv_scoles; // 积分
	int scole_steps = 100;// 多少步1个积分
	int progress = 0; // 进度
	View v_share;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.activity_cricle_progress, container,
				false);
		init();
		addView();
		init1();
		AddLunbo();
		setTimerTasck();
		return root;
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (SettingsActivity.sharedPreferences == null) {
			SettingsActivity.sharedPreferences = getActivity()
					.getSharedPreferences(
							SettingsActivity.SETP_SHARED_PREFERENCES,
							Context.MODE_PRIVATE);
		}
		// ****************find
		v_share = (View) root.findViewById(R.id.share);
		tv_rank = (TextView) root.findViewById(R.id.tv_rank);
		mRoundProgressBar = (RoundProgressBar) root
				.findViewById(R.id.roundProgressBar);
		tv_date = (TextView) root.findViewById(R.id.tv_date);
		btn_store = (Button) root.findViewById(R.id.store);
		// ***********set
		btn_store.setOnClickListener(this);
		tv_rank.setOnClickListener(this);
		tv_rank.setText(Html.fromHtml("<u>" + "您当前排名1名，查看更多 >" + "</u>"));
		tv_date.setText(DateUtilities.getSystemDate1()
				+ " "
				+ DateUtilities.getWeekDay(DateUtilities.getSystemDate())
						.replace("周", "星期"));
		v_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), ShareActivity.class);
				i.putExtra("step", total_step);
				i.putExtra("miles", distance);
				i.putExtra("cal", calories);
				getActivity().startActivity(i);
			}
		});
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
		startRunService();
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
			int step = Integer.valueOf(total_step);
			progress = step % 100;
			mRoundProgressBar.setProgress(progress); // 进度展示
			if (distance > 1000) {
				tv_distance.setText(formatDouble(distance / 1000) + "km ");// 显示路程
			} else {
				tv_distance.setText(formatDouble(distance) + "m ");// 显示路程
			}
			tv_calories.setText(formatDouble(calories) + "卡");// 显示卡路里
			int scoles = step / scole_steps;
			tv_scoles.setText("已积" + scoles + "分");
			long temp = System.currentTimeMillis() - StepDetector.start_time;
			editor.putLong("TIMER", temp);
			editor.commit();
		}
	};

	/**
	 * 获取Activity相关控件
	 */
	private void addView() {
		tv_show_step = (TextView) root.findViewById(R.id.real_steps);
		tv_distance = (TextView) root.findViewById(R.id.miles);
		tv_calories = (TextView) root.findViewById(R.id.calories);
		tv_scoles = (TextView) root.findViewById(R.id.scoles);

		tv_show_step.setText("0");
		tv_distance.setText(formatDouble(0.0));
		tv_calories.setText(formatDouble(0.0));

		handler.removeCallbacks(thread);

	}

	/**
	 * 初始化1
	 */
	private void init1() {
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

	private void startRunService() {
		Intent intent = new Intent(getActivity(), StepCounterService.class);
		getActivity().startService(intent);
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		flag = true;
	}

	/**
	 * 添加广告/商品
	 */
	private void AddLunbo() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		HealthLifeLunboFragment f = new HealthLifeLunboFragment();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// 添加活动公告
		ft.replace(R.id.content, f);
		// 添加商品
		ScoreShop shop = new ScoreShop();
		ft.replace(R.id.content1, shop);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_rank:
			startActivity(new Intent(getActivity(), Rank.class));
			break;
		case R.id.store:
			startActivity(new Intent(getActivity(), Rank.class));
			break;
		}
	}

	/**
	 * 设置定时任务
	 */
	private void setTimerTasck() {
		Timer mytimer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				mHandler.sendEmptyMessage(1);
			}
		};
		mytimer.schedule(task, 3000, 3000);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				new rThread().start();
				break;
			case 2:
				if (result != null) {
					JSONObject json;
					try {
						json = new JSONObject(result);
						String status = json.getString("status").toString();
						if (status.equals("1")) {
							Log.e("laodup step", "成功");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}

		}
	};
	String result = "";

	class rThread extends Thread {
		@Override
		public void run() {
			// 请求网络
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("token", API.token));
			parameters.add(new BasicNameValuePair("step", total_step + ""));
			parameters
					.add(new BasicNameValuePair("stepsize", step_length + ""));
			parameters.add(new BasicNameValuePair("weight", weight + ""));
			try {
				result = URLConnectionUtil.HttpClientPost(API.UPLOAD_STEP,
						parameters);
				Message m = new Message();
				m.what = 2;
				handler.sendMessage(m);
			} catch (Exception e) {
			}
			super.run();
		}
	}
}
