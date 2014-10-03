package com.bzsy.listdemo;

import java.util.ArrayList;
import java.util.HashMap;

import com.bzsy.listdemo.MyListView.LoadDataListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements LoadDataListener{

	private MyListView listView;
	private SimpleAdapter simpleAdapter;
	private int totalItemCount = 0;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (MyListView) findViewById(R.id.List);
		listView.setLoadDataListener(this);
		String[] from = new String[]{"ItemImage", "ItemTitle", "ItemText"};
		int[] to = new int[]{R.id.ListItemImage, R.id.ListItemTitle, R.id.ListItemText};
		addData(10);
		simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.layout_list_item, from, to);
		listView.setAdapter(simpleAdapter);
	}
	
	private void addData(int num){
		for (int i = totalItemCount, n = i+num; i < n; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.ic_launcher);
	        map.put("ItemTitle", "Title " + (i+1));
	        map.put("ItemText", "Text " + (i+1));
	        arrayList.add(map);
		}
		totalItemCount += num;
	}

	/**
	 * 加载数据
	 */
	@Override
	public void loadData() {
		new Handler().postDelayed(new Runnable() {//模拟加载延时
			
			@Override
			public void run() {
				addData(5);
				simpleAdapter.notifyDataSetChanged();
				listView.loadDataFinished();
			}
		}, 1000);
		
	}

	@Override
	public void refresh() {
		new Handler().postDelayed(new Runnable() {//模拟刷新延时
			
			@Override
			public void run() {
				arrayList.clear();
				totalItemCount = 0;
				addData(10);
				simpleAdapter.notifyDataSetChanged();
				listView.refreshDataFinished();
			}
		}, 1000);
		
	}
}
