package com.mimi.baiguo.sample;

import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.mimi.baiguo.activity.MessageUI;
import com.mimi.baiguo.main.TwoFragmentUI;

/**
 * IM定制化初始化统一入口，这里后续会增加更多的IM定制化功�?
 *
 * @author zhaoxu
 */
public class CustomSampleHelper {

    private static String TAG=CustomSampleHelper.class.getSimpleName();

    public static void initCustom() {
    	//自定义消息类提交（不提交无法更换自定义UI）
    	AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, TwoFragmentUI.class);
    	//自定义聊天类提交
    	AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, MessageUI.class);
/*
        //聊天界面相关自定�?-------
        //聊天界面的自定义风格1：［图片、文字小猪气泡］风格
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustomSample.class);
        //聊天界面的自定义风格2：［图片切割气泡、文字小猪气泡］风格
//        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustomSample2.class);
        //-----------------------
        //聊天业务相关
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_OPERATION_POINTCUT, ChattingOperationCustomSample.class);
        //会话列表UI相关
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationListUICustomSample.class);
        //会话列表业务相关
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_OPERATION_POINTCUT, ConversationListOperationCustomSample.class);
        //消息通知�?
        AdviceBinder.bindAdvice(PointCutEnum.NOTIFICATION_POINTCUT, NotificationInitSampleHelper.class);
        //联系人界面UI相关
        AdviceBinder.bindAdvice(PointCutEnum.CONTACTS_UI_POINTCUT, ContactsUICustomSample.class);
        //联系人界面业务相�?
        AdviceBinder.bindAdvice(PointCutEnum.CONTACTS_OP_POINTCUT, ContactsOperationCustomSample.class);
        //全局配置修改
        AdviceBinder.bindAdvice(PointCutEnum.YWSDK_GLOBAL_CONFIG_POINTCUT, YWSDKGlobalConfigSample.class);

        //@消息界面
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_FRAGMENT_AT_MSG_DETAIL, SendAtMsgDetailUISample.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_AT_MSG_LIST, AtMsgListUISample.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_SELECT_AT_MEMBER, SelectTribeAtMemberSample.class);*/
    }
}
