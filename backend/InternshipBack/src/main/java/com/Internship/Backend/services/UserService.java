package com.Internship.Backend.services;

import com.Internship.Backend.mappers.UserManageMapper;
import com.Internship.Backend.mappers.UserMapper;
import com.Internship.Backend.models.*;

import com.Internship.Backend.security.jwt.JwtTokenForEmailService;
import com.Internship.Backend.security.services.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserManageMapper mapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    JwtTokenForEmailService jwtTokenService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public List<UserModel> getUsers() {
        return mapper.getUsers();
    }

    public UserService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public void addUser(UserModel user) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // String password = encoder.encode(user.getPassword());
        // user.setPassword(password);
        user.setCreated_date(dateFormat.parse(d));
        // user.setPassword_changed_date(date);

        Long regionId = mapper.getRegionByBranchId(user.getBranch_id());
        user.setRegion_id(regionId);
        // String username = user.getEmail().substring(0, user.getEmail().indexOf('@'));
        // user.setUsername(username);

        JobPositionModel jobPosition = mapper.getJoBPositionByEmpId(user.getEmpId());
        user.setPosition_id(jobPosition.getId());

        mapper.addUser(user);

        UserRoleMappingModel userrolemodel = new UserRoleMappingModel();
        userrolemodel.setCreated_date(dateFormat.parse(d));
        userrolemodel.setCreated_by(user.getCreated_by());
        userrolemodel.setRole_id(user.getRole_id());
        userrolemodel.setUser_id(user.getId());
        UserRoleMappingModel isExist = mapper.isUserRoleExist(userrolemodel);
        if (isExist == null) {
            mapper.mapUserRole(userrolemodel);
        }

                    //Audit trail
                    AuditTrailModel trail = new AuditTrailModel();
                    trail.setUser_id(user.getCreated_by());
                    trail.setAction("Create");
                    trail.setTimestamp(dateFormat.parse(d));
                    trail.setDetails("User with username: " + user.getUsername()+" created");
                    mapper.saveToAuditTrail(trail);
    }

    public void updateUser(UserModel user) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);

        user.setModified_date(dateFormat.parse(d));
        Long regionId = mapper.getRegionByBranchId(user.getBranch_id());
        user.setRegion_id(regionId);
        // String username = user.getEmail().substring(0, user.getEmail().indexOf('@'));
        // user.setUsername(username);

        JobPositionModel jobPosition = mapper.getJoBPositionByEmpId(user.getEmpId());
        user.setPosition_id(jobPosition.getId());

        mapper.updateUser(user);
        if(user.getIs_locked()==0){
            user.setLogin_attempts(0);
            mapper.updateLoginAttempts(user);
        }
        UserRoleMappingModel userrolemodel = new UserRoleMappingModel();
        userrolemodel.setModified_date(dateFormat.parse(d));
        userrolemodel.setModified_by(user.getModified_by());
        userrolemodel.setRole_id(user.getRole_id());
        userrolemodel.setUser_id(user.getId());
        mapper.updateMapUserRole(userrolemodel);
        

                            //Audit trail
                            AuditTrailModel trail = new AuditTrailModel();
                            trail.setUser_id(user.getModified_by());
                            trail.setAction("Update");
                            trail.setTimestamp(dateFormat.parse(d));
                            trail.setDetails("User with username: " + user.getUsername()+" updated");
                            mapper.saveToAuditTrail(trail);
    }

    public Boolean PhoneAlreadyExists(String phoneNumber) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.PhoneAlreadyExists(phoneNumber);
            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public Boolean UsernameAlreadyExists(String username) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.UsernameAlreadyExists(username);
            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public Boolean EmailAlreadyExists(String email) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.EmailAlreadyExists(email);
            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public List<UserRoleMappingModel> fetchUserRoleMappings() {
        return mapper.fetchUserRoleMappings();
    }

    public UserRoleMappingModel mapUserRole(UserRoleMappingModel model) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        model.setCreated_date(dateFormat.parse(d));
        UserRoleMappingModel isExist = mapper.isUserRoleExist(model);
        if (isExist == null) {
            mapper.mapUserRole(model);
            return model;
        } else {
            return null;
        }
    }

    public String updateMapUserRole(UserRoleMappingModel model) throws ParseException {
        UserRoleMappingModel isExist = mapper.isUserRoleExist(model);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        model.setModified_date(dateFormat.parse(d));
        if (isExist == null) {
            mapper.updateMapUserRole(model);
            return "updated";
        }
        return null;
    }

    public String deleteMapuserrole(DeletedUserRoleMappingModel model) throws ParseException {
        UserRoleMappingModel userrole = mapper.getUserRole(model.getId());
        model.setModified_by(userrole.getModified_by());
        model.setModified_by(userrole.getModified_by());
        model.setCreated_by(userrole.getCreated_by());
        model.setCreated_date(userrole.getCreated_date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        model.setDeleted_date(dateFormat.parse(d));
        try {
            mapper.deleteUserRoleMapping(model);
                                //Audit trail
                                AuditTrailModel trail = new AuditTrailModel();
                                trail.setUser_id(userrole.getModified_by());
                                trail.setAction("Delete");
                                trail.setTimestamp(dateFormat.parse(d));
                                trail.setDetails("User with username: " + userrole.getUsername()+" with role"+model.getRole_id()+" deleted");
                                mapper.saveToAuditTrail(trail);
            return "deleted";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public Boolean PhoneExistsForUpdate(ValidationCheckPayload payload) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.PhoneExistsForUpdate(payload.getId(), payload.getUnique());

            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean UserExistsForUpdate(ValidationCheckPayload payload) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.UserExistsForUpdate(payload.getId(), payload.getUnique());

            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean EmailExistsForUpdate(ValidationCheckPayload payload) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.EmailExistsForUpdate(payload.getId(), payload.getUnique());

            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean EmpIdAlreadyExists(String empId) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.EmpIdAlreadyExists(empId);
            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public Boolean EmpIdHrAlreadyExists(String empId) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.EmpIdHrAlreadyExists(empId);
            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public List<BranchModel> getUnits() {

        return mapper.getUnits();
    }

    public List<JobPositionModel> getJobPosition() {
        return mapper.getJobPosition();
    }

    public String signUpUser(UserModel model) throws ParseException {
        UserCopyFromHR isuserexistonhr = mapper.isUserExistOnHr(model);
        if (isuserexistonhr != null) {
            RegionModel region = mapper.getRegionId(isuserexistonhr.getDeptLocation());
            model.setRegion(region);
            String username = model.getEmail().substring(0, model.getEmail().indexOf('@'));
            model.setUsername(username);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String d = dateFormat.format(date);
            model.setCreated_date(dateFormat.parse(d));

            model.setPassword_changed_date(date);
            UserModel isUserExis = mapper.isUserExist(model.getUsername());
            if (isUserExis == null) {
                mapper.signUpUser(model);
                Integer user_id = mapper.getUserId(model.getUsername());
                mapper.updateSignUpUser(user_id);
                Integer role_id = mapper.getPositionRole(model.getJobPosition().getId());

                UserRoleMappingModel userrole = new UserRoleMappingModel();
                userrole.setCreated_by(user_id);
                // userrole.setUser_id(user_id);
                userrole.setCreated_date(dateFormat.parse(d));
                userrole.setRole_id(role_id);
                mapper.signupUserRole(userrole);
                // Generate token and send reset password email
                String token = jwtTokenService.generateToken(model.getEmail());
                Optional<Long> userWithToken = userMapper.checkExistanceSignUp(user_id);
                if (userWithToken.isPresent()) {
                    userDetailsService.updateResetToken(userWithToken.get(), token);
                } else {
                    userDetailsService.createPasswordResetTokenForUser(user_id.longValue(), token);
                }
                sendCreatePasswordEmail(model.getEmail(), token);
                return "Email verification have been sent to your awash email!";
            } else {
                return "User Already Exists";
            }

        } else {
            return "The information you provide with this employee id is not valid";
        }
    }

    private void sendCreatePasswordEmail(String email, String token) {
        // SimpleMailMessage message = new SimpleMailMessage();

        // message.setTo(email);
        // message.setSubject("Email verification and pasword creation");

        // message.setText("To verify your email and create password, click the link
        // below::\n\n"
        // + "http://localhost:4200/create-password?token=" + token);
        // message.setFrom(senderEmail);
        // javaMailSender.send(message);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // String htmlBody = "<html><body><div style=\"background-color: #283895;\"><h2 style=\"color:white;\">GMS Email Verification and Password Creation</h2></div>"
            //         + "<p>To verify your email and create password, click the link below:</p>"
            //         + "<p><a href=\"http://localhost:4200/create-password?token=" + token + "\">Verify Email</a></p>"
            //         + "</body></html>";
            String htmlBody = "<html>" +
                      "<head>" +
                          "<style>" +
                              "body {" +
                                  "font-family: Arial, sans-serif;" +
                                  "margin: 0;" +
                                  "padding: 0;" +
                                  "background-color: #f4f4f4;" +
                              "}" +
                              ".container {" +
                                  "max-width: 600px;" +
                                  "margin: 20px auto;" +
                                  "padding: 20px;" +
                                  "background-color: #fff;" +
                                  "border-radius: 5px;" +
                                  "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                              "}" +
                              "h2 {" +
                                  "color: #283895;" +
                                  "text-align: center;" +
                              "}" +
                              "p {" +
                                  "color: #333;" +
                                  "line-height: 1.6;" +
                                  "margin-bottom: 20px;" +
                              "}" +
                              ".btn {" +
                                  "display: inline-block;" +
                                  "padding: 10px 20px;" +
                                  "background-color: #283895;" +
                                  "color: #fff;" +
                                  "text-decoration: none;" +
                                  "border-radius: 5px;" +
                              "}" +
                              ".btn:hover {" +
                                  "background-color: #1a237e;" +
                              "}" +
                          "</style>" +
                      "</head>" +
                      "<body>" +
                          "<div class='container'>" +
                              "<h2>GMS Email Verification and Password Creation</h2>" +
                              "<p>To verify your email and create a password, click the link below:</p>" +
                              "<p><a class='btn' href='http://localhost:4200/create-password?token=" + token + "'>Verify Email</a></p>" +
                          "</div>" +
                      "</body>" +
                  "</html>";


            helper.setTo(email);
            helper.setFrom(senderEmail);
            helper.setSubject("Guarantee Management System email verification and password creation");
            helper.setText(htmlBody, true); // Set HTML content to true

            javaMailSender.send(message);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            // TODO: handle exception
        }

    }

    @Transactional
    public void createPassword(String token, String createpassword) throws ParseException {
        Optional<PasswordResetToken> createToken = userMapper.findByToken(token);
        if (createToken.isPresent() && !createToken.get().isExpired()) {
            UserModel user = new UserModel();
            user.setId(createToken.get().getUser_id());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(createpassword);
            user.setPassword(password);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String d = dateFormat.format(date);

            user.setPassword_changed_date(dateFormat.parse(d));
            userMapper.saveNewPassword(user);
            userMapper.deleteResetTokenSignup(user.getId());
        }
    }

    public BranchModel getBranchbyUserId(Long user_id) {
        return mapper.getBranchbyUserId(user_id);
    }

    public void changePassword(ChangePasswordModel change) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);

        change.setPassword_changed_date(dateFormat.parse(d));
        userMapper.changePassword(change);

    }

    public Boolean empIdAlreadyExistsForUpdate(ValidationCheckPayload payload) {
        try {
            Boolean returnValue = false;
            UserModel user = mapper.empIdAlreadyExistsForUpdate(payload.getId(), payload.getUnique());

            if (user != null) {
                returnValue = true;
            } else {
                returnValue = false;
            }
            return returnValue;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getNumberOfDaysLeft(Long user_id) {
        return mapper.getNumberOfDaysLeft(user_id);
    }

}
