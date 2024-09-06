package com.Internship.Backend.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ChangePasswordModel {
 private String username;
 private String oldPassword;
 private String newPassword;
 private String confirmPassword;
 private Date loginDate;
 private Date password_changed_date;
 
public ChangePasswordModel() {
	super();
}
public ChangePasswordModel(String username, String oldPassword, String newPassword, String confirmPassword,
		Date loginDate, Date password_changed_date) {
	super();
	this.username = username;
	this.oldPassword = oldPassword;
	this.newPassword = newPassword;
	this.confirmPassword = confirmPassword;
	this.loginDate = loginDate;
	this.password_changed_date = password_changed_date;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getOldPassword() {
	return oldPassword;
}
public void setOldPassword(String oldPassword) {
	this.oldPassword = oldPassword;
}
public String getNewPassword() {
	return newPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}
public Date getLoginDate() {
	return loginDate;
}
public void setLoginDate(Date loginDate) {
	this.loginDate = loginDate;
}
public Date getPassword_changed_date() {
	return password_changed_date;
}
public void setPassword_changed_date(Date password_changed_date) {
	this.password_changed_date = password_changed_date;
}
 


}
