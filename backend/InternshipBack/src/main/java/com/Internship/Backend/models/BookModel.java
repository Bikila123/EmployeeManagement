package com.Internship.Backend.models;

public class BookModel {
	  private int id;
	  private String title;
	  private int year;
	public BookModel(int id, String title, int year) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
	}
	public BookModel() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	  
}
