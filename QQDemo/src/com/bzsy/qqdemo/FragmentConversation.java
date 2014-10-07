package com.bzsy.qqdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentConversation extends Fragment {
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater
				.inflate(R.layout.layout_fragment_example, container, false);
		TextView textView = (TextView) v.findViewById(R.id.textView);
		textView.setText("Fragment Conversation");
		return v;
	}
}
