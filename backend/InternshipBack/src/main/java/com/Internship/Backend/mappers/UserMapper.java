package com.Internship.Backend.mappers;

import java.util.List;
import java.util.Optional;

import com.Internship.Backend.models.*;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("select * from tbl_user where username=#{username} and is_enabled=1")
    Optional<UserModel> findByUsername(String username);

    @Select("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS result  FROM  tbl_user WHERE username=#{username}")
    Boolean existsByUsername(String username);

    @Select("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS result  FROM  tbl_user WHERE email=#{email}")
    Boolean existsByEmail(String email);

    @Select("select * from tbl_user where id=#{userId}")
    Optional<UserModel> findById(Long userId);

    @Select("select tr.description from tbl_user_role tur, tbl_role tr where tr.id=tur.role_id and tur.user_id=#{user_id} ")
    List<String> getUserRoles(Long user_id);

    // @Select("select m.*, pm.item_name as parent_item_name from tbl_menu m,
    // tbl_parent_menu pm, tbl_role tr where tr.description = #{role_description}
    // and tr.id=m.role_id and m.parent_item_id=pm.id")
    // MenuModel[] getMenusByRoles(String role_description);

    @Select(" select distinct pm.item_name, pm.id  from tbl_menu m, tbl_parent_menu pm, tbl_role tr where tr.description = #{role_description} and tr.id=m.role_id and m.parent_item_id=pm.id")
    ParentMenuModel[] getParentItems(String role_description);

    @Select("select pm.* from tbl_parent_menu pm, tbl_role tr where tr.description=#{role_description} and tr.id=pm.role_id")
    List<ParentMenuModel> getParentMenusByRoles(String role_description);

    @Select("select m.* from tbl_menu m, tbl_parent_menu pm where pm.id=m.parent_item_id and m.parent_item_id=#{id} and m.role_id=#{role_id} and m.status=1 ")
    MenuModel[] getMenusByRoles(int id, int role_id);

    @Select("SELECT id FROM  tbl_user WHERE email=#{email} and is_enabled = 1 and is_locked=0")
    Optional<Long> findByEmail(String email);

    @Select("SELECT * FROM  tbl_password_reset_token WHERE token=#{token}")
    Optional<PasswordResetToken> findByToken(String token);

    @Insert("insert into tbl_password_reset_token(token,expiryDate,user_id) values(#{token}, #{expiryDate}, #{user_id})")
    void saveToken(PasswordResetToken resetToken);

    @Update("update tbl_user set password = #{password},password_changed_date=#{password_changed_date} where id = #{id}")
    void saveNewPassword(UserModel user);

    @Delete("delete from tbl_password_reset_token where token = #{passwordResetToken}")
    void deleteResetToken(PasswordResetToken passwordResetToken);

    @Select("select id from tbl_password_reset_token where user_id = #{user_id}")
    Optional<Long> checkExistance(Long user_id);

    @Update("update tbl_password_reset_token set token = #{token},expiryDate = #{expiryDate}  where id = #{id}")
    void updateToken(PasswordResetToken resetToken);

    @Select("select * from tbl_user where empId = #{empId}")
    UserModel findByEmployeeIDScheduler(String empId);

    @Update("update tbl_user set region = {region.id}, branch=#{branch.id}, jobPosition=#{jobPosition.id} where empId=#{empId}")
    void updateUserScheduler(UserModel user_store);

    @Select("SELECT id,first_name,last_name FROM  tbl_user WHERE phone_number=#{phone_number} and is_enabled = 1 and is_locked=0")
    Optional<UserModel> isUserActive(String phone_number);

    @Select("select id from tbl_password_reset_otp where user_id = #{user_id}")
    Optional<Long> checkOtpExistance(Long user_id);

    @Update("update tbl_password_reset_otp set otp = #{otp},expiryDate = #{expiryDate}  where id = #{id}")
    void updateOtp(PasswordResetOtp resetOtp);

    @Insert("insert into tbl_password_reset_otp(otp,expiryDate,user_id) values(#{otp}, #{expiryDate}, #{user_id})")
    void saveOtp(PasswordResetOtp resetOtp);

//    @Select("select * from tbl_ozsms")
//    List<OzModel> getOze();

    @Select("select id from tbl_user where phone_number=#{phone_number}")
    Optional<Long> getUserIdByPhone(String phoneNumber);

    @Select("select tp.otp from tbl_password_reset_otp tp, tbl_user tu\n" +
            "where tu.phone_number = #{phone_number} and tu.id = tp.user_id")
    String getOtpByPhone(String phoneNumber);

    @Update("update tbl_user set password=#{password},password_changed_date=#{password_changed_date} where phone_number=#{phone_number}")
    void changeForgotPassword(UserModel user);

    @Select("SELECT * FROM  tbl_password_reset_otp WHERE otp=#{otp} and user_id=#{user_id}")
    Optional<PasswordResetOtp> findByOtp(String otp, Long user_id);

    @Select("select id from tbl_password_reset_token where user_id = #{user_id}")
    Optional<Long> checkExistanceSignUp(Integer user_id);

    @Delete("delete from tbl_password_reset_token where user_id=#{id};update tbl_user set is_enabled=1 where id=#{id};")
    void deleteResetTokenSignup(Long id);

    @Select("select * from tbl_user where is_locked =1 and username=#{username}")
    UserModel getUserLocked(String username);

    @Select("select * from tbl_user where DATEDIFF(DAY, password_changed_date, GETDATE()) >= 30 and username=#{username}")
    UserModel getExpiredPassUser(String username);

    @Select("select login_attempts from tbl_user where username =#{username}")
    int getNumberOfAttempts(String username);

    @Update("update tbl_user set is_locked=1 where username=#{username}")
    void lockUser(String username);

    @Update("update tbl_user set login_attempts=#{attempts} where username=#{username}")
    void addLoginAttempts(String username, int attempts);

    @Update("update tbl_user set password=#{newPassword},password_changed_date=#{password_changed_date} where username=#{username}")
    void changePassword(ChangePasswordModel change);

    @Update("update tbl_user set login_attempts=0 where username=#{username}")
    void updateLoginAttempts(String username);

    @Select("select * from tbl_refresh_token where ipaddress=#{ipAddress} and browser=#{browser} and browser_version=#{browserVersion} and user_id=#{user_id}")
    RefreshToken findByIpAddressAndBrowserAndBrowserVersion(String ipAddress, String browser, String browserVersion, Long user_id);
}
