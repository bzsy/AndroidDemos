package com.bzsy.wechatdemo;

import java.util.ArrayList;
import java.util.HashMap;

import com.bzsy.wechatdemo.MyListView.LoadDataListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class FragmentChat extends Fragment implements LoadDataListener {

	private MyListView listView;
	private SimpleAdapter simpleAdapter;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

	private int[] chatIcons = new int[] { R.drawable.chat1, R.drawable.chat2,
			R.drawable.chat3, R.drawable.chat4, R.drawable.chat5,
			R.drawable.chat6, R.drawable.chat7, R.drawable.chat8,
			R.drawable.chat9, R.drawable.chat10 };
	private String[] weekDays = new String[] { "周日", "周六", "周五", "周四", "周三",
			"周二", "周一" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_fragment_chat, container,
				false);

		listView = (MyListView) v.findViewById(R.id.chatListView);
		listView.setLoadDataListener(this);
		String[] from = new String[] { "ItemImage", "ItemTitle", "ItemText",
				"ItemTime" };
		int[] to = new int[] { R.id.ListItemImage, R.id.ListItemTitle,
				R.id.ListItemText, R.id.ListItemTime };
		initData();
		simpleAdapter = new SimpleAdapter(
				getActivity().getApplicationContext(), arrayList,
				R.layout.layout_list_item, from, to);
		listView.setAdapter(simpleAdapter);
		return v;
	}

	@Override
	public void refresh() {
		new Handler().postDelayed(new Runnable() {// 模拟刷新延时

					@Override
					public void run() {
						arrayList.clear();
						initData();
						simpleAdapter.notifyDataSetChanged();
						listView.refreshDataFinished();
					}
				}, 1000);
	}

	/**
	 * 恢复列表初始化数据
	 * 
	 * @param num
	 */
	private void initData() {
		for (int i = 0; i < 20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", chatIcons[i % 10]);
			map.put("ItemTitle", "联系人 " + (i + 1));
			map.put("ItemText", "最近对话内容 " + (i + 1));
			map.put("ItemTime", weekDays[i % 7]);
			arrayList.add(map);
		}
	}
}
