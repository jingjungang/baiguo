package com.mimi.baiguo.util;



import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class JudgeInternet {
	public static boolean isNetworkAvailable(final Context context) {
		
//		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//			Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
//			return false; 
//		}else{
//			return true;
//		}
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
				.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
		if (mNetworkInfo != null) { 
			return mNetworkInfo.isAvailable(); 
		} else {
			Toast.makeText(context, "当前网络不可用，请检查网络后重试", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	protected static void dialog(final  Context context) {
	
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("当前无网络连接");
		builder.setTitle("提示");
		
		builder.setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());   
			}
		});
		builder.create().show();
	}
}
