package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.RoleResponseDto;
import com.sampleproject.sampleproject.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleResponseDto> getAllRoles();
}
