package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.RoleResponseDto;
import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.repo.RoleRepository;
import com.sampleproject.sampleproject.service.service.RoleService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<Role> roles = roleRepository.findByNameNot("Admin");

        return roles.stream().map(objectMapper::roleToRoleResponseDto).toList();
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
