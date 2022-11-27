package com.mimi.baiguo.lunbo;

/**
 * 轮播图-商品或是活动
 * 景俊钢
 * 2016年8月3日 14:21:53
 */
import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mimi.baiguo.R;

public class HealthLifeLunboFragment extends Fragment {
	View root;
	private MyGallery gallery = null;
	private ArrayList<Integer> imgList;
	private ArrayList<ImageView> portImg;
	private int preSelImgIndex = 0;// 存储上一个选择项的Index
	private LinearLayout ll_focus_indicator_container = null;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_hl_lunbo, container, false);
		// AudioManager mAudioManager = (AudioManager) getActivity()
		// .getSystemService(Context.AUDIO_SERVICE);
		// mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 1);//静音
		initView();
		initlunbo();
		return root;
	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	/**
	 * 轮播初始化
	 */
	private void initlunbo() {
		// TODO Auto-generated method stub
		ll_focus_indicator_container = (LinearLayout) root
				.findViewById(R.id.ll_focus_indicator_container);
		imgList = new ArrayList<Integer>();
		imgList.add(R.drawable.lunbo1);
		imgList.add(R.drawable.lunbo2);
		imgList.add(R.drawable.lunbo3);
		InitFocusIndicatorContainer();
		gallery = (MyGallery) root.findViewById(R.id.gallery);
		gallery.setAdapter(new ImgAdapter(getActivity(), imgList));
		gallery.setFocusable(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int selIndex, long arg3) {
				selIndex = selIndex % imgList.size();
				// 修改上一次选中项的背景
				portImg.get(preSelImgIndex).setImageResource(
						R.drawable.ic_focus);
				// 修改当前选中项的背景
				portImg.get(selIndex).setImageResource(
						R.drawable.ic_focus_select);
				preSelImgIndex = selIndex;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	/**
	 * 初始化指示器
	 */
	private void InitFocusIndicatorContainer() {
		portImg = new ArrayList<ImageView>();
		for (int i = 0; i < imgList.size(); i++) {
			ImageView localImageView = new ImageView(getActivity());
			localImageView.setId(i);
			ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			localImageView.setScaleType(localScaleType);
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					24, 24);
			localImageView.setLayoutParams(localLayoutParams);
			localImageView.setPadding(5, 5, 5, 5);
			localImageView.setImageResource(R.drawable.ic_focus);
			portImg.add(localImageView);
			this.ll_focus_indicator_container.addView(localImageView);
		}
	}
}
