package com.receiptspp.shopfrontmockup.common;

public class Util {
	public static String generatePriceString(double price){
		return String.format("$%.2f", price);
	}
}
