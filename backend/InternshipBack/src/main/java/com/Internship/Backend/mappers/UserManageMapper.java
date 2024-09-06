package com.Internship.Backend.mappers;

import com.Internship.Backend.models.AuditTrailModel;
import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.DeletedUserRoleMappingModel;
import com.Internship.Backend.models.JobPositionModel;
import com.Internship.Backend.models.RegionModel;
import com.Internship.Backend.models.RolesModel;
import com.Internship.Backend.models.UserCopyFromHR;
import com.Internship.Backend.models.UserModel;
import com.Internship.Backend.models.UserRoleMappingModel;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserManageMapper {

        @Options(useGeneratedKeys = true, keyProperty = "id")
        @Insert("insert into tbl_user(username, password,first_name,last_name,middle_name,email,isAccountNonExpired,phone_number,is_loggedin,created_by,is_locked,created_date,is_enabled, empId, branch, region, jobPosition)"
                        + " values(#{username},#{password},#{first_name}, #{last_name}, #{middle_name}, #{email},1, #{phone_number},0, #{created_by},0,#{created_date},1, #{empId}, #{branch_id}, #{region_id}, #{position_id})")
        void addUser(UserModel user);

        @Select("SELECT u.id,u.username,u.first_name,u.middle_name,u.last_name,u.email,u.created_by,u.created_date,u.is_enabled,u.phone_number,u.modified_by,u.modified_date, u.is_locked,u.empId, u.branch as branch_id, u.jobPosition as position_id, ur.role_id FROM tbl_user u, tbl_user_role ur where u.id=ur.user_id")
        List<UserModel> getUsers();

        @Update("update tbl_user set is_enabled = 0 where id=#{id}")
        UserModel deactivateUser(UserModel user);

        @Update("update tbl_user set is_enabled = 1 where id=#{id}")
        UserModel activateUser(UserModel user);

        @Select("select * from tbl_user where username =#{username}")
        UserModel UsernameAlreadyExists(String username);

        @Select("select * from tbl_user where email =#{email}")
        UserModel EmailAlreadyExists(String email);

        @Select("select * from tbl_user where phone_number =#{phone_number}")
        UserModel PhoneAlreadyExists(String phone_number);

        @Update("update tbl_user set first_name =#{first_name},middle_name =#{middle_name},last_name =#{last_name},email =#{email},phone_number =#{phone_number},is_enabled=#{is_enabled},modified_by =#{modified_by},"
                        + "modified_date=#{modified_date}, is_locked=#{is_locked}, branch=#{branch_id}, empId=#{empId}, region=#{region_id}, jobPosition=#{position_id} where id = #{id}")
        void updateUser(UserModel user);

        @Select("select ur.*, tu.username, tr.description from tbl_user_role ur, tbl_user tu, tbl_role tr where ur.role_id = tr.id and ur.user_id = tu.id")
        List<UserRoleMappingModel> fetchUserRoleMappings();

        @Insert("insert into tbl_user_role(user_id, role_id, created_by, created_date) values(#{user_id}, #{role_id}, #{created_by}, #{created_date})")
        void mapUserRole(UserRoleMappingModel model);

        @Select("select * from tbl_user_role where user_id=#{user_id} and role_id=#{role_id}")
        UserRoleMappingModel isUserRoleExist(UserRoleMappingModel model);

        @Update("update tbl_user_role set role_id=#{role_id},modified_by=#{modified_by}, modified_date=#{modified_date} where user_id=#{user_id}")
        void updateMapUserRole(UserRoleMappingModel model);

        @Select("select * from tbl_user where id !=#{id} and phone_number =#{unique}")
        UserModel PhoneExistsForUpdate(Long id, String unique);

        @Select("select * from tbl_user where id !=#{id} and username =#{unique}")
        UserModel UserExistsForUpdate(Long id, String unique);

        @Select("select * from tbl_user where id !=#{id} and email =#{unique}")
        UserModel EmailExistsForUpdate(Long id, String unique);

        @Select("select * from tbl_user_role where id=#{id}")
        UserRoleMappingModel getUserRole(int id);

        @Insert("insert into tbl_deleted_user_role(role_id, user_id, created_by, created_date, modified_by, modified_date,deleted_by, deleted_date) values(#{role_id}, #{user_id}, #{created_by}, #{created_date}, #{modified_by}, #{modified_date}, #{deleted_by}, #{deleted_date}); delete from tbl_user_role where id=#{id}")
        void deleteUserRoleMapping(DeletedUserRoleMappingModel model);

        void removeAllUserRoles(Long id);

        void addUserRole(Long id, RolesModel rolesModel);

        @Select("select * from tbl_user where REPLACE(empId, '/', '') =#{empId}")
        UserModel EmpIdAlreadyExists(String empId);

        @Select("select * from tbl_user_copy_from_hr where REPLACE(empId, '/', '') =#{empId}")
        UserModel EmpIdHrAlreadyExists(String empId);

        @Select("select * from tbl_branch")
        List<BranchModel> getUnits();

        @Select("select p.id, p.title, l.name  as loc_name from tbl_job_position p, tbl_unit_location l where p.location is not null and p.location=l.id")
        List<JobPositionModel> getJobPosition();

        @Select("select * from tbl_user_copy_from_hr where empId=#{empId} and unit=#{branch.name} and position=#{jobPosition.title} and email=#{email}")
        UserCopyFromHR isUserExistOnHr(UserModel model);

        @Select("select * from tbl_region where name =#{deptLocation}")
        RegionModel getRegionId(String deptLocation);

        @Insert("insert into tbl_user(username, password,first_name,last_name,middle_name,email,isAccountNonExpired,phone_number,is_loggedin,created_by,is_locked,created_date,is_enabled,empId,branch,region,jobPosition,password_changed_date)"
                        + " values(#{username},#{password},#{first_name}, #{last_name}, #{middle_name}, #{email},1, #{phone_number},0, #{created_by},0,#{created_date},0, #{empId},#{branch.id},#{region.id},#{jobPosition.id},#{password_changed_date})")
        void signUpUser(UserModel model);

        @Select("select id from tbl_user where username=#{username}")
        Integer getUserId(String username);

        @Select("select role from tbl_job_position where id=#{id}")
        Integer getPositionRole(Long id);

        @Insert("insert into tbl_user_role(role_id, user_id, created_by, created_date) values(#{role_id}, #{user_id}, #{created_by}, #{created_date})")
        void signupUserRole(UserRoleMappingModel userrole);

        @Update("update tbl_user set created_by=#{user_id} where id=#{user_id}")
        void updateSignUpUser(Integer user_id);

        @Select("select * from tbl_user where username =#{username}")
        UserModel isUserExist(String username);

        @Select("select  * from tbl_branch b, tbl_user u where u.id=#{user_id} and u.branch=b.id ")
        BranchModel getBranchbyUserId(Long user_id);

        @Select("select region from tbl_branch where id=#{id}")
        Long getRegionByBranchId(Long id);

        @Select("select * from tbl_user where id !=#{id} and empId =#{unique}")
        UserModel empIdAlreadyExistsForUpdate(Long id, String unique);

        @Insert("insert into tbl_audit_trail(user_id,action,timestamp,details)\n" +
                        "values(#{user_id},#{action},#{timestamp},#{details})")
        void saveToAuditTrail(AuditTrailModel trail);

        @Select("select p.* from tbl_user_copy_from_hr u, tbl_job_position p where u.empId=#{empId} and u.position=p.title")
        JobPositionModel getJoBPositionByEmpId(String empId);

        @Select("select DATEDIFF(day, password_changed_date, GETDATE()) from tbl_user where id=#{user_id}")
        Long getNumberOfDaysLeft(Long user_id);

        @Update("update tbl_user set login_attempts=#{login_attempts} where id = #{id}")
        void updateLoginAttempts(UserModel user);

}
