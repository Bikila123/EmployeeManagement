package com.Internship.Backend.payload.request;


//@Data
public class LoginRequest {
	private String username;
	private String password;
	private String forcelogout;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getForcelogout() {
		return forcelogout;
	}
	public void setForcelogout(String forcelogout) {
		this.forcelogout = forcelogout;
	}
	
}
