package com.Internship.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internship.Backend.mappers.EmployeeMapper;
import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.EmployeeModel;
import com.Internship.Backend.models.JobPositionModel;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper mapper;

	public void addEmployee(EmployeeModel data) {
//		 TODO Auto-generated method stub
//		 System.out.println(data);
		mapper.addEmployee(data);
	}

	public List<EmployeeModel> getEmployees() {
		// TODO Auto-generated method stub
		return mapper.getEmployees();
	}
	
	public void updateEmployee(EmployeeModel data) {
		 mapper.updateEmployee(data);
	}
	
	public void deleteEmployee(String empid) {
		mapper.deleteEmployeeById(empid);
	}
	
	public List<BranchModel> getBranch(){
		
		return mapper.getBranch();
	}
	
	public List<JobPositionModel> getPosition(){
		return mapper.getPosition();
	}

}
