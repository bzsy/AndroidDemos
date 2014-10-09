package com.bzsy.wechatdemo;

import java.util.ArrayList;
import java.util.HashMap;

import com.bzsy.wechatdemo.MyListView.LoadDataListener;
import com.jauker.widget.BadgeView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FragmentChat extends Fragment implements LoadDataListener,
		OnItemLongClickListener {

	private MyListView listView;
	private SimpleAdapter simpleAdapter;
	private PopupWindow popupWindow;
	private Button btnDeleteItem;
	private TextView popupTitle;
	private View v;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	private int[] chatIcons = new int[] { R.drawable.chat1, R.drawable.chat2,
			R.drawable.chat3, R.drawable.chat4, R.drawable.chat5,
			R.drawable.chat6, R.drawable.chat7, R.drawable.chat8,
			R.drawable.chat9, R.drawable.chat10 };
	private String[] weekDays = new String[] { "周日", "周六", "周五", "周四", "周三",
			"周二", "周一" };
	final int ITEM_DEL = 1;// contextmenu中的删除项
	private int item_slected;// contextmenu对应的列表项

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.layout_fragment_chat, container, false);

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
		listView.setOnItemLongClickListener(this);

		View popupView = inflater.inflate(R.layout.popup_window, null);
		popupTitle = (TextView) popupView.findViewById(R.id.popupTitle);
		btnDeleteItem = (Button) popupView.findViewById(R.id.deleteItemBtn);
		btnDeleteItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				arrayList.remove(item_slected);
				simpleAdapter.notifyDataSetChanged();
				popupWindow.dismiss();
			}
		});

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		popupWindow.setFocusable(true);

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

	/**
	 * ContextMenu
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo mMenuInfo = (AdapterContextMenuInfo) menuInfo;
		item_slected = mMenuInfo.position - 1;
		menu.setHeaderTitle(arrayList.get(item_slected).get("ItemTitle")
				.toString());
		menu.add(0, 1, 0, "删除该聊天");

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			arrayList.remove(item_slected);
			simpleAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		item_slected = arg2 - 1;
		popupTitle.setText(arrayList.get(item_slected).get("ItemTitle")
				.toString());
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
		return false;
	}
}
