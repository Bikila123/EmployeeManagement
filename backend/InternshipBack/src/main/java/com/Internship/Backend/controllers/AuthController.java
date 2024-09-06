package com.Internship.Backend.controllers;

import java.net.ConnectException;
import java.util.*;

import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;

import com.Internship.Backend.models.*;
import com.Internship.Backend.security.jwt.JwtTokenForEmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.CommunicationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.Internship.Backend.exception.TokenRefreshException;
import com.Internship.Backend.payload.request.LoginRequest;
import com.Internship.Backend.payload.request.SignupRequest;
import com.Internship.Backend.payload.request.TokenRefreshRequest;
import com.Internship.Backend.payload.response.JwtResponse;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.payload.response.TokenRefreshResponse;
import com.Internship.Backend.mappers.RefreshTokenMapper;
import com.Internship.Backend.mappers.RoleMapper;
import com.Internship.Backend.mappers.UserMapper;
import com.Internship.Backend.security.jwt.JwtUtils;
import com.Internship.Backend.security.services.RefreshTokenService;
import com.Internship.Backend.security.services.UserDetailsImpl;
import com.Internship.Backend.security.services.UserDetailsServiceImpl;
import com.Internship.Backend.services.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userRepository;

    @Autowired
    RoleMapper roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtTokenForEmailService jwtTokenService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    RefreshTokenMapper refreshTokenRepository;

    @Autowired
    private UserService service;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest reqadd) {
        // do not change the return message It has been used on frontend to show
        // messages
        try {

            String username = loginRequest.getUsername().substring(0, loginRequest.getUsername().indexOf('@'));
            loginRequest.setUsername(username);

            // Get IP address of the requester
            String ipAddress = reqadd.getRemoteAddr();
            // Get browser information
            String browser = reqadd.getHeader("User-Agent");

            // Extract browser version from User-Agent header
            String browserVersion = null;
            if (browser != null && browser.contains("rv")) {
                browserVersion = browser.substring(browser.indexOf("rv") + 3);
            } else if (browser != null && browser.contains("MSIE")) {
                browserVersion = browser.substring(browser.indexOf("MSIE") + 5);
            } else if (browser != null && browser.contains("Chrome")) {
                browserVersion = browser.substring(browser.indexOf("Chrome") + 7);
            } else if (browser != null && browser.contains("Firefox")) {
                browserVersion = browser.substring(browser.indexOf("Firefox") + 8);
            } else if (browser != null && browser.contains("Safari")) {
                browserVersion = browser.substring(browser.indexOf("Version") + 8);
            }

            // check if the user is locked
            UserModel lockedUser = userRepository.getUserLocked(loginRequest.getUsername());
            if (lockedUser != null) {
                return new ResponseEntity<>("Locked", HttpStatus.OK);
            }

            // to check if the user already logged in
            Optional<UserModel> isuserExists = userRepository.findByUsername(loginRequest.getUsername());
            if (isuserExists.isPresent()) {
                // check if the user is logged in
                RefreshToken token = refreshTokenRepository.isUserLoggedIn(isuserExists.get().getId());
                if (token != null) {
                    if ((!token.getBrowser().equalsIgnoreCase(browser)
                            || !token.getBrowser_version().equalsIgnoreCase(browserVersion)
                            || !token.getIpaddress().equalsIgnoreCase(ipAddress))
                            && !loginRequest.getForcelogout().equalsIgnoreCase("yes")) {
                        return new ResponseEntity<>("UserLoggedIn", HttpStatus.OK);
                    }
                }
                // List<Attributes> details = userDetailsService.getADUserDetails(loginRequest.getUsername(),
                //         loginRequest.getPassword());
                // System.out.println("Details:"+details.get(0));

            // Existing user authentication method
            Authentication authentication = authenticationManager
            .authenticate(new
            UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // UserDetailsImpl userDetails = new UserDetailsImpl(loginRequest.getUsername(),
            // password, authorities);
                // if (authentication != null) {
                    UserDetailsImpl userDetails = new UserDetailsImpl(isuserExists.get().getId(),
                            isuserExists.get().getUsername(), isuserExists.get().getEmail(),
                            isuserExists.get().getPassword(), null);

                    String jwt = jwtUtils.generateJwtToken(userDetails);

                    List<String> roles = userDetailsService.getUserRoles(userDetails.getId());
                    if (loginRequest.getForcelogout().equalsIgnoreCase("yes")) {
                        refreshTokenRepository.deleteByUser(isuserExists.get().getId());
                    }
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), ipAddress,
                            browser, browserVersion);
                            //to check if pass expired
                    // UserModel expiredUser = userRepository.getExpiredPassUser(loginRequest.getUsername());
                    // make login attempts 0
                    userRepository.updateLoginAttempts(loginRequest.getUsername());
                    
                    //checking if passexpired but since we are authenticating user from AD it has been set to false
                    boolean expired = false;
                    // if (expiredUser != null) {
                    //     expired = true;
                    // }
                    logger.info("user with username:" + loginRequest.getUsername() + " is loggeding from ip address:"
                            + ipAddress + " on browser:" + browser);
                    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                            userDetails.getUsername(), userDetails.getEmail(), roles, expired));
