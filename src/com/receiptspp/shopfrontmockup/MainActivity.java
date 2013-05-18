package com.receiptspp.shopfrontmockup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView productContainer = (ListView) findViewById(R.id.productContainer);

		List<Product> list = new ArrayList<Product>();
		list.add(new Product("Trimtex Short O-Pants",
				"These be some great pants", "THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
				80.0,R.drawable.pants));
		list.add(new Product("Trimtex Rapid LZR Shirt",
				"This be a great shirt", "THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
				60.0,R.drawable.shirt));
		list.add(new Product("Trimtex O-Socks",
				"These be some great socks", "THIS IS A FANTASTIC DESCRIPTION ipsum lorem rida roo",
				40.0,R.drawable.socks));
		list.addAll(list);
		list.addAll(list);
		ArrayAdapter<Product> adapter = new ProductArrayAdapter(this, list);

		productContainer.setAdapter(adapter);

		productContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Item no. " + position,
						Toast.LENGTH_SHORT).show();
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
