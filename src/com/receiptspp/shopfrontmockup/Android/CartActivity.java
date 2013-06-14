package com.receiptspp.shopfrontmockup.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.receiptspp.shopfrontmockup.R;
import com.receiptspp.shopfrontmockup.business.Cart;
import com.receiptspp.shopfrontmockup.business.Product;

public class CartActivity extends Activity {

	private CartActivity self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		self = this;
		
		Cart cart = Cart.getInstance();
		
		Button continueShoppingButton = (Button) findViewById(R.id.continueShoppingButton);

		continueShoppingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, MainActivity.class));
			}
		});
		
		Button checkoutButton = (Button) findViewById(R.id.checkoutButton);

		checkoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, CheckoutActivity.class));
			}
		});
		
		ListView cartProductContainer = (ListView) findViewById(R.id.cartProductContainer);

		ArrayAdapter<Product> adapter = new CartViewArrayAdapter(this, cart.getProductsInCart());

		cartProductContainer.setAdapter(adapter);

		cartProductContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Item no. " + position,
						Toast.LENGTH_SHORT).show();
				
				Intent productViewIntent = new Intent(self, ProductActivity.class);
				int idOfProductInView = Integer.parseInt(view.getTag(R.id.productId).toString());
				productViewIntent.putExtra("product", idOfProductInView);
				startActivity(productViewIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart, menu);
		return true;
	}

}
