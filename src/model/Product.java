package model;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int categoryId;
	private double price;
	private int quantity;

	public Product() {
	}

	public Product(int id, String name, int categoryId, double price, int quantity) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.price = price;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
