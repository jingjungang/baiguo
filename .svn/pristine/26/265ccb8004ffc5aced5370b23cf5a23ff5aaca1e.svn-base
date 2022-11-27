package com.mimi.baiguo.main;
/**
 * 消息界面UI*/
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.mimi.baiguo.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TwoFragmentUI extends IMConversationListUI{

	private TextView tv_title;
	public TwoFragmentUI(Pointcut pointcut) {
		super(pointcut);
		// TODO Auto-generated constructor stub
	}

	public View getCustomConversationListTitle(Fragment fragment, Context context, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_two_ui, null);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		
		tv_title.setText(R.string.tab_2);
	    return view;
	}
	/**
	 * 该方法可以构造一个会话列表为空时的展示View
	 * @return
	 *      empty view
	 */
	@Override
	public View getCustomEmptyViewInConversationUI(Context context) {
	    /** 以下为示例代码，开发者可以按需返回任何view*/
	    TextView textView = new TextView(context);
	    textView.setText("还没有会话哦，快去找人聊聊吧!");
	    textView.setGravity(Gravity.CENTER);
	    textView.setTextSize(18);
	    return textView;
	}
}
