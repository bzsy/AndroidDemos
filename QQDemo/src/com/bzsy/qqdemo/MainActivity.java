package com.bzsy.qqdemo;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends ActionBarActivity {

	private int maxMargin = 0;
	private FragmentSlidingMenu menuFragment;
	private FragmentViewPager contentFragment;
	private DisplayMetrics displayMetrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);

		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		maxMargin = displayMetrics.heightPixels / 10;

		menuFragment = new FragmentSlidingMenu();
		contentFragment = new FragmentViewPager();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingMenu, menuFragment);
		transaction.replace(R.id.content, contentFragment);
		transaction.commit();

		SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingPaneLayout);
		slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {

			/**
			 * 缩放效果，via：http://www.eoeandroid.com/thread-545123-1-1.html
			 */
			@Override
			public void onPanelSlide(View arg0, float slideOffset) {
				int contentMargin = (int) (slideOffset * maxMargin);
				FrameLayout.LayoutParams contentParams = contentFragment
						.getCurrentViewParams();
				contentParams.setMargins(0, contentMargin, 0, contentMargin);
				contentFragment.setCurrentViewPararms(contentParams);

				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
						/ (float) displayMetrics.heightPixels;
				menuFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
				menuFragment.getCurrentView().setPivotY(
						displayMetrics.heightPixels / 2);
				menuFragment.getCurrentView().setAlpha(slideOffset);
				menuFragment.getCurrentView().setTranslationX(
						-100 + slideOffset * 100);
			}

			@Override
			public void onPanelOpened(View arg0) {

			}

			@Override
			public void onPanelClosed(View arg0) {

			}
		});
	}

}
