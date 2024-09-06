package com.Internship.Backend.models;


import lombok.Data;
import lombok.NoArgsConstructor;

public class BranchModel {
    private Long id;
    private String name;
    private String code;
    private RegionModel region;
    private boolean status;
    private Long user_id;
    
	public BranchModel() {
		super();
	}

	public BranchModel(Long id, String name, String code, RegionModel region, boolean status, Long user_id) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.region = region;
		this.status = status;
		this.user_id = user_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public RegionModel getRegion() {
		return region;
	}

	public void setRegion(RegionModel region) {
		this.region = region;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
    
}
