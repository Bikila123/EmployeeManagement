package com.Internship.Backend.models;
public class EmployeeIdTracker {
    private int emp_year;
    private int last_employee_number;
    
	public EmployeeIdTracker() {
		super();
	}
	public EmployeeIdTracker(int emp_year, int last_employee_number) {
		super();
		this.emp_year = emp_year;
		this.last_employee_number = last_employee_number;
	}
	public int getEmp_year() {
		return emp_year;
	}
	public void setEmp_year(int emp_year) {
		this.emp_year = emp_year;
	}
	public int getLast_employee_number() {
		return last_employee_number;
	}
	public void setLast_employee_number(int last_employee_number) {
		this.last_employee_number = last_employee_number;
	}
	

}
