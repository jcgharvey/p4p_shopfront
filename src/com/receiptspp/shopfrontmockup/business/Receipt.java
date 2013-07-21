package com.receiptspp.shopfrontmockup.business;

import org.json.JSONException;
import org.json.JSONObject;

public interface Receipt {

	public void addItem(JSONObject json);

	public JSONObject toJSON() throws JSONException;
}
