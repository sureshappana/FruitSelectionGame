/*
 * ImageAdapter.java
 * Homework - 01
 * Members: Suresh Appana
 * 			Midhun Mathew Sunny
 * 			Rohan
 * 
 */

package com.example.selectiongame;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	int[] mThumbIds;
	ArrayList<ImageInfo> imageInfo;
	int[] imageAlphaList;
	
	public ImageAdapter(Context mContext, int[] mThumbIds,
			ArrayList<ImageInfo> imageInfo, int[] imageAlphaList) {
		super();
		this.mContext = mContext;
		this.mThumbIds = mThumbIds;
		this.imageInfo = imageInfo;
		this.imageAlphaList = imageAlphaList;
	}

	public int[] getmThumbIds() {
		return mThumbIds;
	}

	public int[] getImageAlphaList() {
		return imageAlphaList;
	}

	public void setImageAlphaList(int[] imageAlphaList) {
		this.imageAlphaList = imageAlphaList;
	}

	public void setmThumbIds(int[] mThumbIds) {
		this.mThumbIds = mThumbIds;
	}

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(1, 1, 1, 1);
		} else {
			imageView = (ImageView) convertView;
		}
		ImageInfo imageDetails = imageInfo.get(mThumbIds[position]);

		imageView.setImageResource(imageDetails.getFruitId());
		imageView.setTag(imageDetails.getFruitName());
		if(imageAlphaList[position] == 1)
		imageView.setAlpha(MainActivity.ALPHA_FACTOR);
		return imageView;
	}
}