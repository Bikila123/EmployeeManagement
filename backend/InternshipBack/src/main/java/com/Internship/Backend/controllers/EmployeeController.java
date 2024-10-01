package com.Internship.Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Internship.Backend.mappers.EmployeeIdMapper;
import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.DepartmentModel;
import com.Internship.Backend.models.EmployeeIdTracker;
import com.Internship.Backend.models.EmployeeModel;
import com.Internship.Backend.models.JobPositionModel;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

   @Autowired
   EmployeeService service;
   @Autowired
   EmployeeIdMapper idmapper;
   
   @PostMapping("/addEmployee")
   public ResponseEntity<MessageResponse> addEmployee(@RequestBody EmployeeModel data){
       String message;
       try {
    	   System.out.println("request reached");
    	   String empid = service.generateEmployeeId();
    	   data.setEmpid(empid);
    	   System.out.println("Employee ID:"+empid);
           service.addEmployee(data);
           message = "Employee Added Successfully";
           return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
       } catch (Exception e) {
           e.printStackTrace();
           message = "Could not Add Employee : " + ". Error: " + e.getMessage();
           logger.error("Error Occured", e);
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
       }
   }
   
   @GetMapping("/getEmployees")
   public ResponseEntity<List<EmployeeModel>> getEmployees(){
	  try {
		  List<EmployeeModel> employees= service.getEmployees();
		  return new ResponseEntity<>(employees, HttpStatus.OK);
	  }catch(Exception e) {
		  logger.error("error on get employees", e);
		  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

	  }
   }
   
   @PutMapping("/updateEmployee")
   public ResponseEntity<MessageResponse> updateEmployee(@RequestBody EmployeeModel data){
       String message;
       try {
           service.updateEmployee(data);
           message = "Employee Updated Successfully";
           return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
       } catch (Exception e) {
           e.printStackTrace();
           message = "Could not Update Employee : " + ". Error: " + e.getMessage();
           logger.error("Error Occured", e);
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
       }
   }
   
   @DeleteMapping("/deleteEmployee")
   public ResponseEntity<MessageResponse> deleteEmployee(String empid ){
       String message;
       try {
           service.deleteEmployee(empid);
           message = "Employee Deleted Successfully";
           return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
       } catch (Exception e) {
           e.printStackTrace();
           message = "Could not Delete Employee : " + ". Error: " + e.getMessage();
           logger.error("Error Occured", e);
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
       }
   }
   @GetMapping("/getBranch")
   public ResponseEntity<List<BranchModel>> getBranch(){
	   try{
		   List<BranchModel> branches = service.getBranch();
		   return new ResponseEntity<>(branches,HttpStatus.OK);
	   }catch(Exception e) {
		   logger.error("error on get branch", e);
		   return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		   
	   }
   }
   
   @GetMapping("/getPosition")
   public ResponseEntity<List<JobPositionModel>> getPosition(){
	   try{
		   List<JobPositionModel> positions = service.getPosition();
		   return new ResponseEntity<>(positions,HttpStatus.OK);
	   }catch(Exception e) {
		   logger.error("error on get branch", e);
		   return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		   
	   }
   }
   
   @GetMapping("/getDepartment")
   public ResponseEntity<List<DepartmentModel>> getDepartment(){
	   try{
		   List<DepartmentModel> dpt = service.getDepartment();
		   return new ResponseEntity<>(dpt,HttpStatus.OK);
	   }catch(Exception e) {
		   logger.error("error on get department", e);
		   return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		   
	   }
   }
//   @GetMapping("/getLastNum/{year}")
//   public ResponseEntity<Integer> getLastNum(@PathVariable int year){
//	   try {
//		   EmployeeIdTracker number = idmapper.getLastNum(year);
//		   return new ResponseEntity<>(number.getLastEmployeeNumber(),HttpStatus.OK);
//	   }catch(Exception e) {
//		   logger.error("error on get last num", e);
//		   return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//	   
//	   }
//	 
//   }
   
}
