package com.Internship.Backend.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.Internship.Backend.models.BranchModel;
import com.Internship.Backend.models.EmployeeModel;
import com.Internship.Backend.models.JobPositionModel;
import com.Internship.Backend.models.DepartmentModel;

public interface EmployeeMapper {

	@Insert("insert into tbl_employee(empid, first_name, last_name, unit, position, salary, email, phone, gender, department) values(#{empid}, #{first_name}, #{last_name}, #{unit}, #{position}, #{salary}, #{email}, #{phone}, #{gender}, #{department})")
	void addEmployee(EmployeeModel data);

	@Select("select * from tbl_employee")
	List<EmployeeModel> getEmployees();
	
	@Select("select id, name from tbl_branch")
	List<BranchModel> getBranch();
	
	@Select("select id, title from tbl_job_position")
	List<JobPositionModel> getPosition();
	
	@Select("select id, department_name from tbl_department")
	List<DepartmentModel> getDepartment();
	
	@Delete("delete from tbl_employee where empid = #{empid}")
	void deleteEmployeeById(String empid);

	@Update("update tbl_employee set  first_name = #{first_name}, last_name = #{last_name}, unit = #{unit}, position =#{position}, salary = #{salary}, email = #{email}, phone = #{phone}, gender=#{gender}, department = #{department} where empid=#{empid} ")
	void updateEmployee(EmployeeModel data);

}
