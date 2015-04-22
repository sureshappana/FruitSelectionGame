/*
 * MainActivity.java
 * Homework - 01
 * Members: Suresh Appana
 * 			Midhun Mathew Sunny
 * 			Rohan
 * 
 */

package com.example.selectiongame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	int MAX_RANDOM = 8;
	int MIN_RANDOM = 1;
	int MATRIX_GRID_SIZE = 5;
	int MATRIX_SIZE = MATRIX_GRID_SIZE * MATRIX_GRID_SIZE;
	static float ALPHA_FACTOR = 0.30f;

	ArrayList<ImageInfo> imageInfo;
	static GridView gridview = null;
	private int fruitIds[];
	private int focusImageId = -1;
	private Random randomGenerator = null;
	private int timesFocusImageSelected = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageInfo = new ArrayList<ImageInfo>();

		imageInfo.add(new ImageInfo(R.drawable.apple, "APPLE"));
		imageInfo.add(new ImageInfo(R.drawable.lemon, "LEMON"));
		imageInfo.add(new ImageInfo(R.drawable.mango, "MANGO"));
		imageInfo.add(new ImageInfo(R.drawable.peach, "PEACH"));
		imageInfo.add(new ImageInfo(R.drawable.strawberry, "STRAWBERRY"));
		imageInfo.add(new ImageInfo(R.drawable.tomato, "TOMATO"));

		fruitIds = new int[MATRIX_SIZE];

		randomGenerator = new Random();

		generateImages();

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setOnItemClickListener(gridViewOnItemClicked);

		Button btn = (Button) findViewById(R.id.resetBtn);
		btn.setOnClickListener(this);

		btn = (Button) findViewById(R.id.exitBtn);
		btn.setOnClickListener(this);
	}

	// Listener for GridView items
	OnItemClickListener gridViewOnItemClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {

			if (0 == timesFocusImageSelected) {
				Toast.makeText(MainActivity.this,
						getString(R.string.allSelectedWarningMessage),
						Toast.LENGTH_SHORT).show();
				return;
			}
			ImageView image = (ImageView) arg0.getChildAt(position);
			if (v.getTag().toString() == (imageInfo.get(focusImageId))
					.getFruitName()) {
				if (image.getAlpha() != ALPHA_FACTOR) {
					image.setAlpha(ALPHA_FACTOR);
					timesFocusImageSelected--;
					TextView selectedFruit = (TextView) findViewById(R.id.selectedFruitLabel);
					selectedFruit.setText(getString(R.string.focusLabel) + " "
							+ (imageInfo.get(focusImageId)).getFruitName()
							+ "(" + timesFocusImageSelected + ")");
					shuffleImages(arg0);
				}
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage(getString(R.string.errorMessage))
						.setNegativeButton(getString(R.string.reset),
								errorDialogClickListener)
						.setPositiveButton(getString(R.string.exitBtn),
								errorDialogClickListener).show();

			}
			if (timesFocusImageSelected == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle(getString(R.string.successMessageHeading));
				builder.setMessage(
						getString(R.string.successMessage) + " "
								+ (imageInfo.get(focusImageId)).getFruitName()
								+ "!!")
						.setNegativeButton(getString(R.string.ok),
								selectionCompleteDialogClickListener)
						.setPositiveButton(getString(R.string.newgame),
								selectionCompleteDialogClickListener).show();
			}

		}

		private void shuffleImages(AdapterView<?> arg0) {

			int[] imageAlphaList = new int[MATRIX_SIZE];
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < MATRIX_SIZE; i++) {
				ImageView image = (ImageView) arg0.getChildAt(i);
				if (image.getAlpha() != ALPHA_FACTOR) {
					list.add(i);
					imageAlphaList[i] = 0;
				} else
					imageAlphaList[i] = 1;
			}
			Collections.shuffle(list);
			int[] shuffled_fruitIds = new int[MATRIX_SIZE];
			shuffled_fruitIds = fruitIds.clone();
			for (int i = 0, j = 0; i < MATRIX_SIZE; i++) {
				ImageView image = (ImageView) arg0.getChildAt(i);
				if (image.getAlpha() != ALPHA_FACTOR) {
					shuffled_fruitIds[i] = fruitIds[list.get(j++)];
				}
			}
			fruitIds = shuffled_fruitIds.clone();
			gridview = (GridView) findViewById(R.id.gridview);
			gridview.setAdapter(new ImageAdapter(MainActivity.this,
					shuffled_fruitIds, imageInfo, imageAlphaList));

		}

	};

	// Listener for Alert Dialog
	DialogInterface.OnClickListener errorDialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			case DialogInterface.BUTTON_NEGATIVE:
				generateImages();
				break;

			case DialogInterface.BUTTON_POSITIVE:
				MainActivity.this.finish();
				System.exit(0);
				break;
			}
		}
	};

	DialogInterface.OnClickListener selectionCompleteDialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			case DialogInterface.BUTTON_NEGATIVE:
				break;

			case DialogInterface.BUTTON_POSITIVE:
				generateImages();
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.resetBtn) {
			generateImages();
		} else if (v.getId() == R.id.exitBtn) {
			MainActivity.this.finish();
			System.exit(0);
		}

	}

	private void generateImages() {

		Arrays.fill(fruitIds, -1); // Initializing array to -1
		focusImageId = randomGenerator.nextInt(imageInfo.size());
		int repeatFocusImage = randomGenerator.nextInt(MAX_RANDOM - MIN_RANDOM
				+ 1) + 1;

		timesFocusImageSelected = repeatFocusImage;
		Log.d("selectiongame", "in outer class:" + focusImageId);
		Log.d("selectiongame", "repeatFocus Image:" + repeatFocusImage);
		/*
		 * This while is to make sure that the selected fruit is displayed
		 * N(random number generated) times in the grid.
		 */

		while (repeatFocusImage > 0) {
			int randomInteger = randomGenerator.nextInt(MATRIX_SIZE);
			if (-1 == fruitIds[randomInteger]) {
				fruitIds[randomInteger] = focusImageId;
				repeatFocusImage--;
			}
		}

		/*
		 * This for loop is for filling 'MATRIX_SIZE-N' positions with fruits
		 * other than selected.
		 */

		int tempMatrixSize = 0;
		while (tempMatrixSize < MATRIX_SIZE) {
			int randomInteger = randomGenerator.nextInt(imageInfo.size());
			if (-1 == fruitIds[tempMatrixSize]) {
				if (randomInteger != focusImageId) {
					fruitIds[tempMatrixSize] = randomInteger;
					tempMatrixSize++;
				}
			} else {
				tempMatrixSize++;
			}
		}

		TextView selectedFruit = (TextView) findViewById(R.id.selectedFruitLabel);
		selectedFruit.setText("Find All "
				+ (imageInfo.get(focusImageId)).getFruitName() + "{"
				+ timesFocusImageSelected + "}");
		int[] tempList = new int[MATRIX_SIZE];
		Arrays.fill(tempList, 0);

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this, fruitIds, imageInfo,
				tempList));

	}

}
