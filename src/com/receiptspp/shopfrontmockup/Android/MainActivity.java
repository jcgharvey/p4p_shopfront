package com.receiptspp.shopfrontmockup.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.receiptspp.shopfrontmockup.R;
import com.receiptspp.shopfrontmockup.business.Product;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;

public class MainActivity extends Activity {

	private MainActivity self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// save a reference to this
		self = this;
		// initialize ormdroid
		ORMDroidApplication.initialize(this);
		List<Product> products = Entity.query(Product.class).executeMulti();
		if (products.size() == 0) {
			Product p = new Product("Trimtex Short O-Pants",
					"These be some great pants",
					"THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
					80.0, R.drawable.pants);
			p.save();
			products.add(p);
			p = new Product("Trimtex Rapid LZR Shirt", "This be a great shirt",
					"THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
					60.0, R.drawable.shirt);
			p.save();
			products.add(p);
			p = new Product("Trimtex O-Socks", "These be some great socks",
					"THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
					40.0, R.drawable.socks);
			p.save();
			products.add(p);
		}
		
		ListView productContainer = (ListView) findViewById(R.id.productContainer);

		ArrayAdapter<Product> adapter = new ProductViewArrayAdapter(this, products);

		productContainer.setAdapter(adapter);

		productContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Item no. " + view.getTag(R.id.productId),
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
