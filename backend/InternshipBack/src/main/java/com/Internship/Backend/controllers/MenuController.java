package com.Internship.Backend.controllers;

import java.util.List;

import javax.management.relation.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Internship.Backend.models.MenuModel;
import com.Internship.Backend.models.ParentMenuModel;
import com.Internship.Backend.models.RolesModel;
import com.Internship.Backend.services.MenuService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    MenuService service;

    @GetMapping("/getmenus")
    public ResponseEntity<List<MenuModel>> getAllMenus() {
        List<MenuModel> response = service.getAllMenus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getroles")
    public ResponseEntity<List<RolesModel>> fetchRoles() {
        try {
            List<RolesModel> roles = service.fetchRoles();

            return new ResponseEntity<>(roles, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getparentmenus")
    public ResponseEntity<List<ParentMenuModel>> getParentMenus() {
        try {
            List<ParentMenuModel> parentmenus = service.getParentMenus();
            return new ResponseEntity<>(parentmenus, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addMenu")
    public ResponseEntity<MenuModel> addMenu(@RequestBody MenuModel menu){
        try {
            service.addMenu(menu);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/updateMenu")
    public ResponseEntity<MenuModel> updateMenu(@RequestBody MenuModel menu){
        try {
            service.updateMenu(menu);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error Occured", e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

}
