package com.receiptspp.shopfrontmockup;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductArrayAdapter extends ArrayAdapter<Product> {

	private Context context;
	private List<Product> products;

	public ProductArrayAdapter(Context context, List<Product> products) {
		super(context, R.layout.product_list_item, products);
		this.context = context;
		this.products = products;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Product product = products.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.product_list_item, parent,
				false);
		TextView titleView = (TextView) rowView.findViewById(R.id.listProductTitle);
		TextView blurbView = (TextView) rowView.findViewById(R.id.listProductBlurb);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.listProductImage);
		
		titleView.setText(product.getTitle());
		blurbView.setText(product.getBlurb());
		imageView.setImageResource(product.getimageId());
		
		return rowView;
	}
}