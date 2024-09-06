package com.Internship.Backend.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.Internship.Backend.models.ERole;
import com.Internship.Backend.models.RolesModel;
import org.apache.ibatis.annotations.Update;

public interface RoleMapper {
  @Select("select * from tbl_role where description=#{name}")
  Optional<RolesModel> findByName(ERole name);

  @Select("SELECT * FROM tbl_role")
  List<RolesModel> fetchRoles();

  @Update("update tbl_role set description=#{description},status=#{status} where id = #{id}")
  void updateRole(RolesModel criteriaModel);

  @Insert("insert into tbl_role(description,status) values(#{description},1)")
  void addRole(RolesModel model);
}