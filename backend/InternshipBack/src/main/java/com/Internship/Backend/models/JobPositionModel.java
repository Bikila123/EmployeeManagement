package com.Internship.Backend.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class JobPositionModel {
    private Long id;

    private String title;

    private int location;

    private int role;

    private String loc_name;

    private String role_name;

    private List<RolesModel> roles;
    
    

	public JobPositionModel(Long id, String title, int location, int role, String loc_name, String role_name,
			List<RolesModel> roles) {
		super();
		this.id = id;
		this.title = title;
		this.location = location;
		this.role = role;
		this.loc_name = loc_name;
		this.role_name = role_name;
		this.roles = roles;
	}
	
	

	public JobPositionModel() {
		super();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public List<RolesModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesModel> roles) {
		this.roles = roles;
	}
    
}
