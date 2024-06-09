package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int adminId;
	private Date date;
	private ArrayList<Product> products;
	private double total;

	public Bill() {
	}

	public Bill(int id, String name, int adminId, Date date, ArrayList<Product> products, double total) {
		this.id = id;
		this.name = name;
		this.adminId = adminId;
		this.date = date;
		this.products = products;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAdminId() {
		return adminId;
	}

	public Date getDate() {
		return date;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public double getTotal() {
		return total;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
