/*
 * ImageInfo.java
 * Homework - 01
 * Members: Suresh Appana
 * 			Midhun Mathew Sunny
 * 			Rohan
 * 
 */

package com.example.selectiongame;

public class ImageInfo {
	int fruitId;
	String fruitName;

	public ImageInfo(int fruitId, String fruitName) {
		super();
		this.fruitId = fruitId;
		this.fruitName = fruitName;
	}

	public int getFruitId() {
		return fruitId;
	}

	public void setFruitId(int fruitId) {
		this.fruitId = fruitId;
	}

	public String getFruitName() {
		return fruitName;
	}

	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}

}
