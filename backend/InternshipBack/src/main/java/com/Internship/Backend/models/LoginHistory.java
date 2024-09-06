package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class LoginHistory {
   private Long id;
   private String username;
   private String loginDate;
   
public LoginHistory() {
	super();
}
public LoginHistory(Long id, String username, String loginDate) {
	super();
	this.id = id;
	this.username = username;
	this.loginDate = loginDate;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getLoginDate() {
	return loginDate;
}
public void setLoginDate(String loginDate) {
	this.loginDate = loginDate;
}
   
}
