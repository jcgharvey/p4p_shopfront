package com.receiptspp.shopfrontmockup;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class MockReceipt implements Receipt {

	private List<JSONObject> products;
	private String name;
	private String phone;
	private String address;
	
	
	
	public MockReceipt(String storeName){
		products = new ArrayList<JSONObject>();
		this.name = storeName;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public void addItem(JSONObject json) {
		products.add(json);
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		
		
		return json;
	}	
	
}
