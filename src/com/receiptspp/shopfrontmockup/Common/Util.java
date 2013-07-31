package com.receiptspp.shopfrontmockup.common;

public class Util {
	public static String generatePriceString(double price) {
		return String.format("$%.2f", price);
	}

	public static String getHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = bytes.length - 1; i >= 0; --i) {
			int b = bytes[i] & 0xff;
			if (b < 0x10)
				sb.append('0');
			sb.append(Integer.toHexString(b));
			// Commented out to remove spaces
			// if (i > 0) {
			// sb.append(" ");
			// }
		}
		return sb.toString();
	}
}
