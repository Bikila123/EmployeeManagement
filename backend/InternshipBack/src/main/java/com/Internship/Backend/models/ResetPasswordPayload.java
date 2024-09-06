package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResetPasswordPayload {

    private String newPassword;
    private String token;
    
	public ResetPasswordPayload() {
		super();
	}
	public ResetPasswordPayload(String newPassword, String token) {
		super();
		this.newPassword = newPassword;
		this.token = token;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
}
