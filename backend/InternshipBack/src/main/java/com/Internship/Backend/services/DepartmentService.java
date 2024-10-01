package com.Internship.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.Internship.Backend.mappers.DepartmentMapper;
import com.Internship.Backend.models.DepartmentModel;

public class DepartmentService {
	@Autowired
	DepartmentMapper mapper;

	public void addDepartment(DepartmentModel data) {
		// TODO Auto-generated method stub
		mapper.addDepartment(data);		
	}

	public List<DepartmentModel> getDepartment() {
		// TODO Auto-generated method stub
		return 	mapper.getDepartment();
	}

}
