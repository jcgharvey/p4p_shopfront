package com.receiptspp.shopfrontmockup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roscopeco.ormdroid.Entity;

public class ProductActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		Intent receivedIntent = getIntent();
		int idOfProductToDisplay = receivedIntent.getExtras().getInt("product");
		Product product = Entity.query(Product.class).where("id")
				.eq(idOfProductToDisplay).execute();

		setContentView(R.layout.activity_product);

		// set the fields

		TextView titleView = (TextView) findViewById(R.id.viewProductTitle);
		TextView blurbView = (TextView) findViewById(R.id.viewProductBlurb);
		TextView descriptionView = (TextView) findViewById(R.id.viewProductDescription);
		ImageView imageView = (ImageView) findViewById(R.id.viewProductImage);

		titleView.setText(product.title);
		blurbView.setText(product.blurb);
		descriptionView.setText(product.description);
		imageView.setImageResource(product.imageId);

		Button buyButton = (Button) findViewById(R.id.buyButton);

		buyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Buy",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

}
