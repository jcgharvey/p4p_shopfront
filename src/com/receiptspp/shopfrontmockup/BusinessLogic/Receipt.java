package com.receiptspp.shopfrontmockup.BusinessLogic;

import org.json.JSONObject;

public interface Receipt {

	public void addItem(JSONObject json);
	
	public JSONObject toJSON();
}
