package com.bzsy.listdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyListView extends ListView implements OnScrollListener{

	private View footer;
	private int lastVisibleItem = 0;
	private int totalItemCount = 0;
	private boolean isLoading = false;
	private LoadDataListener loadDataListener;
	
	
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Init(context);
	}
	
	private void Init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.layout_loading, null);
		footer.findViewById(R.id.loading).setVisibility(View.GONE);//设置默认不显示
		this.addFooterView(footer);
		
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
		if (!isLoading && lastVisibleItem==totalItemCount) {//判断是否已经到达最底端，放在onScroll()因为操作更顺畅
			isLoading = true;
			footer.findViewById(R.id.loading).setVisibility(View.VISIBLE);
			loadDataListener.loadData();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	public interface LoadDataListener{//加载更多数据的接口
		public void loadData();
	}
	
	public void setLoadDataListener(LoadDataListener loadDataListener){
		this.loadDataListener = loadDataListener;
		this.setOnScrollListener(this);//如果放在init()中，onScroll()中loadDataListener仍为null会报错。除非把加载更多数据放在onScrollStateChanged()中
	}

	public void loadDataFinished(){
		footer.findViewById(R.id.loading).setVisibility(View.GONE);
		isLoading = false;
	}
}
