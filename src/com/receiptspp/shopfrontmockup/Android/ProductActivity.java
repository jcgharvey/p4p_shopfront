package com.receiptspp.shopfrontmockup.android;

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

import com.receiptspp.shopfrontmockup.R;
import com.receiptspp.shopfrontmockup.business.Cart;
import com.receiptspp.shopfrontmockup.business.Product;
import com.roscopeco.ormdroid.Entity;

public class ProductActivity extends Activity {

	private Product product;
	private ProductActivity self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		Intent receivedIntent = getIntent();
		int idOfProductToDisplay = receivedIntent.getExtras().getInt("product");
		product = Entity.query(Product.class).where("id")
				.eq(idOfProductToDisplay).execute();

		setContentView(R.layout.activity_product);
		
		self = this;
		
		// set the fields

		TextView titleView = (TextView) findViewById(R.id.viewProductTitle);
		TextView blurbView = (TextView) findViewById(R.id.viewProductBlurb);
		TextView descriptionView = (TextView) findViewById(R.id.viewProductDescription);
		ImageView imageView = (ImageView) findViewById(R.id.viewProductImage);

		titleView.setText(product.title);
		blurbView.setText(product.blurb);
		descriptionView.setText(product.description);
		imageView.setImageResource(product.imageId);

		Button addItemButton = (Button) findViewById(R.id.addItemToCart);

		addItemButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Add",
						Toast.LENGTH_SHORT).show();
				Cart cart = Cart.getInstance();
				cart.addItem(product);
				startActivity(new Intent(self, CartActivity.class));
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
