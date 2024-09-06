package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TypeModel {
    private int id;
    private String type;
    private int created_updated_by;
    
	public TypeModel() {
		super();
	}
	public TypeModel(int id, String type, int created_updated_by) {
		super();
		this.id = id;
		this.type = type;
		this.created_updated_by = created_updated_by;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCreated_updated_by() {
		return created_updated_by;
	}
	public void setCreated_updated_by(int created_updated_by) {
		this.created_updated_by = created_updated_by;
	}
    
}
