package com.Internship.Backend.services;

import com.Internship.Backend.mappers.ParentMenuMapper;
import com.Internship.Backend.models.ParentMenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentMenuService {

    @Autowired
    private ParentMenuMapper mapper;
    public void addParentMenu(ParentMenuModel model) {
        mapper.addParentMenu(model);
    }

    public List<ParentMenuModel> fetchparentmenus() {
        return mapper.fetchparentmenus();
    }

    public void updateParentMenu(ParentMenuModel model) {
        mapper.updateParentMenu(model);
    }
}
