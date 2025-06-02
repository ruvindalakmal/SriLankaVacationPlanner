package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.requestdto.PrivilegeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.PrivilegeResponseDto;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> getAllPrivileges();

    String savePrivilege(PrivilegeRequestDto privilegeRequestDto);

    PrivilegeResponseDto updatePrivilege(Integer id, PrivilegeRequestDto privilegeRequestDto) throws AlreadyExistsException;

    PrivilegeResponseDto deletePrivilege(Integer id) throws AlreadyExistsException;
}
