package com.receiptspp.shopfrontmockup.business;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.receiptspp.shopfrontmockup.common.Keys;

public class MockReceipt implements Receipt {

	private Map<JSONObject, Integer> productsMap;
	private String storeName;
	private String phone;
	private String address;
	private double totalTransaction;
	private double taxRate;
	private String userId;

	public MockReceipt() {
		productsMap = new HashMap<JSONObject, Integer>();
		totalTransaction = 0.0;
	}

	public MockReceipt(Boolean setUpFromCart) {
		this();
		if (setUpFromCart) {
			Cart cart = Cart.getInstance();
			Map<Product, Integer> productMap = cart.getCartProductQuantityMap();
			for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
				productsMap.put(entry.getKey().toJSON(), entry.getValue());
			}
			String tempId = cart.getUserId();
			if (tempId != null){
				this.userId = tempId;
			}
		}
	}

	public void setName(String name) {
		this.storeName = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public void addItem(JSONObject json) {
		try {
			double price = json.getDouble(Keys.productPrice);
			totalTransaction += price;
			Integer quantity = productsMap.get(json);
			if (quantity == null) {
				productsMap.put(json, 1);
			} else {
				productsMap.put(json, quantity + 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject toJSON() {
		JSONObject receipt = new JSONObject();

		receipt = jsonPut(receipt, Keys.receiptStoreName, storeName);
		receipt = jsonPut(receipt, Keys.receiptPhone, phone);
		receipt = jsonPut(receipt, Keys.receiptAddress, address);
		receipt = jsonPut(receipt, Keys.receiptTaxRate, taxRate);
		receipt = jsonPut(receipt, Keys.receiptUserId, userId);

		try {
			receipt.put(Keys.receiptItems, listToJSONArray());
		} catch (JSONException e) {
			// pass
		}
		receipt = jsonPut(receipt, Keys.receiptTotalTransaction,
				totalTransaction);

		return receipt;
	}

	private JSONObject jsonPut(JSONObject json, String key, Object obj) {
		try {
			json.put(key, obj);
		} catch (JSONException e) {
			// the field might not have been set, we're not fussed
		} catch (NullPointerException e) {
			// the obj might be null
		}
		return json;
	}

	private JSONArray listToJSONArray() throws JSONException {
		JSONArray array = new JSONArray();
		for (Map.Entry<JSONObject, Integer> entry : productsMap.entrySet()) {
			JSONObject itemQuantityPair = new JSONObject();
			itemQuantityPair.put(Keys.receiptItem, entry.getKey());
			itemQuantityPair.put(Keys.receiptItemQuantity, entry.getValue());
			array.put(itemQuantityPair);
		}
		return array;
	}

}
