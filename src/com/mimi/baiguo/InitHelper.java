package com.mimi.baiguo;

import android.app.Application;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.wxlib.util.SysUtil;
import com.mimi.baiguo.sample.CustomSampleHelper;
import com.mimi.baiguo.sample.LoginSampleHelper;

/**
 * SDK 全局初始�?
 * 
 * @author shuheng
 */
public class InitHelper {

	public static void initYWSDK(Application application){
		//TODO ע�⣺--------------------------------------
				//  ���²������˳�����ϸ�Ҫ���밴��ʾ���Ĳ��裨todo step��
				// ��˳����ã�
				//TODO --------------------------------------------

				// ------[todo step1]-------------
				//��IM���Ƴ�ʼ���ݣ��������Ҫ���ƻ�������ȥ���˷����ĵ���
				//todo ע�⣺��������ȫ�ֳ�ʼ����������������ִ�У�

		CustomSampleHelper.initCustom();

		// ------[todo step2]-------------
				//SDK��ʼ��
		LoginSampleHelper.getInstance().initSDK_Sample(application);

		//���ڽ�ʹ��Override�ķ�ʽ���м������ã������YWSDKGlobalConfigSample
		YWAPI.enableSDKLogOutput(true);

//		IYWContactService.enableBlackList();

//		YWAPI.setEnableAutoLogin(false);
	}
}