//                 } else {
//                         int login_attempts = userRepository.getNumberOfAttempts(loginRequest.getUsername());
//                         int loginattemptLeft = 5 - login_attempts;
//                         if (login_attempts >= 4) {
//                             userRepository.lockUser(loginRequest.getUsername());
//                             return new ResponseEntity<>(
//                                     "Invalid credentials. You have no more attempt(s) your account is locked out.",
//                                     HttpStatus.OK);
//                         } else {
//                             userRepository.addLoginAttempts(loginRequest.getUsername(), login_attempts + 1);
//                             loginattemptLeft = loginattemptLeft - 1;
//                             return new ResponseEntity<>("Invalid credentials. You have " + loginattemptLeft
//                                     + " more attempt(s) before your account gets locked out.", HttpStatus.OK);
//                         }
//                 }

            } else {
                return new ResponseEntity<>("notexist", HttpStatus.OK);
            }

        } catch (Exception e) {
            logger.error("Error Occured", e);
            System.out.println("message expected:"+e.getMessage());
            if(e instanceof CommunicationException || e instanceof ConnectException){
                return new ResponseEntity<>("notconnected", HttpStatus.OK);
            }else{
                Optional<UserModel> isuserExists = userRepository.findByUsername(loginRequest.getUsername());
                if (isuserExists.isPresent()) {
                    int login_attempts = userRepository.getNumberOfAttempts(loginRequest.getUsername());
                    int loginattemptLeft = 5 - login_attempts;
                    if (login_attempts >= 4) {
                        userRepository.lockUser(loginRequest.getUsername());
                        return new ResponseEntity<>(
                                "Invalid credentials. You have no more attempt(s) your account is locked out.",
                                HttpStatus.OK);
                    } else {
                        userRepository.addLoginAttempts(loginRequest.getUsername(), login_attempts + 1);
                        loginattemptLeft = loginattemptLeft - 1;
                        return new ResponseEntity<>("Invalid credentials. You have " + loginattemptLeft
                                + " more attempt(s) before your account gets locked out.", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>("notexist", HttpStatus.OK);
                }
            }
            
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create new user's account
            UserModel user = new UserModel(signUpRequest.getUsername(), signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            Set<String> strRoles = signUpRequest.getRole();
            Set<RolesModel> roles = new HashSet<>();

            if (strRoles == null) {
                RolesModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            RolesModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "mod":
                            RolesModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                            break;
                        default:
                            RolesModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            // userRepository.save(user);
            logger.info("user with username:" + signUpRequest.getUsername() + " has been successfully registered");
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request, HttpServletRequest reqadd) {
        String requestRefreshToken = request.getRefreshToken();
        // System.out.println("request:"+request);
        UserModel tokenuser = refreshTokenService.getUsersDetail(requestRefreshToken);

        // Get IP address of the requester
        String ipAddress = reqadd.getRemoteAddr();
        // Get browser information
        String browser = reqadd.getHeader("User-Agent");

        // Extract browser version from User-Agent header
        final String browserVersion;
        if (browser != null && browser.contains("rv")) {
            browserVersion = browser.substring(browser.indexOf("rv") + 3);
        } else if (browser != null && browser.contains("MSIE")) {
            browserVersion = browser.substring(browser.indexOf("MSIE") + 5);
        } else if (browser != null && browser.contains("Chrome")) {
            browserVersion = browser.substring(browser.indexOf("Chrome") + 7);
        } else if (browser != null && browser.contains("Firefox")) {
            browserVersion = browser.substring(browser.indexOf("Firefox") + 8);
        } else if (browser != null && browser.contains("Safari")) {
            browserVersion = browser.substring(browser.indexOf("Version") + 8);
        } else {
            browserVersion = "Unknown";
        }

        try {
            logger.info("refresh token sent from IP Address:" + ipAddress + " successfully generate jwt");
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(user -> {
                        String token = jwtUtils.generateTokenFromUsername(tokenuser.getUsername());
                        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(tokenuser.getId(),
                                ipAddress, browser, browserVersion);
                        return ResponseEntity.ok(new TokenRefreshResponse(token, newRefreshToken.getToken()));
                    })
                    .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                            "Refresh token is not in database!"));
        } catch (Exception e) {
            logger.error("Error Occured on refresh token sent from IP Address:" + ipAddress, e);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/signout/{id}")
    public ResponseEntity<?> logoutUser(@PathVariable Long id) {
        refreshTokenService.deleteByUserId(id);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PostMapping("/fetchmenu")
    public ResponseEntity<List<FetchMenuModel>> getMenusByRoles(@RequestBody String[] roles) {
        List<FetchMenuModel> response = userDetailsService.getMenusByRoles(roles);
        return new ResponseEntity<List<FetchMenuModel>>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody UserModel request) {
        String message;
        try {
            String email = request.getEmail();

            Optional<Long> userId = userDetailsService.findByEmail(email);
            if (userId.isPresent()) {
                // Generate token and send reset password email
                String token = jwtTokenService.generateToken(email);
                Optional<Long> userWithToken = userRepository.checkExistance(userId.get());
                if (userWithToken.isPresent()) {
                    userDetailsService.updateResetToken(userWithToken.get(), token);
                } else {
                    userDetailsService.createPasswordResetTokenForUser(userId.get(), token);
                }
                sendResetPasswordEmail(email, token);
                message = "Email sent successfully.";
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } else {
                message = "User not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(message));
            }

        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Update User : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    private void sendResetPasswordEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("To reset your password, click the link below:\n\n"
                + "http://localhost:4200/reset-password?token=" + token);
        message.setFrom(senderEmail);
        javaMailSender.send(message);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordPayload payload) {
        String message;
        try {
            Optional<PasswordResetToken> resetToken = userRepository.findByToken(payload.getToken());
            if (resetToken.isPresent() && !resetToken.get().isExpired()) {
                userDetailsService.resetPassword(payload.getToken(), encoder.encode(payload.getNewPassword()));
                message = "Password reset successfully.";
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

    @GetMapping("/authenticate/{token}")
    public ResponseEntity<?> authenticateResetToken(@PathVariable String token) {
        String message;
        try {

            Optional<PasswordResetToken> resetToken = userRepository.findByToken(token);
            if (resetToken.isPresent() && !resetToken.get().isExpired()) {
                // Token is valid, redirect to reset password page
                message = "Token authenticated successfully. Redirect to reset password page.";
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } else {
                message = "Invalid or expired token.";
                return ResponseEntity.badRequest().body(new MessageResponse(message));
            }
        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not authenticate token : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }

    }

    @PostMapping("forgotpassbyphone")
    public ResponseEntity<Boolean> forgotpassbyphone(@RequestBody String phoneNumber) {
        try {
            // String phoneNumber = user.getPhone_number();
            Boolean response = userDetailsService.forgotpassbyphone(phoneNumber);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("verifyphone")
    public ResponseEntity<Boolean> verifyphone(@RequestBody UserModel user) {
        try {

            Boolean response = userDetailsService.verifyphone(user);
            return new ResponseEntity<Boolean>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("changeforgotpassword")
    public ResponseEntity<MessageResponse> changeforgotpassword(@RequestBody UserModel user) {

        String message;

        try {

            Optional<Long> userId = userRepository.getUserIdByPhone(user.getPhone_number());
            Optional<PasswordResetOtp> resetToken = userRepository.findByOtp(user.getOtp(), userId.get());

            if (resetToken.isPresent() && !resetToken.get().isExpired()) {
                userDetailsService.changeforgotpassword(user);
                message = "success";
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } else {
                message = "Invalid or expired otp.";
                return ResponseEntity.badRequest().body(new MessageResponse(message));
            }

        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not Reset Password : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/authenticateResetOtp")
    public ResponseEntity<?> authenticateResetOtp(@RequestParam("otp") String otp,
            @RequestBody Map<String, String> requestBody) {
        String message;
        try {
            String phone_number = requestBody.get("phone_number");
            Optional<Long> userId = userRepository.getUserIdByPhone(phone_number);
            Optional<PasswordResetOtp> resetToken = userRepository.findByOtp(otp, userId.get());

            if (resetToken.isPresent() && !resetToken.get().isExpired()) {
                message = "Otp authenticated successfully. Redirect to change password page.";
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } else {
                message = "Invalid or expired otp.";
                return ResponseEntity.badRequest().body(new MessageResponse(message));
            }

        } catch (Exception e) {
            logger.error("Error Occured", e);
            message = "Could not authenticate otp : " + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

}