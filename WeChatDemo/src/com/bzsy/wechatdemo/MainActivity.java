package com.bzsy.wechatdemo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener{

	private ViewPager viewPager;
	private FragmentPagerAdapter fragmentPagerAdapter;
	private ArrayList<Fragment> fragmentArrayList;
	final int MAX_PAGES = 4;//Tab页面数量
	final int PAGE_CHAT = 0;
	final int PAGE_CONTACT = 1;
	final int PAGE_EXPLOR = 2;
	final int PAGE_MY = 3;
	private boolean isScrollView = true;//true：滑动切换； false：点击底部导航栏切换
	
	//灰色底部导航栏
	private ImageView imageViewChat;
	private ImageView imageViewContact;
	private ImageView imageViewExplor;
	private ImageView imageViewMy;
	private TextView textViewChat;
	private TextView textViewContact;
	private TextView textViewExplor;
	private TextView textViewMy;
	
	//绿色底部导航栏
	private ImageView imageViewChat_s;
	private ImageView imageViewContact_s;
	private ImageView imageViewExplor_s;
	private ImageView imageViewMy_s;
	private TextView textViewChat_s;
	private TextView textViewContact_s;
	private TextView textViewExplor_s;
	private TextView textViewMy_s;
	
	private ImageView[] imageViews;
	private ImageView[] imageViews_s;
	
	private TextView[] textViews;
	private TextView[] textViews_s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		imageViewChat = (ImageView) findViewById(R.id.imageViewChat);
		imageViewContact = (ImageView) findViewById(R.id.imageViewContact);
		imageViewExplor = (ImageView) findViewById(R.id.imageViewExplor);
		imageViewMy = (ImageView) findViewById(R.id.imageViewMy);
		textViewChat = (TextView) findViewById(R.id.textViewChat);
		textViewContact = (TextView) findViewById(R.id.textViewContact);
		textViewExplor = (TextView) findViewById(R.id.textViewExplor);
		textViewMy = (TextView) findViewById(R.id.textViewMy);
		
		imageViewChat_s = (ImageView) findViewById(R.id.imageViewChat_s);
		imageViewContact_s = (ImageView) findViewById(R.id.imageViewContact_s);
		imageViewExplor_s = (ImageView) findViewById(R.id.imageViewExplor_s);
		imageViewMy_s = (ImageView) findViewById(R.id.imageViewMy_s);
		textViewChat_s = (TextView) findViewById(R.id.textViewChat_s);
		textViewContact_s = (TextView) findViewById(R.id.textViewContact_s);
		textViewExplor_s = (TextView) findViewById(R.id.textViewExplor_s);
		textViewMy_s = (TextView) findViewById(R.id.textViewMy_s);
		
		
		imageViews = new ImageView[]{imageViewChat, imageViewContact, imageViewExplor, imageViewMy};
		imageViews_s = new ImageView[]{imageViewChat_s, imageViewContact_s, imageViewExplor_s, imageViewMy_s};
		
		textViews = new TextView[]{textViewChat, textViewContact, textViewExplor, textViewMy};
		textViews_s = new TextView[]{textViewChat_s, textViewContact_s, textViewExplor_s, textViewMy_s};
		
		for (int i = 0; i < MAX_PAGES; i++) {
			imageViews_s[i].setOnClickListener(this);
			textViews_s[i].setOnClickListener(this);
		}
		clearView();
		imageViewChat.setAlpha((float)0);
		imageViewChat_s.setAlpha((float)1);
		textViewChat.setAlpha((float)0);
		textViewChat_s.setAlpha((float)1);		
		
		FragmentChat fragmentChat = new FragmentChat();
		FragmentContact fragmentContact = new FragmentContact();
		FragmentExplor fragmentExplor = new FragmentExplor();
		FragmentMy fragmentMy = new FragmentMy();
		
		fragmentArrayList = new ArrayList<Fragment>();
		fragmentArrayList.add(fragmentChat);
		fragmentArrayList.add(fragmentContact);
		fragmentArrayList.add(fragmentExplor);
		fragmentArrayList.add(fragmentMy);
		
		fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return fragmentArrayList.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return fragmentArrayList.get(arg0);
			}
		};
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(fragmentPagerAdapter);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(arg0 == 1){
			isScrollView = true;
		}
	}

	/**
	 * 通过改变透明度来达到渐变的效果
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (isScrollView) {
			imageViews_s[arg0].setAlpha(1-arg1);
			textViews_s[arg0].setAlpha(1-arg1);
			imageViews[arg0].setAlpha(arg1);
			textViews[arg0].setAlpha(arg1);
			
			if(arg0+1 < MAX_PAGES){
				imageViews_s[arg0+1].setAlpha(arg1);
				textViews_s[arg0+1].setAlpha(arg1);
				imageViews[arg0+1].setAlpha(1-arg1);
				textViews[arg0+1].setAlpha(1-arg1);
			}
		}
		
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}

	/**
	 * 点击导航栏切换页面
	 */
	@Override
	public void onClick(View arg0) {
		int page = 0;
		isScrollView = false;
		switch(arg0.getId()){
		case R.id.imageViewChat_s:
		case R.id.textViewChat_s:
			page = PAGE_CHAT;
			break;
		case R.id.imageViewContact_s:
		case R.id.textViewContact_s:
			page = PAGE_CONTACT;
			break;
		case R.id.imageViewExplor_s:
		case R.id.textViewExplor_s:
			page = PAGE_EXPLOR;
			break;
		case R.id.imageViewMy_s:
		case R.id.textViewMy_s:
			page = PAGE_MY;
			break;
		default:
			break;
		}
		if(viewPager.getCurrentItem() != page){
			viewPager.setCurrentItem(page);
			clearView();
			imageViews_s[page].setAlpha((float)1);
			textViews_s[page].setAlpha((float)1);
			imageViews[page].setAlpha((float)0);
			textViews[page].setAlpha((float)0);
		}
	}
	
	private void clearView(){
		for (int i = 0; i < MAX_PAGES; i++) {
			imageViews_s[i].setAlpha((float)0);
			textViews_s[i].setAlpha((float)0);
			imageViews[i].setAlpha((float)1);
			textViews[i].setAlpha((float)1);
		}
	}
}
