package com.receiptspp.shopfrontmockup;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<Product, Integer> cartProductQauntityMap = new HashMap<Product, Integer>();
	private double totalTransaction = 0.0;

	// Private constructor prevents instantiation from other classes
	private Cart() {
	}

	private static class CartHolder {
		public static final Cart INSTANCE = new Cart();

	}

	public static Cart getInstance() {
		return CartHolder.INSTANCE;
	}

	public void addItem(Product product) {
		double price = product.getPrice();
		totalTransaction += price;
		Integer quantity = cartProductQauntityMap.get(product);
		if (quantity == null) {
			cartProductQauntityMap.put(product, 1);
		} else {
			cartProductQauntityMap.put(product, quantity + 1);
		}
	}

	public void addMultipleItems(Product product, Integer quantity) {
		double price = product.getPrice();
		totalTransaction += price * quantity;
		Integer currentQuantity = cartProductQauntityMap.get(product);
		if (currentQuantity == null) {
			cartProductQauntityMap.put(product, quantity);
		} else {
			cartProductQauntityMap.put(product, currentQuantity + quantity);
		}
	}

	public void removeOneItem(Product product) {
		double price = product.getPrice();
		Integer currentQuantity = cartProductQauntityMap.get(product);

		if (currentQuantity != null) {
			currentQuantity--;
			// if lt 0 then do nothing as the qty in map is 0.
			if (currentQuantity >= 0) {
				cartProductQauntityMap.put(product, currentQuantity);
				// if we have succeeded decrement the price.
				totalTransaction -= price;
			}
		}
	}

	public void removeAllItem(Product product) {
		double price = product.getPrice();
		Integer currentQuantity = cartProductQauntityMap.get(product);

		if (currentQuantity != null) { // then there is stuff in the map
			cartProductQauntityMap.remove(product);
			// if we have succeeded decrement the price.
			totalTransaction -= price * currentQuantity;
		}
	}

	public void clear() {
		cartProductQauntityMap.clear();
		totalTransaction = 0.0;
	}

	public Map<Product, Integer> getCartProductQauntityMap() {
		return cartProductQauntityMap;
	}

	public double getTotalTransaction() {
		return totalTransaction;
	}
}
