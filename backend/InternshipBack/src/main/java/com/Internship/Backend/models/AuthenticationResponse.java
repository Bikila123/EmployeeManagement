package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AuthenticationResponse {
  private  String jwt;
  private  String roleId;
  
public AuthenticationResponse(String jwt, String roleId) {
	super();
	this.jwt = jwt;
	this.roleId = roleId;
}

public AuthenticationResponse() {
	super();
}

public String getJwt() {
	return jwt;
}
public void setJwt(String jwt) {
	this.jwt = jwt;
}
public String getRoleId() {
	return roleId;
}
public void setRoleId(String roleId) {
	this.roleId = roleId;
}
  
}
