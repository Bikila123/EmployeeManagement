package com.Internship.Backend.models;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String email;
    private String password;
    private int created_by;
    private Date created_date;
    private String is_loggedin;
    private int is_locked;
    private int is_enabled;
    private int isAccountNonExpired;
    private String phone_number;
    private int modified_by;
    private Date modified_date;
    private RegionModel region;
    private BranchModel branch;
    private JobPositionModel jobPosition;
    private String empId;
    private String otp;
    private String placeofwork;
    private String media;
    private Date password_changed_date;
    private int login_attempts;
    private Long branch_id;
    private Long position_id;
    private Long region_id;
    private int role_id;
   private Set<RolesModel> roles = new HashSet<>();
    public UserModel(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
    
	public UserModel() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCreated_by() {
		return created_by;
	}
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getIs_loggedin() {
		return is_loggedin;
	}
	public void setIs_loggedin(String is_loggedin) {
		this.is_loggedin = is_loggedin;
	}
	public int getIs_locked() {
		return is_locked;
	}
	public void setIs_locked(int is_locked) {
		this.is_locked = is_locked;
	}
	public int getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(int is_enabled) {
		this.is_enabled = is_enabled;
	}
	public int getIsAccountNonExpired() {
		return isAccountNonExpired;
	}
	public void setIsAccountNonExpired(int isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Date getModified_date() {
		return modified_date;
	}
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	public RegionModel getRegion() {
		return region;
	}
	public void setRegion(RegionModel region) {
		this.region = region;
	}
	public BranchModel getBranch() {
		return branch;
	}
	public void setBranch(BranchModel branch) {
		this.branch = branch;
	}
	public JobPositionModel getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(JobPositionModel jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getPlaceofwork() {
		return placeofwork;
	}
	public void setPlaceofwork(String placeofwork) {
		this.placeofwork = placeofwork;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public Date getPassword_changed_date() {
		return password_changed_date;
	}
	public void setPassword_changed_date(Date password_changed_date) {
		this.password_changed_date = password_changed_date;
	}
	public int getLogin_attempts() {
		return login_attempts;
	}
	public void setLogin_attempts(int login_attempts) {
		this.login_attempts = login_attempts;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Long getPosition_id() {
		return position_id;
	}
	public void setPosition_id(Long position_id) {
		this.position_id = position_id;
	}
	public Long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public Set<RolesModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RolesModel> roles) {
		this.roles = roles;
	}
    
}
