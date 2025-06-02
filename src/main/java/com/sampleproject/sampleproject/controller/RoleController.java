package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.responsedto.RoleResponseDto;
import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/role/alldata" , produces = "application/json")
    public List<RoleResponseDto> getAllRoles(){
        return roleService.getAllRoles();
    }
}
