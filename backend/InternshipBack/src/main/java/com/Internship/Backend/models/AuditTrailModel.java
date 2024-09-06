package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class AuditTrailModel {
    private Long id;
    private int user_id;
    private String username;
    private String action;
    private Date timestamp;
    private String details;
    
	public AuditTrailModel() {
		super();
	}
	public AuditTrailModel(Long id, int user_id, String username, String action, Date timestamp, String details) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.username = username;
		this.action = action;
		this.timestamp = timestamp;
		this.details = details;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
    
}
