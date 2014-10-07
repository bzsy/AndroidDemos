package com.bzsy.qqdemo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;

public class FragmentViewPager extends Fragment implements OnClickListener {

	private View v;
	private ViewPager viewPager;
	private FragmentPagerAdapter fragmentViewPager;
	private ArrayList<Fragment> arrayListFragment;
	private ImageView tabConversation_s;
	private ImageView tabContact_s;
	private ImageView tabPlugin_s;
	private ImageView[] tabs;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		v.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) v.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.layout_viewpager, container, false);

		(tabConversation_s = (ImageView) v
				.findViewById(R.id.skin_tab_icon_conversation_selected))
				.setOnClickListener(this);
		(tabContact_s = (ImageView) v
				.findViewById(R.id.skin_tab_icon_contact_selected))
				.setOnClickListener(this);
		(tabPlugin_s = (ImageView) v
				.findViewById(R.id.skin_tab_icon_plugin_selected))
				.setOnClickListener(this);
		tabs = new ImageView[] { tabConversation_s, tabContact_s, tabPlugin_s };
		clearTab();
		tabConversation_s.setAlpha(1f);

		viewPager = (ViewPager) v.findViewById(R.id.viewPager);
		arrayListFragment = new ArrayList<Fragment>();
		arrayListFragment.add(new FragmentConversation());
		arrayListFragment.add(new FragmentContact());
		arrayListFragment.add(new FragmentPlugin());
		fragmentViewPager = new FragmentPagerAdapter(getFragmentManager()) {

			@Override
			public int getCount() {

				return arrayListFragment.size();
			}

			@Override
			public Fragment getItem(int arg0) {

				return arrayListFragment.get(arg0);
			}
		};

		viewPager.setAdapter(fragmentViewPager);

		return v;
	}

	@Override
	public void onClick(View arg0) {
		clearTab();
		int item_num = 0;
		switch (arg0.getId()) {
		case R.id.skin_tab_icon_conversation_selected:
			item_num = 0;
			break;

		case R.id.skin_tab_icon_contact_selected:
			item_num = 1;
			break;

		case R.id.skin_tab_icon_plugin_selected:
			item_num = 2;
			break;

		default:
			break;
		}
		tabs[item_num].setAlpha(1f);
		viewPager.setCurrentItem(item_num);
	}

	/**
	 * 取消底部Tab选中状态
	 */
	private void clearTab() {
		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setAlpha(0f);
		}

	}
}
