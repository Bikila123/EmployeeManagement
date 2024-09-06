package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ByUserModel {

    private int branch;
    private int region;
    private int segment;
    
	public ByUserModel(int branch, int region, int segment) {
		super();
		this.branch = branch;
		this.region = region;
		this.segment = segment;
	}
	
	public ByUserModel() {
		super();
	}

	public int getBranch() {
		return branch;
	}
	public void setBranch(int branch) {
		this.branch = branch;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public int getSegment() {
		return segment;
	}
	public void setSegment(int segment) {
		this.segment = segment;
	}
    
}
