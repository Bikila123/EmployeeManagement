package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class StatusModel {
    private int id;
    private String status;
    private int created_updated_by;
    
	public StatusModel() {
		super();
	}
	public StatusModel(int id, String status, int created_updated_by) {
		super();
		this.id = id;
		this.status = status;
		this.created_updated_by = created_updated_by;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCreated_updated_by() {
		return created_updated_by;
	}
	public void setCreated_updated_by(int created_updated_by) {
		this.created_updated_by = created_updated_by;
	}
    
}
