package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MenuModel {
    private int id;
    private String item_name;
    private String link;
    private String icon;
    private int parent_item_id;
    private int status;
    private int role_id;
    
	public MenuModel() {
		super();
	}
	public MenuModel(int id, String item_name, String link, String icon, int parent_item_id, int status, int role_id) {
		super();
		this.id = id;
		this.item_name = item_name;
		this.link = link;
		this.icon = icon;
		this.parent_item_id = parent_item_id;
		this.status = status;
		this.role_id = role_id;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getParent_item_id() {
		return parent_item_id;
	}
	public void setParent_item_id(int parent_item_id) {
		this.parent_item_id = parent_item_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
    
    
}
