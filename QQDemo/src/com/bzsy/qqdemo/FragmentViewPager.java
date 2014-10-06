package com.bzsy.qqdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class FragmentViewPager extends Fragment {

	private View v;
	private ViewPager viewPager;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		v.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) v.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.layout_viewpager, container, false);
		viewPager = (ViewPager) v.findViewById(R.id.viewPager);
		return v;
	}
}
