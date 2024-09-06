package com.Internship.Backend.controllers;

import com.Internship.Backend.models.RolesModel;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/Role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService service;

    @GetMapping("/fetchAll")
    public ResponseEntity<List<RolesModel>> fetchRoles() {
        List<RolesModel> response = service.fetchRoles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateRole(@RequestBody RolesModel roleModel) {

        String message;
        try {
            service.updateRole(roleModel);
            message = "User Role Updated Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Update User Role: " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addRole(@RequestBody RolesModel model){

        String message;
        try {
            service.addRole(model);
            message = "User Role Added Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Add User Role: " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }
}
