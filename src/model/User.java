package model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String fullname;
	private String username;
	private String email;
	private String phoneNumber;
	private String password;
	private boolean isVerify;

	public User() {
	}

	public User(int id, String fullname, String username, String email, String phoneNumber, String password,
			boolean isVerify) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.isVerify = isVerify;
	}

	public int getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

}
