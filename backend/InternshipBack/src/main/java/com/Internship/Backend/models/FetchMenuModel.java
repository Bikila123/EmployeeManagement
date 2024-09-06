package com.Internship.Backend.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class FetchMenuModel {
    private String parent_item_name;
    private MenuModel[]  menus;
    private String parent_icon;
    
	public FetchMenuModel() {
		super();
	}
	public FetchMenuModel(String parent_item_name, MenuModel[] menus, String parent_icon) {
		super();
		this.parent_item_name = parent_item_name;
		this.menus = menus;
		this.parent_icon = parent_icon;
	}
	public String getParent_item_name() {
		return parent_item_name;
	}
	public void setParent_item_name(String parent_item_name) {
		this.parent_item_name = parent_item_name;
	}
	public MenuModel[] getMenus() {
		return menus;
	}
	public void setMenus(MenuModel[] menus) {
		this.menus = menus;
	}
	public String getParent_icon() {
		return parent_icon;
	}
	public void setParent_icon(String parent_icon) {
		this.parent_icon = parent_icon;
	}
    
}
