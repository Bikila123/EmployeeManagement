package com.Internship.Backend.security.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


import javax.naming.directory.SearchControls;

import com.Internship.Backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Internship.Backend.mappers.UserMapper;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attributes;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userRepository;
     @Autowired
    private LdapTemplate ldapTemplate;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public List<String> getAllUserNames() {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        List<String> userNames = new ArrayList<>();
        ldapTemplate.search("", "(objectclass=*)", (Attributes attrs) -> {
            if (attrs.get("samaccountname") != null && attrs.get("samaccountname").get() != null) {
                userNames.add(attrs.get("samaccountname").get().toString());
            }
            return null;
        });

        return userNames;
    }

      public List<Attributes> getADUserDetails(String username, String password) {
        // String ldapServer = "ldap://awash.local:389/dc=awash,dc=local";

        // Configure LDAP context source
        ldapTemplate.setContextSource(contextSource(username, password));

        // Define search controls
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        List<Attributes> results = ldapTemplate.search("", "(samaccountname=" + username + ")",
                searchControls, (AttributesMapper<Attributes>) attrs -> attrs);

        return results;
    }

    private LdapContextSource contextSource(String username, String password) {
        LdapContextSource contextSource = new LdapContextSource();
        //for production
/*         contextSource.setUrl("ldap://awash.local");
        contextSource.setBase("dc=awash,dc=local");
        contextSource.setUserDn(username+"@awash.local"); */
        //for test 
        contextSource.setUrl("ldap://awashtest.local");
        contextSource.setBase("dc=awashtest,dc=local");
        contextSource.setUserDn(username+"@awashtest.local");
        contextSource.setPassword(password);
        contextSource.setReferral("follow"); // Enable referral following
        contextSource.afterPropertiesSet(); // required
        return contextSource;
    }
    public boolean testConnection(String username, String password) {
        try {
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl("ldap://awash.local");
            contextSource.setBase("dc=awash,dc=local");
            contextSource.setUserDn(username+"@awash.local");
            contextSource.setPassword(password);
            contextSource.setReferral("follow"); // Enable referral following
            contextSource.afterPropertiesSet(); // required
            contextSource.getContext(username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getUserRoles(Long user_id) {

        return userRepository.getUserRoles(user_id);
    }

    public List<FetchMenuModel> getMenusByRoles(String[] roles) {
        List<FetchMenuModel> returnMenuHierarchy = new ArrayList<>();
        // FetchMenuModel menuHierarchy = new FetchMenuModel();
        MenuModel[] menus;
        List<ParentMenuModel> pmenu = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            pmenu.clear();
            pmenu = userRepository.getParentMenusByRoles(roles[i]);
            for (int j = 0; j < pmenu.size(); j++) {
                FetchMenuModel menuHierarchy = new FetchMenuModel();
                menuHierarchy.setParent_item_name(pmenu.get(j).getItem_name());
                menus = userRepository.getMenusByRoles(pmenu.get(j).getId(), pmenu.get(j).getRole_id());
                menuHierarchy.setMenus(menus);
                menuHierarchy.setParent_icon(pmenu.get(j).getParent_icon());
                returnMenuHierarchy.add(menuHierarchy);
            }

        }
        return returnMenuHierarchy;
    }

    public Optional<Long> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(Long userId, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser_id(userId);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
        userRepository.saveToken(resetToken);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws ParseException {

        Optional<PasswordResetToken> resetToken = userRepository.findByToken(token);
        if (resetToken.isPresent() && !resetToken.get().isExpired()) {
            UserModel user = new UserModel();
            user.setId(resetToken.get().getUser_id());
            user.setPassword(newPassword);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String d = dateFormat.format(date);

            user.setPassword_changed_date(dateFormat.parse(d));
            userRepository.saveNewPassword(user);
//            userRepository.deleteResetToken(resetToken.get());
        }
    }

    public void updateResetToken(Long id, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setId(id);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
        userRepository.updateToken(resetToken);
    }

    public Boolean forgotpassbyphone(String phone_number) {

         Optional<UserModel> userId = userRepository.isUserActive(phone_number);
         if (userId.isPresent()) {
//             OZSMS ozobj = new OZSMS();
             String otp = generateOTP(6);

             Optional<Long> userWithOtp = userRepository.checkOtpExistance(userId.get().getId());
             if (userWithOtp.isPresent()) {
                 PasswordResetOtp resetOtp = new PasswordResetOtp();
                 resetOtp.setOtp(otp);
                 resetOtp.setId(userWithOtp.get());
                 resetOtp.setExpiryDate(LocalDateTime.now().plusMinutes(10)); // Otp valid for 10 minutes
                 userRepository.updateOtp(resetOtp);
             } else {
                 PasswordResetOtp resetOtp = new PasswordResetOtp();
                 resetOtp.setOtp(otp);
                 resetOtp.setUser_id(userId.get().getId());
                 resetOtp.setExpiryDate(LocalDateTime.now().plusMinutes(10));

                 userRepository.saveOtp(resetOtp);
             }

             String recipient = phone_number;
             String messagedata = "Dear, " + userId.get().getFirst_name() + " " + userId.get().getLast_name() + " your Guarantee Management System verification code is " + otp;
//             List<OzModel> oz = userRepository.getOze();
//             OzModel ozo = oz.get(0);
//             ozobj.sendmessage(messagedata, recipient, ozo);
             return true;
         } else {
             return false;
         }
    }

    public static String generateOTP(int length) {
        String numbers = "0123456789";
        Random rndm_method = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    public Boolean verifyphone(UserModel user) {

            boolean returnValue=false;
            String otp = userRepository.getOtpByPhone(user.getPhone_number());
            if(user.getOtp().equals(otp)){
                returnValue=true;
            }else{
                returnValue= false;
            }
            return returnValue;

        }

    public void changeforgotpassword(UserModel user) throws ParseException {

            Optional<UserModel> isActive = userRepository.isUserActive(user.getPhone_number());
            if(isActive.isPresent()){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String password = encoder.encode(user.getPassword());
                user.setPassword(password);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String d = dateFormat.format(date);

                user.setPassword_changed_date(dateFormat.parse(d));
                userRepository.changeForgotPassword(user);

            }
    }

}