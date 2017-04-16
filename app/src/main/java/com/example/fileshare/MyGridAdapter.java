package com.example.fileshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] images_text = { "局域网扫描", "图像捕捉", "图像裁剪", "图像合成", "用户设置" };
	public int[] images = { R.drawable.network_scanning, R.drawable.image_capture,
			R.drawable.image_clipping, R.drawable.image_synthesis, R.drawable.user_setting };

	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.text_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.image_item);
		iv.setBackgroundResource(images[position]);

		tv.setText(images_text[position]);
		return convertView;
	}

}
