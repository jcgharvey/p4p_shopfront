package com.receiptspp.shopfrontmockup;

import org.json.JSONObject;

public interface Receipt {

	public void addItem(JSONObject json);
	
	public JSONObject toJSON();
}
