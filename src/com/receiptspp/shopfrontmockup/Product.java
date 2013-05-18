package com.receiptspp.shopfrontmockup;

public class Product {
	private String title;
	private String description;
	private String blurb;
	private double price;
	private int imageId;

	public Product(String title, String description, String blurb, double price, int imageId) {
		this.title = title;
		this.description = description;
		this.blurb = blurb;
		this.price = price;
		this.imageId = imageId;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getBlurb(){
		return this.blurb;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public int getimageId(){
		return this.imageId;
	}
}
