package com.Internship.Backend.models;

import java.io.Serializable;

public class EmployeeModel implements Serializable { //to transform this class to different types of strings 
	private String empid;
	private String first_name;
	private String last_name;
	private int unit;
	private int position;
	private Double salary;
	private String email;
	private String phone;
	
	public EmployeeModel() {
		super();
	}
	public EmployeeModel(String empid, String first_name, String last_name, int unit, int position, Double salary, String email, String phone) {
		super();
		this.empid = empid;
		this.first_name = first_name;
		this.last_name = last_name;
		this.unit = unit;
		this.position = position;
		this.salary = salary;
		this.email = email;
		this.phone = phone;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
