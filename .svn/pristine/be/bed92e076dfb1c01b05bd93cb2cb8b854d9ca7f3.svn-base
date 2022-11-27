package com.mimi.baiguo.activity;
/**
 * 自定义聊天界面的类*/
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.mimi.baiguo.API;
import com.mimi.baiguo.R;
import com.mimi.baiguo.util.SystemBarTintManager;

public class MessageUI extends IMChattingPageUI{

	private TextView tv_title;
	private ImageButton ib_left_handle01;
	private static SystemBarTintManager tintManager;
	public MessageUI(Pointcut pointcut) {
		super(pointcut);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getCustomTitleView(final Fragment fragment, Context context,
			LayoutInflater inflater, YWConversation conversation) {
		// TODO Auto-generated method stub
//		fragment.getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		tintManager = new SystemBarTintManager(fragment.getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.common_theme);
		View view = inflater.inflate(R.layout.fragment_three,new RelativeLayout(context), false);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(API.getDoctorName());
		ib_left_handle01 = (ImageButton) view.findViewById(R.id.ib_left_handle01);
		ib_left_handle01.setBackgroundResource(R.drawable.back_a_normal2x);
		ib_left_handle01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fragment.getActivity().finish();
			}
		});
		return view;
	}

}
