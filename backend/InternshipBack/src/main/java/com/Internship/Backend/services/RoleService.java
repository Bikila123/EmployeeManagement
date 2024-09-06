package com.Internship.Backend.services;

import com.Internship.Backend.mappers.RoleMapper;
import com.Internship.Backend.models.RolesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper mapper;

    public List<RolesModel> fetchRoles() {
        return mapper.fetchRoles();
    }

    public void updateRole(RolesModel model) {
        mapper.updateRole(model);
    }

    public void addRole(RolesModel model) {
         mapper.addRole(model);
    }
}
