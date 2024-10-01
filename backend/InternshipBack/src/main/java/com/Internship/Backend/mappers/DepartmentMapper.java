package com.Internship.Backend.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.Internship.Backend.models.DepartmentModel;

public interface DepartmentMapper {
	
	@Select("select * from tbl_department")
	List<DepartmentModel> getDepartment();
	
	@Insert("insert into tbl_department (department_name) values(#{dept_name}")
	void addDepartment(DepartmentModel data);
	

}
