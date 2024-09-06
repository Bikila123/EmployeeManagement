package com.Internship.Backend.models;


import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RefreshToken {

  private long id;

  private UserModel user;

//   @Column(nullable = false, unique = true)
  private String token;

  private Instant expiryDate;
  private String browser;
  private String ipaddress;
  private String browser_version;
  
public RefreshToken() {
	super();
}
public RefreshToken(long id, UserModel user, String token, Instant expiryDate, String browser, String ipaddress,
		String browser_version) {
	super();
	this.id = id;
	this.user = user;
	this.token = token;
	this.expiryDate = expiryDate;
	this.browser = browser;
	this.ipaddress = ipaddress;
	this.browser_version = browser_version;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public UserModel getUser() {
	return user;
}
public void setUser(UserModel user) {
	this.user = user;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public Instant getExpiryDate() {
	return expiryDate;
}
public void setExpiryDate(Instant expiryDate) {
	this.expiryDate = expiryDate;
}
public String getBrowser() {
	return browser;
}
public void setBrowser(String browser) {
	this.browser = browser;
}
public String getIpaddress() {
	return ipaddress;
}
public void setIpaddress(String ipaddress) {
	this.ipaddress = ipaddress;
}
public String getBrowser_version() {
	return browser_version;
}
public void setBrowser_version(String browser_version) {
	this.browser_version = browser_version;
}
  
 

}