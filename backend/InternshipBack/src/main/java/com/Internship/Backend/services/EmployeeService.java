package com.Internship.Backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internship.Backend.mappers.EmployeeMapper;
import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.DepartmentModel;
import com.Internship.Backend.models.EmployeeModel;
import com.Internship.Backend.models.JobPositionModel;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper mapper;
	private EmployeeIdTrackerRepository employeeIdTrackerRepository;

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
	
	 public String generateEmployeeId() {
	        // Get current year
	        int year = LocalDate.now().getYear();

	        // Get the last employee number from the database (for the current year)
	        EmployeeIdTracker tracker = employeeIdTrackerRepository.findByYear(year);

	        int newEmployeeNumber;
	        if (tracker == null) {
	            // Initialize if no record for the current year
	            newEmployeeNumber = 10000;
	            tracker = new EmployeeIdTracker();
	            tracker.setYear(year);
	            tracker.setLastEmployeeNumber(newEmployeeNumber);
	        } else {
	            // Increment the last employee number by 1
	            newEmployeeNumber = tracker.getLastEmployeeNumber() + 1;
	            tracker.setLastEmployeeNumber(newEmployeeNumber);
	        }

	        // Save the updated tracker
	        employeeIdTrackerRepository.save(tracker);

	        // employee ID: AB/10000/2024
	        String empid = String.format("AB/%05d/%d", newEmployeeNumber, year);

	        return empid;
	    }

}
