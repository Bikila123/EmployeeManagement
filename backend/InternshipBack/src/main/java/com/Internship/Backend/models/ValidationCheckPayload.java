package com.Internship.Backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;


public class ValidationCheckPayload {

    private Long id;
    private String unique;
    private String idL;
    
	public ValidationCheckPayload() {
		super();
	}
	public ValidationCheckPayload(Long id, String unique, String idL) {
		super();
		this.id = id;
		this.unique = unique;
		this.idL = idL;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnique() {
		return unique;
	}
	public void setUnique(String unique) {
		this.unique = unique;
	}
	public String getIdL() {
		return idL;
	}
	public void setIdL(String idL) {
		this.idL = idL;
	}
    
}
