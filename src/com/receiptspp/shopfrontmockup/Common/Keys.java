package com.receiptspp.shopfrontmockup.common;

public class Keys {

	// Product Keys
	public static final String productTitle = "name";
	public static final String productPrice = "price_per_item";

	// Receipt Keys
	public static final String receiptStoreName = "store_name";
	public static final String receiptPhone = "phone";
	public static final String receiptAddress = "address";
	public static final String receiptTotalTransaction = "total_transaction";
	public static final String receiptTaxRate = "tax_rate";
	public static final String receiptItem = "item";
	public static final String receiptItems = "items";
	public static final String receiptItemQuantity = "quantity";
	public static final String receiptDateTime = "date_time";
	public static final String receiptCategory = "category";

	// Endpoints
	public static final String receiptsBaseUrl = "http://receiptspp.herokuapp.com/";
	public static final String receiptsEndpointStart = receiptsBaseUrl
			+ "users/";
	public static final String receiptsEndpointEnd = "/receipts";
	public static final String receiptsSmartcard = receiptsBaseUrl
			+ "user_from_smartcard/";

	public static final String userId = "user_id";
}