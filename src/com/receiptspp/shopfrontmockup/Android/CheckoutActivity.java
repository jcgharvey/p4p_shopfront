package com.receiptspp.shopfrontmockup.Android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.receiptspp.shopfrontmockup.R;

public class CheckoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
