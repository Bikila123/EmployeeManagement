package com.Internship.Backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internship.Backend.mappers.EmployeeIdMapper;
import com.Internship.Backend.mappers.EmployeeMapper;
import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.DepartmentModel;
import com.Internship.Backend.models.EmployeeIdTracker;
import com.Internship.Backend.models.EmployeeModel;
import com.Internship.Backend.models.JobPositionModel;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper mapper;
	@Autowired
	EmployeeIdMapper idmapper;

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
	
	public List<DepartmentModel>  getDepartment(){
		return mapper.getDepartment();
	}
	public Integer getLastNum(Integer year) {
		return idmapper.getLastNum(year);
	}
	public void addEmployeeId(int year, int last_number) {
		idmapper.addEmployeeId(year, last_number);
	}
	
	public String generateEmployeeId() {
		 try {
	        // Get current year
	        int year = LocalDate.now().getYear();
	        System.out.println("requested year :"+year);

	        // Get the last employee number from the database (for the current year)
	        Integer last_number = idmapper.getLastNum(year);
	        System.out.println("last number gainded: "+last_number);
	        Integer newEmployeeNumber;
	        if (last_number==null) {
	        // Initialize if no record for the current year
	            newEmployeeNumber= 10000;
	            addEmployeeId(year, newEmployeeNumber); 
	        }
	        else 
	        {
	        // Increment the last employee number by 1
//	            Integer newEmployeeNumber ;
	        	newEmployeeNumber=last_number+1;
	            addEmployeeId(year, newEmployeeNumber); 

	         }

	        // employee ID format : AB/10000/2024
	        String empid = String.format("AB/%05d/%d", newEmployeeNumber, year);

	        return empid;
	    }
		catch (Exception e) {
		   e.printStackTrace();
	    	System.out.println("unable to generate empid"+e);
	    	String error ="unable1";
	    	return error;
	    }
	 }
}
