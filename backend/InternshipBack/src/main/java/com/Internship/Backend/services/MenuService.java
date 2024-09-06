package com.Internship.Backend.services;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internship.Backend.mappers.MenuMapper;
import com.Internship.Backend.models.MenuModel;
import com.Internship.Backend.models.ParentMenuModel;
import com.Internship.Backend.models.RolesModel;

@Service
public class MenuService {
   @Autowired
   MenuMapper mapper;
    public List<MenuModel> getAllMenus() {
        return mapper.getAllMenus();
    }
    public List<RolesModel> fetchRoles() {
        return mapper.fetchRoles();
    }
    public List<ParentMenuModel> getParentMenus() {
        return mapper.getParentMenus();
    }
    public void addMenu(MenuModel menu) {
        mapper.addMenu(menu);
    }
    public void updateMenu(MenuModel menu) {
        mapper.updateMenu(menu);
    }
    
}
