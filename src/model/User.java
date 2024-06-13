package model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int id;
	private String username;
	private String email;
	private String password;
	private boolean isVerify;

	public User() {
	}

	public User(String username, String email, String password, boolean isVerify) {
		User.id++;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isVerify = isVerify;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

}
