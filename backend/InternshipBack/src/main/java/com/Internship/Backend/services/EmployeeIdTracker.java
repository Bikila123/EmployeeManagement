package com.Internship.Backend.services;

import org.springframework.ldap.odm.annotations.Id;

//@Entity
//@Table(name = "employee_id_tracker")
public class EmployeeIdTracker {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    private int lastEmployeeNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getLastEmployeeNumber() {
		return lastEmployeeNumber;
	}
	public void setLastEmployeeNumber(int lastEmployeeNumber) {
		this.lastEmployeeNumber = lastEmployeeNumber;
	}

}
