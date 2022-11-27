package com.mimi.baiguo;

import com.alibaba.wxlib.util.SysUtil;
import com.umeng.openim.OpenIMAgent;
import com.umeng.socialize.PlatformConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

public class BaiGuoApplication extends MultiDexApplication {

	private static Context sContext;
	public String User_doctor = "";

	public static Context getContext() {
		return sContext;
	}

	@Override
	public void onCreate() {
		// 微信
		PlatformConfig.setWeixin("wx5fa1388508b9cdc6",
				"39e3bf90c5c2ae7ff5fbd9380c49be5d");

		if (mustRunFirstInsideApplicationOnCreate()) {
			return;
		}
		InitHelper.initYWSDK(this);
		OpenIMAgent im = OpenIMAgent.getInstance(this);
		im.init();

	}

	private boolean mustRunFirstInsideApplicationOnCreate() {
		SysUtil.setApplication(this);
		sContext = getApplicationContext();
		return SysUtil.isTCMSServiceProcess(sContext);
	}

	public void WriteSetting(boolean flag, boolean isNewVersion) {
		SharedPreferences pref = getSharedPreferences("setting_info",
				MODE_PRIVATE);
		SharedPreferences.Editor sharedata = pref.edit();
		sharedata.putBoolean("first_load", flag);
		sharedata.putBoolean("new_version", isNewVersion);
		sharedata.putString("version_code", API.version);
		sharedata.commit();
	}

}
