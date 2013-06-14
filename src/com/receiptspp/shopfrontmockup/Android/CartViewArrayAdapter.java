package com.receiptspp.shopfrontmockup.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.receiptspp.shopfrontmockup.R;
import com.receiptspp.shopfrontmockup.business.Cart;
import com.receiptspp.shopfrontmockup.business.Product;
import com.receiptspp.shopfrontmockup.common.Util;

public class CartViewArrayAdapter extends ArrayAdapter<Product> {

	private Context context;
	private List<Product> products;
	private Cart cart;

	public CartViewArrayAdapter(Context context, List<Product> products) {
		super(context, R.layout.cart_list_item, products);
		this.context = context;
		this.products = products;
		this.cart = Cart.getInstance();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Product product = products.get(position);
		double price = product.getPrice();
		Integer quantity = cart.getQuantityOfProduct(product);
		double subTotal = quantity * price;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.cart_list_item, parent, false);
		rowView.setTag(R.id.productId, product.getId());
		
		// get text views
		TextView titleView = (TextView) rowView
				.findViewById(R.id.listCartProductTitle);
		TextView priceView = (TextView) rowView
				.findViewById(R.id.listCartProductPrice);
		TextView quantityView = (TextView) rowView
				.findViewById(R.id.listCartProductQuantity);
		TextView subtotalView = (TextView) rowView
				.findViewById(R.id.listCartProductSubTotal);
		
		// set text views
		titleView.setText(product.getTitle());
		priceView.setText(Util.generatePriceString(price));
		quantityView.setText(quantity.toString());
		subtotalView.setText(Util.generatePriceString(subTotal));

		return rowView;
	}

}