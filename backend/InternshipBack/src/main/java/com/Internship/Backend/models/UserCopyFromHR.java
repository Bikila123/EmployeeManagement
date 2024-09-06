package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserCopyFromHR {
    private Integer id;
    private Boolean status;
    private String deptLocation;
    private String departmentName;
    private String empId;
    private String position;
    private String empName;
    private String email;
    private String employmentDate;
    private String unit;
    
	public UserCopyFromHR() {
		super();
	}
	public UserCopyFromHR(Integer id, Boolean status, String deptLocation, String departmentName, String empId,
			String position, String empName, String email, String employmentDate, String unit) {
		super();
		this.id = id;
		this.status = status;
		this.deptLocation = deptLocation;
		this.departmentName = departmentName;
		this.empId = empId;
		this.position = position;
		this.empName = empName;
		this.email = email;
		this.employmentDate = employmentDate;
		this.unit = unit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getDeptLocation() {
		return deptLocation;
	}
	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmploymentDate() {
		return employmentDate;
	}
	public void setEmploymentDate(String employmentDate) {
		this.employmentDate = employmentDate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
    
}

