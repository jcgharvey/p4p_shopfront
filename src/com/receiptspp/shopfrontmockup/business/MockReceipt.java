package com.receiptspp.shopfrontmockup.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	private String category;
	private double totalTransaction;
	private String userId;
	private String dateTime;
	private static final String serverFormat = "dd/MM/yyyy HH:mm:ss";

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
				Product p = (Product) entry.getKey();
				Integer qty = entry.getValue();
				productsMap.put(p.toJSON(), entry.getValue());
				totalTransaction += p.getPrice() * qty;
			}
			String tempId = cart.getUserId();
			if (tempId != null) {
				this.userId = tempId;
			}
			this.dateTime = new SimpleDateFormat(serverFormat).format(Calendar
					.getInstance().getTime());
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

	/**
	 * Get a JSON representation of our receipt
	 * 
	 * @throws JSONException
	 */
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject receipt = new JSONObject();

		receipt.put(Keys.receiptStoreName, storeName);
		receipt = jsonPut(receipt, Keys.receiptPhone, phone);
		receipt = jsonPut(receipt, Keys.receiptAddress, address);

		receipt.put(Keys.receiptUserId, userId);
		receipt.put(Keys.receiptDateTime, dateTime);
		receipt.put(Keys.receiptCategory, category);
		// add all the items to the receipt
		try {
			receipt.put(Keys.receiptItems, productsMapToJSONArray());
		} catch (JSONException e) {
			// pass
		}

		receipt.put(Keys.receiptTotalTransaction, totalTransaction);
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

	private JSONArray productsMapToJSONArray() throws JSONException {
		JSONArray array = new JSONArray();
		for (Map.Entry<JSONObject, Integer> entry : productsMap.entrySet()) {
			JSONObject item = entry.getKey();
			item.put(Keys.receiptItemQuantity, entry.getValue());
			array.put(item);
		}
		return array;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

}
