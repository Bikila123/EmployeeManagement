package com.Internship.Backend.models;

public class DepartmentModel {
	  private int id;
	  private String department_name;
	  
	public DepartmentModel() {
		super();
	}

	public DepartmentModel(int id, String department_name) {
		super();
		this.id = id;
		this.department_name = department_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	
	
}
