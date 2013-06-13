package com.receiptspp.shopfrontmockup;

import org.json.JSONException;
import org.json.JSONObject;

import com.roscopeco.ormdroid.Column;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Table;

@Table(name="products")
public class Product extends Entity {
	@Column(name="id",primaryKey=true)
	public int id;
	public String title;
	public String description;
	public String blurb;
	public double price;
	public int imageId;
	
	public Product()
	{
		this(null,null,null,0.0,0);
	}

	public Product(String title, String description, double price, int imageId) {
		this.title = title;
		this.description = description;
		this.blurb = "";
		this.price = price;
		this.imageId = imageId;
	}
	
	public Product(String title, String description, String blurb, double price, int imageId) {
		this.title = title;
		this.description = description;
		this.blurb = blurb;
		this.price = price;
		this.imageId = imageId;
	}
	
	public int getId(){
		return id;
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
	
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		try {
			json.put(Keys.productTitle, title);
			json.put(Keys.productPrice, price);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}

}
