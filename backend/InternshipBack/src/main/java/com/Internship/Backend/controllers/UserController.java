package com.Internship.Backend.controllers;

import com.Internship.Backend.mappers.UserMapper;
import com.Internship.Backend.models.*;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/User/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    
    @Autowired
    UserMapper userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("getUsers")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> response = service.getUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("addUser")
    public ResponseEntity<MessageResponse> addUser(@RequestBody UserModel user){

        String message;
        try {
            service.addUser(user);
            message = "User Added Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not Add User : " + ". Error: " + e.getMessage();
            logger.error("Error Occured", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("updateUser")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody UserModel user){
        String message;
        try {
            service.updateUser(user);
            message = "User Updated Successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error Occured", e);
            message = "Could not Update User : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @GetMapping("phoneexists/{phone_number}")
    public ResponseEntity<Boolean> PhoneAlreadyExists(@PathVariable("phone_number") String phone_number){
        try {

            Boolean response = service.PhoneAlreadyExists(phone_number);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @GetMapping("userexists/{username}")
    public ResponseEntity<Boolean> UsernameAlreadyExists(@PathVariable("username") String username){
        try {
            Boolean response = service.UsernameAlreadyExists(username);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @GetMapping("emailexists/{email}")
    public ResponseEntity<Boolean> EmailAlreadyExists(@PathVariable("email") String email){
        try {
            Boolean response = service.EmailAlreadyExists(email);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }
    @GetMapping("/empIdexists/{trimmedEmpId}")
    public ResponseEntity<Boolean> EmpIdAlreadyExists(@PathVariable("trimmedEmpId") String trimmedEmpId){
        try {
            Boolean response = service.EmpIdAlreadyExists(trimmedEmpId);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }
    @GetMapping("/empIdHrexists/{trimmedEmpId}")
    public ResponseEntity<Boolean> EmpIdHrAlreadyExists(@PathVariable("trimmedEmpId") String trimmedEmpId){
        try {
            Boolean response = service.EmpIdHrAlreadyExists(trimmedEmpId);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @GetMapping("getunits")
    public ResponseEntity<List<BranchModel>> getUnits(){
        try {
            List<BranchModel> units = service.getUnits();
            return new ResponseEntity<List<BranchModel>>(units, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getjobpositions")
    public ResponseEntity<List<JobPositionModel>> getJobPosition(){
        try {
            List<JobPositionModel> jobPosition = service.getJobPosition();
            return new ResponseEntity<List<JobPositionModel>>(jobPosition, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("userrole")
    public ResponseEntity<List<UserRoleMappingModel>> fetchUserRoleMappings() {
        try {
            List<UserRoleMappingModel> response = service.fetchUserRoleMappings();
            return new ResponseEntity<List<UserRoleMappingModel>>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("mapuserrole")
    public ResponseEntity<UserRoleMappingModel> mapUserRole(@RequestBody UserRoleMappingModel model){
        try {
           UserRoleMappingModel response= service.mapUserRole(model);
             return new ResponseEntity<UserRoleMappingModel>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
             return null;
        }
    }

    @PutMapping("updateMapuserrole")

    public ResponseEntity<MessageResponse> updateMapUserRole(@RequestBody UserRoleMappingModel model){
        try {
           String response= service.updateMapUserRole(model);
           return new ResponseEntity<MessageResponse>(new MessageResponse(response), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
             return null;
        }
    }

    @PostMapping("deleteMapuserrole")
        public ResponseEntity<MessageResponse> deleteMapuserrole(@RequestBody DeletedUserRoleMappingModel model){
        try {
           String response= service.deleteMapuserrole(model);
           return new ResponseEntity<MessageResponse>(new MessageResponse(response), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
             return null;
        }
    }
    @PostMapping("phoneexistsforupdate")
    public ResponseEntity<Boolean> PhoneExistsForUpdate(@RequestBody ValidationCheckPayload payload){
        try {
            Boolean response = service.PhoneExistsForUpdate(payload);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @PostMapping("userexistsforupdate")
    public ResponseEntity<Boolean> UserExistsForUpdate(@RequestBody ValidationCheckPayload payload){
        try {
            Boolean response = service.UserExistsForUpdate(payload);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }
    @PostMapping("emailexistsforupdate")
    public ResponseEntity<Boolean> EmailExistsForUpdate(@RequestBody ValidationCheckPayload payload){
        try {
            Boolean response = service.EmailExistsForUpdate(payload);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @PostMapping("empIdAlreadyExistsForUpdate")
    public ResponseEntity<Boolean> empIdAlreadyExistsForUpdate(@RequestBody ValidationCheckPayload payload){
        try {
            Boolean response = service.empIdAlreadyExistsForUpdate(payload);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<MessageResponse> signUpUser(@RequestBody UserModel model){
        try {
            String response= service.signUpUser(model);
            return new ResponseEntity<MessageResponse>(new MessageResponse(response), HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return null;
        }

    }

        @PostMapping("createpassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordPayload payload) {
        String message;
        System.out.println(payload);
        try {
            Optional<PasswordResetToken> createToken = userRepository.findByToken(payload.getToken());
            if (createToken.isPresent() && !createToken.get().isExpired()) {
                service.createPassword(payload.getToken(), payload.getNewPassword());
                message = "Password create successfully.";
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } else {
                message = "Invalid or expired token.";
                return ResponseEntity.badRequest().body(new MessageResponse(message));
            }

        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Reset Password : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }

    }

    @GetMapping("getBranch/{user_id}")
    public ResponseEntity<BranchModel> getBranchbyUserId(@PathVariable("user_id") Long user_id){
        try {
            BranchModel response = service.getBranchbyUserId(user_id);
            return new ResponseEntity<BranchModel>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("changePassword")
    public void changePassword(@RequestBody ChangePasswordModel change) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(change.getUsername(), change.getOldPassword()));

            if (authentication != null) {
                String newPassword = encoder.encode(change.getNewPassword());
                change.setNewPassword(newPassword);
                service.changePassword(change);

            } else {
                System.out.println("username and old password is not authenticated!");
            }

        } catch (BadCredentialsException ex) {
            logger.error("Error Occured", ex);
            throw new Exception("Incorrect Username or Password", ex);
        }
    }

    @GetMapping("getNumberOfDaysLeft/{user_id}")
    public ResponseEntity<Long> getNumberOfDaysLeft(@PathVariable Long user_id){
          Long numberofdays = service.getNumberOfDaysLeft(user_id);
          return new ResponseEntity<>(numberofdays, HttpStatus.OK);
    }

}
