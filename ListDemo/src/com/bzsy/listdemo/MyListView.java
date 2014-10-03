package com.bzsy.listdemo;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyListView extends ListView implements OnScrollListener{

	private View footer;
	private View header;
	private int headerMeasuredHeight;//下拉刷新的header布局的高度
	private int lastVisibleItem = 0;
	private boolean isLoading = false;//正在载入更多数据
	private LoadDataListener loadDataListener;
	
	final int REFRESH_STATE_UNUSABLE = -1;//列表不位于顶端
	final int REFRESH_STATE_NORMAL = 0;//正常状态
	final int REFRESH_STATE_PULL = 1;//下拉状态
	final int REFRESH_STATE_RELEASE = 2;//松开可以刷新
	final int REFRESH_STATE_REFRESHING = 3;//刷新中
	final int REFRESH_STATE_FINISHED = 4;//刷新完毕
	private int refreshState = REFRESH_STATE_NORMAL;//下拉刷新状态
	private int firstVisibleItem = 0;
	private int startY;
	
	private TextView headerTextView;
	private ProgressBar headerProgressBar;
	
	public MyListView(Context context) {
		super(context);
		Init(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init(context);
	}
	
	private void Init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.layout_loading, null);
		footer.findViewById(R.id.loading).setVisibility(View.GONE);//设置默认不显示
		this.addFooterView(footer);
		
		header = inflater.inflate(R.layout.layout_loading, null);
		this.addHeaderView(header);
		measureView(header);
		headerMeasuredHeight = header.getMeasuredHeight();
		setHeaderPaddingTop(-headerMeasuredHeight);
		header.invalidate();
		headerTextView = (TextView) header.findViewById(R.id.loadingTextView);
		headerProgressBar = (ProgressBar) header.findViewById(R.id.loadingProgressBar);
	}
	
	/**
	 * 设置Header位置
	 * @param top
	 */
	private void setHeaderPaddingTop(int top){
		if(top>headerMeasuredHeight){
			top = headerMeasuredHeight;
		}
		header.setPadding(header.getPaddingLeft(), top, header.getPaddingRight(), header.getPaddingBottom());
	}
	
	/**
	 * 通知父布局，布局所占的尺寸
	 * @param v
	 */
	private void measureView(View v){
		ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
		if (null == layoutParams) {
			layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
		int height;
		if (layoutParams.height > 0) {
			height = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
		}else{
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		v.measure(width, height);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastVisibleItem = firstVisibleItem + visibleItemCount;
		if (!isLoading && lastVisibleItem==totalItemCount) {//判断是否已经到达最底端，放在onScroll()因为操作更顺畅
			isLoading = true;
			footer.findViewById(R.id.loading).setVisibility(View.VISIBLE);
			loadDataListener.loadData();
		}
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE && 0 == firstVisibleItem) {//判断是否处于下拉刷新可用的状态，即列表位与顶端
			refreshState = REFRESH_STATE_NORMAL;
		}else{
			refreshState = REFRESH_STATE_UNUSABLE;
		}
	}
	
	public interface LoadDataListener{
		public void loadData();//加载更多数据
		public void refresh();//下拉刷新
	}
	
	public void setLoadDataListener(LoadDataListener loadDataListener){
		this.loadDataListener = loadDataListener;
		this.setOnScrollListener(this);//如果放在init()中，onScroll()中loadDataListener仍为null会报错。除非把加载更多数据放在onScrollStateChanged()中
	}

	/**
	 * 加载更多数据完毕
	 */
	public void loadDataFinished(){
		footer.findViewById(R.id.loading).setVisibility(View.GONE);
		isLoading = false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int len = 0;
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(refreshState == REFRESH_STATE_NORMAL){
				startY = (int) ev.getY();
			}else{
				startY = -1;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(startY >= 0){
				len = (int) ev.getY() - startY;
				if(len > headerMeasuredHeight){
					refreshState = REFRESH_STATE_RELEASE;
				}else if(len > 0){
					refreshState = REFRESH_STATE_PULL;
				}else{
					refreshState = REFRESH_STATE_NORMAL;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(refreshState == REFRESH_STATE_RELEASE){
				refreshState = REFRESH_STATE_REFRESHING;
				loadDataListener.refresh();
			}
			break;
		default:
				break;
		}
		if(startY > 0){
			if(refreshState!=REFRESH_STATE_REFRESHING)
				setHeaderPaddingTop(len-headerMeasuredHeight);
			updataView(refreshState);
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 下拉时更新Header中的提示内容
	 * @param refreshState
	 */
	private void updataView(int refreshState) {
		
		switch(refreshState){
		case REFRESH_STATE_PULL:
			headerProgressBar.setVisibility(View.INVISIBLE);
			headerTextView.setText("↓ Pull to refresh");//TODO：可以在Header布局里加个箭头图标
			break;
		case REFRESH_STATE_RELEASE:
			headerProgressBar.setVisibility(View.INVISIBLE);
			headerTextView.setText("↑ Release to refresh");
			break;
		case REFRESH_STATE_REFRESHING:
			headerProgressBar.setVisibility(View.VISIBLE);
			headerTextView.setText("Refreshing...");
			break;
		}
		
	}

	/**
	 * 下拉刷新完毕
	 */
	public void refreshDataFinished(){
		refreshState = REFRESH_STATE_NORMAL;
		headerProgressBar.setVisibility(View.INVISIBLE);
		headerTextView.setText("✓ Refresh Success");
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				setHeaderPaddingTop(-headerMeasuredHeight);
			}
		}, 500);
	}
}
