package com.Internship.Backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Internship.Backend.models.DepartmentModel;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.DepartmentService;

public class DepartmentController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	DepartmentService service;
	
	@PostMapping("/addDepartment")
	   public ResponseEntity<MessageResponse> addDepartment(@RequestBody DepartmentModel data){
	       String message;
	       try {
	           service.addDepartment(data);
	           message = "Department Added Successfully";
	           return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
	       } catch (Exception e) {
	           e.printStackTrace();
	           message = "Could not Add Department: " + ". Error: " + e.getMessage();
	           logger.error("Error Occured", e);
	           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
	       }
	   }
	
	@GetMapping("/getDepartment")
		public ResponseEntity<List<DepartmentModel>> getDepartment(){
			String message;
			  try {
				  List<DepartmentModel> department = service.getDepartment();
				  return new ResponseEntity<>(department, HttpStatus.OK);
			  }catch(Exception e) {
				  logger.error("error on get department", e);
				  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

			  }
		}
	   

}
