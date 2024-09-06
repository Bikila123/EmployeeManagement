package com.Internship.Backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
public class PasswordResetOtp {
    private Long id;
    private String otp;
    private LocalDateTime expiryDate;
    private Long user_id;

    public PasswordResetOtp() {
		super();
	}

	public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
    
}
