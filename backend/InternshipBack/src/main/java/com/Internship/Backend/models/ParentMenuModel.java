package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ParentMenuModel {
    private int id;
    private String item_name;
    private int role_id;
    private String parent_icon;
    
	public ParentMenuModel() {
		super();
	}
	public ParentMenuModel(int id, String item_name, int role_id, String parent_icon) {
		super();
		this.id = id;
		this.item_name = item_name;
		this.role_id = role_id;
		this.parent_icon = parent_icon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getParent_icon() {
		return parent_icon;
	}
	public void setParent_icon(String parent_icon) {
		this.parent_icon = parent_icon;
	}
    
    
}
