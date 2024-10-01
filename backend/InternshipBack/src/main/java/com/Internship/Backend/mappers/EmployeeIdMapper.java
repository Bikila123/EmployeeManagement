package com.Internship.Backend.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.Internship.Backend.models.EmployeeIdTracker;
import com.Internship.Backend.models.EmployeeModel;

public interface EmployeeIdMapper {
	
	@Select("select max(last_employee_number) from employee_id_tracker where emp_year = #{year}")
	Integer getLastNum(Integer year);
	
	@Insert("insert into employee_id_tracker(emp_year, last_employee_number) values(#{YEAR}, #{last_employee_number})")
	void addEmployeeId(int YEAR, int last_employee_number);
}
