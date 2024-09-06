package com.Internship.Backend.mappers;

import com.Internship.Backend.models.ParentMenuModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ParentMenuMapper {

    @Select("SELECT * FROM tbl_parent_menu")
    List<ParentMenuModel> fetchparentmenus();

    @Insert("insert into tbl_parent_menu(item_name,role_id,parent_icon) values(#{item_name},#{role_id},#{parent_icon})")
    void addParentMenu(ParentMenuModel model);

    @Update("update tbl_parent_menu set item_name=#{item_name},role_id=#{role_id},parent_icon=#{parent_icon} where id = #{id}")
    void updateParentMenu(ParentMenuModel model);
}
