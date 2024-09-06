package com.Internship.Backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class TokenRefreshRequest {
  private String refreshToken;
  
  

public TokenRefreshRequest(String refreshToken) {
	super();
	this.refreshToken = refreshToken;
}


public TokenRefreshRequest() {
	super();
}


public String getRefreshToken() {
	return refreshToken;
}

public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
}

}
