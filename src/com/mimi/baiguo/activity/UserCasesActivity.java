package com.mimi.baiguo.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;
/***
 * 患者病例
 * @author AAA
 *
 */
public class UserCasesActivity extends Activity {

	private TextView tv_title;
	private ImageButton btnBack;
/**添加时间，更新时间， 是否饮酒，饮酒频率，每次饮酒量，酒精度数，过敏史，添加过敏史，手术史，添加手术史，输血史，添加输血史， 家族史，药物临床试验史，添加药物临床试验史，
 * 是否参加药物临床试验史，参加药物临床试验史开始时间，参加药物临床试验史结束时间 */
	private TextView tvAddTime,  tvUpdateTime, tvDrink,tvDrinktime,tvDrinknumber,tvDrinkdegree,
			tvAllergy,tvAllergys,tvOperation,tvOperations,tvBlood,tvBloods,tvFamily,tvDrug,tvDrugs,tvDrugtest,tvStime,tvEtime;
	
	private String Url=API.USER_CASE_URL;
	private static SystemBarTintManager tintManager;
	String Token=API.getToken();
	String result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.common_theme);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.activity_user_cases);

		init();
		LoginActivity.startProgressDialog(this);
		getUserInfos();
	}

	private void init() {
		tvAddTime=(TextView)findViewById(R.id.tv_add_time);
		tvUpdateTime=(TextView)findViewById(R.id.tv_update_time);
		tvDrink=(TextView)findViewById(R.id.tv_drink);
		tvDrinktime=(TextView)findViewById(R.id.tv_drinktime);
		tvDrinknumber=(TextView)findViewById(R.id.tv_drinknumber);
		tvDrinkdegree=(TextView)findViewById(R.id.tv_drinkdegree);
		tvAllergy=(TextView)findViewById(R.id.tv_allergy);
		tvAllergys=(TextView)findViewById(R.id.tv_allergys);
		tvOperation=(TextView)findViewById(R.id.tv_operation);
		tvOperations=(TextView)findViewById(R.id.tv_operations);
		tvBlood=(TextView)findViewById(R.id.tv_blood);
		tvBloods=(TextView)findViewById(R.id.tv_bloods);
		tvFamily=(TextView)findViewById(R.id.tv_family);
		tvDrug=(TextView)findViewById(R.id.tv_drug);
		tvDrugs=(TextView)findViewById(R.id.tv_drugs);
		tvDrugtest=(TextView)findViewById(R.id.tv_drugtest);
		tvStime=(TextView)findViewById(R.id.tv_stime);
		tvEtime=(TextView)findViewById(R.id.tv_etime);
		
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(R.string.user_cases);
		btnBack=(ImageButton) findViewById(R.id.ib_left_handle01);
		btnBack.setBackgroundResource(R.drawable.back_a_normal2x);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
public void getUserInfos() {
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					LoginActivity.stopProgressDialog();
					try {
						JSONObject jo = new JSONObject(result);
						int status = jo.getInt("status");
						switch(status){
						case -1: 
							Toast.makeText(UserCasesActivity.this, "userid不存在",Toast.LENGTH_SHORT).show();
							break;
						case -2: 
							Toast.makeText(UserCasesActivity.this, "未获取到用户信息",Toast.LENGTH_SHORT).show();
							break;
						case -3: 
							Toast.makeText(UserCasesActivity.this, "用户对应病例信息不存在",Toast.LENGTH_SHORT).show();
							break;
						case 1:
				        	JSONObject joi = jo.getJSONObject("info");
				        	tvAddTime.setText(joi.getString("add_time")) ;
				        	tvUpdateTime.setText(joi.getString("update_time")) ;
				        	tvDrink.setText(joi.getString("drink")) ;
				        	tvDrinktime.setText(joi.getString("drinktime")) ;
				        	tvDrinknumber.setText(joi.getString("drinknumber")) ;
				        	tvDrinkdegree.setText(joi.getString("drinkdegree")) ;
				        	tvAllergy.setText(joi.getString("allergy")) ;
				        	tvAllergys.setText(joi.getString("allergys")) ;
				        	tvOperation.setText(joi.getString("operation")) ;
				        	tvOperations.setText(joi.getString("operations")) ;
				        	tvBlood.setText(joi.getString("blood")) ;
				        	tvBloods.setText(joi.getString("bloods")) ;
				        	tvFamily.setText(joi.getString("family")) ;
				        	tvDrug.setText(joi.getString("drug")) ;
				        	tvDrugs.setText(joi.getString("drugs")) ;
				        	tvDrugtest.setText(joi.getString("drugtest")) ;
				        	tvStime.setText(joi.getString("stime")) ;
				        	tvEtime.setText(joi.getString("etime")) ;
							break;
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
				result = Postmessage(Url, Token);
				Message m = new Message();
				m.what = 1;
				handler.sendMessage(m);
			}
		}.start();
	}
	
	public String Postmessage(String Url, String Token) {

		// String value="";
		try {
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "utf-8");
			String data = "token=" + URLEncoder.encode(Token, "UTF-8");
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
	
	
}
