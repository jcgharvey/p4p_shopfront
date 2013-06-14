package com.receiptspp.shopfrontmockup.Common;

public class Util {
	public static String generatePriceString(double price){
		return String.format("$%.2f", price);
	}
}
