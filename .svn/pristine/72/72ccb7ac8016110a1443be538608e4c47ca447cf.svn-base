package com.mimi.baiguo.main;

/**
 * 我
 * 2016年5月31日 09:23:45
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.activity.LoginActivity;
import com.mimi.baiguo.activity.UserCasesActivity;
import com.mimi.baiguo.me.AboutUsActivity;
import com.mimi.baiguo.me.FeedBackActivity;
import com.mimi.baiguo.me.ModiPwdActivity;
import com.mimi.baiguo.me.UserCenterActivity;
import com.mimi.baiguo.me.UserInfosActivity;
import com.mimi.baiguo.util.AsyncLoadingImg;
import com.mimi.baiguo.util.CustomProgressDialog;
import com.mimi.baiguo.util.JudgeInternet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FourFragment extends Fragment {
	private RelativeLayout rl_user_center;// 用户简略信息
	private ImageView iv_user_icon, iv_user_sex;
	private TextView tv_user_name, tv_user_age;

	/** ListView */
	private ListView lv_user_related;
	private TextView tv_title;

	private String Url = API.USER_INFO_URL;
	String token = API.getToken();
	String result;

	ImageLoader imageLoader;
	String avatar;
	String userName;
	/** 登录状态判断 */
	String loginstate;
	Button btn_logout;
	private static CustomProgressDialog progressDialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		progressDialog = CustomProgressDialog.createDialog(getActivity());
		View view = inflater.inflate(R.layout.fragment_four, null, false);
		init(view);
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("loginstate", Activity.MODE_MULTI_PROCESS);
		loginstate = sharedPreferences.getString("loginstate", "false");
		if ("true".equals(loginstate) && !API.getYouke().equals("游客账号")) {
			if (JudgeInternet.isNetworkAvailable(getActivity())) {
				progressDialog.show();
				getUserInfo();
			}
		} else {
			tv_user_name.setText("点击登录");
			tv_user_age.setText("");
		}
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("loginstate", Activity.MODE_MULTI_PROCESS);
		loginstate = sharedPreferences.getString("loginstate", "false");
		if (!"true".equals(loginstate)) {
			tv_user_name.setText("点击登录");
			tv_user_age.setText("");
		}
		if (API.uploading) {
			iv_user_icon.setImageBitmap(API.bitmap);
		}
	}

	private void init(View view) {
		rl_user_center = (RelativeLayout) view
				.findViewById(R.id.rl_user_center);
		iv_user_icon = (ImageView) view.findViewById(R.id.iv_user_icon);
		tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
		tv_user_age = (TextView) view.findViewById(R.id.tv_user_age);
		iv_user_sex = (ImageView) view.findViewById(R.id.iv_user_sex);
		tv_title = (TextView) view.findViewById(R.id.tv_title);

		tv_title.setText(R.string.tab_4);
		iv_user_icon.setImageResource(R.drawable.icon_man);
		lv_user_related = (ListView) view.findViewById(R.id.lv_user_related);

		// 加载适配器
		String[] from = new String[] { "image", "text" };
		int[] to = new int[] { R.id.iv_user_related, R.id.tv_user_related };

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.item_four, from, to);
		lv_user_related.setAdapter(adapter);
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		if (!TextUtils.isEmpty(API.token)) {
			btn_logout.setVisibility(View.GONE);
		}
		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog();
			}
		});
		openList();
	}

	private List<Map<String, Object>> getData() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("image", R.drawable.user_infos);
		// map.put("text", "基本信息");
		// list.add(map);

		// Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("image", R.drawable.user_cases);
		// map1.put("text", "我的病史");
		// list.add(map1);
		//
		// Map<String, Object> map2 = new HashMap<String, Object>();
		// map2.put("image", R.drawable.user_tests);
		// map2.put("text", "辅助检查");
		// list.add(map2);
		//
		// Map<String, Object> map3 = new HashMap<String, Object>();
		// map3.put("image", R.drawable.user_visit);
		// map3.put("text", "随访通知");
		// list.add(map3);

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("image", R.drawable.set_icon1);
		map1.put("text", "修改密码");
		list.add(map1);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("image", R.drawable.set_icon2);
		map2.put("text", "意见反馈");
		list.add(map2);

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("image", R.drawable.set_icon3);
		map3.put("text", "关于我们");
		list.add(map3);

		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("image", R.drawable.set_icon4);
		map4.put("text", "版本号 : " + getString(R.string.version_info));
		list.add(map4);

		return list;
	}

	private void openList() {
		rl_user_center.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("true".equals(loginstate)) {
					// 跳转到用户中心界面
					Intent intent = new Intent(getActivity(),
							UserCenterActivity.class);
					intent.putExtra("avatar", avatar);
					intent.putExtra("userName", userName);
					startActivityForResult(intent, 1);

				} else {
					// 跳转到登陆界面
					Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
				}

			}
		});
		// rl_user_center.setOnTouchListener(this);
		lv_user_related.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				// case 0:
				// 基本信息
				// if (!API.getYouke().equals("游客账号")) {
				// Intent intent = new Intent(getActivity(),
				// UserInfosActivity.class);
				// startActivity(intent);
				//
				// } else {
				// Toast.makeText(getActivity(), "您目前使用的是游客账号，请登录！",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new Intent(getActivity(),
				// LoginActivity.class);
				// startActivity(intent);
				// }
				//
				// break;
				/*
				 * case 1: // 我的病例 if (!API.getYouke().equals("游客账号")) { Intent
				 * intent = new Intent(getActivity(), UserCasesActivity.class);
				 * startActivity(intent);
				 * 
				 * } else { Toast.makeText(getActivity(), "您目前使用的是游客账号，请登录！",
				 * Toast.LENGTH_SHORT).show(); Intent intent = new
				 * Intent(getActivity(), LoginActivity.class);
				 * startActivity(intent); } break; case 2: // 辅助检查 if
				 * (!API.getYouke().equals("游客账号")) { // Intent intent=new //
				 * Intent(getActivity(),TestsActivity.class); //
				 * startActivity(intent); Toast.makeText(getActivity(), "敬请期待！",
				 * Toast.LENGTH_SHORT).show();
				 * 
				 * } else { Toast.makeText(getActivity(), "您目前使用的是游客账号，请登录！",
				 * Toast.LENGTH_SHORT).show(); Intent intent = new
				 * Intent(getActivity(), LoginActivity.class);
				 * startActivity(intent); } break; case 3: // 随访记录 if
				 * (!API.getYouke().equals("游客账号")) { // Intent intent=new //
				 * Intent(getActivity(),VisitInfomActivity.class); //
				 * startActivity(intent); Toast.makeText(getActivity(), "敬请期待！",
				 * Toast.LENGTH_SHORT).show();
				 * 
				 * } else { Toast.makeText(getActivity(), "您目前使用的是游客账号，请登录！",
				 * Toast.LENGTH_SHORT).show(); Intent intent = new
				 * Intent(getActivity(), LoginActivity.class);
				 * startActivity(intent); }
				 * 
				 * break;
				 */
				// 2016年5月6日 12:42:13 jjg 提出个人中心菜单
				case 0:
					Intent intent = null;
					if ("true".equals(loginstate)) {
						intent = new Intent(getActivity(),
								ModiPwdActivity.class);
					} else {
						Toast.makeText(getActivity(), "请先登录！",
								Toast.LENGTH_SHORT).show();
						intent = new Intent(getActivity(), LoginActivity.class);
					}
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(getActivity(), FeedBackActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(getActivity(), AboutUsActivity.class);
					startActivity(intent);
					break;
				case 3:
					Toast.makeText(getActivity(),
							"当前版本：" + getString(R.string.version_info),
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
	}

	/** 获取用户简略信息 */
	public void getUserInfo() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					progressDialog.dismiss();
					try {
						if (result != null) {
							JSONObject jo = new JSONObject(result);
							JSONObject joi = jo.getJSONObject("info");
							userName = joi.getString("mobile");
							tv_user_name.setText(userName);
							String sex = joi.getString("sex");
							if (sex == (1 + "")) {
								iv_user_sex
										.setBackgroundResource(R.drawable.man);
							} else if (sex == (2 + "")) {
								iv_user_sex
										.setBackgroundResource(R.drawable.woman);
							} else if (sex == (0 + "")) {
								iv_user_sex.setVisibility(View.GONE);
							}
							avatar = joi.getString("avatar");
							if (avatar != null && !avatar.equals("")) {
								imageLoader = AsyncLoadingImg
										.getImageLoader(getActivity());
								imageLoader.displayImage(avatar, iv_user_icon,
										AsyncLoadingImg.getDefaultOptions());
							} else {
								iv_user_icon
										.setImageResource(R.drawable.icon_man);
							}
						} else {
							// Toast.makeText(getActivity(), "",
							// Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		new Thread() {
			public void run() {
				result = Postmessage(Url, token);
				Message m = new Message();
				m.what = 1;
				handler.sendMessage(m);
			}
		}.start();
	}

	public String Postmessage(String Url, String token) {
		try {
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "utf-8");
			String data = "token=" + URLEncoder.encode(token, "UTF-8")
					+ "&type=" + URLEncoder.encode("2", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			InputStreamReader is = new InputStreamReader(conn.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(is);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			is.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/** 退出登录提示 **/
	private void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 先得到构造器
		builder.setTitle("提示"); // 设置标题
		builder.setMessage("是否确认退出?"); // 设置内容
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 关闭dialog
						// 将登陆状态保存
						SharedPreferences sharedPreferences = getActivity()
								.getSharedPreferences("loginstate",
										Activity.MODE_MULTI_PROCESS);
						SharedPreferences.Editor editor = sharedPreferences
								.edit();
						editor.putString("loginstate", "false");
						editor.commit();
						new LoginActivity().loginOut_Sample();
						Toast.makeText(getActivity(), "退出登录成功",
								Toast.LENGTH_SHORT).show();
						API.token = "";
						Intent intent = new Intent(getActivity(),
								LoginActivity.class);
						startActivity(intent);
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// 参数都设置完成了，创建并显示出来
		builder.create().show();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(API.token)) {
			btn_logout.setVisibility(View.VISIBLE);
		} else {
			btn_logout.setVisibility(View.GONE);
		}
	}
}
