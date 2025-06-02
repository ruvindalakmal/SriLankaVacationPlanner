package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.requestdto.UserRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.UserResponseDto;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto deleteUser(Integer id) throws AccessDeniedException;

    String saveUser(UserRequestDto userRequestDto) throws AccessDeniedException;

    UserResponseDto updateUser(Integer id, UserRequestDto userRequestDto) throws AlreadyExistsException;
}
