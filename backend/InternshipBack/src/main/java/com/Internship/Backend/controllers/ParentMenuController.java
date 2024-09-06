package com.Internship.Backend.controllers;



import com.Internship.Backend.models.ParentMenuModel;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.ParentMenuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/ParentMenu")
public class ParentMenuController {
    private static final Logger logger = LoggerFactory.getLogger(ParentMenuController.class);

    @Autowired
    private ParentMenuService service;

    @GetMapping("/fetchparentmenus")
    public ResponseEntity<List<ParentMenuModel>> fetchparentmenus() {
        List<ParentMenuModel> response = service.fetchparentmenus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateParentMenu(@RequestBody ParentMenuModel model) {

        String message;
        try {
            service.updateParentMenu(model);
            message = "Parent Menu Updated Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Update Parent Menu: " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/addParentMenu")
    public ResponseEntity<MessageResponse> addParentMenu(@RequestBody ParentMenuModel model){

        String message;
        try {
            service.addParentMenu(model);
            message = "Parent Menu Added Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Add Parent Menu: " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }
}
