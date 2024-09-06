package com.Internship.Backend.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DeletedUserRoleMappingModel {
    private int id;
    private int role_id;
    private int user_id;
    private String username;
    private String description;
    private int created_by;
    private Date created_date;
    private int modified_by;
    private Date modified_date; 
    private int deleted_by;
    private Date deleted_date;
    
	public DeletedUserRoleMappingModel() {
		super();
	}
	public DeletedUserRoleMappingModel(int id, int role_id, int user_id, String username, String description,
			int created_by, Date created_date, int modified_by, Date modified_date, int deleted_by, Date deleted_date) {
		super();
		this.id = id;
		this.role_id = role_id;
		this.user_id = user_id;
		this.username = username;
		this.description = description;
		this.created_by = created_by;
		this.created_date = created_date;
		this.modified_by = modified_by;
		this.modified_date = modified_date;
		this.deleted_by = deleted_by;
		this.deleted_date = deleted_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCreated_by() {
		return created_by;
	}
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Date getModified_date() {
		return modified_date;
	}
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	public int getDeleted_by() {
		return deleted_by;
	}
	public void setDeleted_by(int deleted_by) {
		this.deleted_by = deleted_by;
	}
	public Date getDeleted_date() {
		return deleted_date;
	}
	public void setDeleted_date(Date deleted_date) {
		this.deleted_date = deleted_date;
	}
    
}
