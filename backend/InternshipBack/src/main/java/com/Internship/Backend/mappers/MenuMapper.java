package com.Internship.Backend.mappers;

import java.util.List;

import javax.management.relation.Role;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.Internship.Backend.models.MenuModel;
import com.Internship.Backend.models.ParentMenuModel;
import com.Internship.Backend.models.RolesModel;

public interface MenuMapper {

    @Select("select * from tbl_menu")
    List<MenuModel> getAllMenus();

    @Select("select * from tbl_role")
    List<RolesModel> fetchRoles();

    @Select("select * from tbl_parent_menu")
    List<ParentMenuModel> getParentMenus();

    @Insert("insert into tbl_menu(item_name, link, icon,parent_item_id, status,role_id) values(#{item_name}, #{link}, #{icon}, #{parent_item_id}, 1, #{role_id})")
    void addMenu(MenuModel menu);

    @Update("update tbl_menu set item_name = #{item_name}, link=#{link}, icon=#{icon}, parent_item_id=#{parent_item_id},status=#{status}, role_id=#{role_id} where id=#{id}")
    void updateMenu(MenuModel menu);

}
