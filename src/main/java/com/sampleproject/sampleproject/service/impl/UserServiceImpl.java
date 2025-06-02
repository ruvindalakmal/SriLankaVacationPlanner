package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.controller.UserPrivilegeController;
import com.sampleproject.sampleproject.dto.requestdto.UserRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.UserResponseDto;
import com.sampleproject.sampleproject.entity.Employee;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.EmployeeRepository;
import com.sampleproject.sampleproject.repo.RoleRepository;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.service.service.UserService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPrivilegeController userPrivilegeController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        //Check User Authentication and Authorization
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "User");

        if (userPrivilege.getPrivi_select()) {
            List<User> users = userRepository.findAllUsersWithoutAdminRole(Sort.by(Sort.Direction.DESC, "id"));
            return objectMapper.userToUserResponseDto(users);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserResponseDto deleteUser(Integer id) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "User");

        if (!userPrivilege.getPrivi_delete()) {
            throw new AccessDeniedException("You do not have permission to delete this user");
        }
        User loggedUser = userRepository.getByUsername(auth.getName());

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with ID :" + id +  "not found"));
        
        user.setStatus(false);
        user.setDeleted_datetime(LocalDateTime.now());
        user.setDeleted_userid(loggedUser.getId());
        userRepository.save(user);
        return objectMapper.userToUserResponseDto(user);


    }

    @Override
    @Transactional
    public String saveUser(UserRequestDto userRequestDto) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "User");

        if (!userPrivilege.getPrivi_insert()) {
            throw new AccessDeniedException("You do not have permission to insert this user");
        }
        try {
            // Map DTO to User entity
            User user = objectMapper.toEntity(userRequestDto);

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setAdded_datetime(LocalDateTime.now());

            Employee employee = employeeRepository.findById(userRequestDto.getEmployeeResponseDto().getId()).orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));
            Set<Role> managedRoles = user.getRoles().stream().map(role -> roleRepository.findById(role.getId()).orElseThrow(() -> new RuntimeException("Role not found"))).collect(Collectors.toSet());

            user.setRoles(managedRoles);
            user.setEmployee(employee);
            // Save user
            userRepository.save(user);
            return "OK";
        } catch (EntityNotFoundException e) {
            // Handle missing employee
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            // Handle other errors
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save user", e);
        }
    }

    @Override
    public UserResponseDto updateUser(Integer id, UserRequestDto userRequestDto) throws AlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "User");


        if (userPrivilege.getPrivi_update()) {
            User loggedUser = userRepository.getByUsername(auth.getName());
            User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with ID :" + id + "not found"));

            User extUserByEmail = userRepository.getByEmail(userRequestDto.getEmail());
            if (extUserByEmail != null && extUserByEmail.getId().equals(user.getId())) {
                throw new AlreadyExistsException("Update Failed: Given Email already exists");
            }

            objectMapper.toEntity(userRequestDto , user);
            user.setModified_datetime(LocalDateTime.now());
            user.setModified_userid(loggedUser.getId());

            userRepository.save(user);
            return objectMapper.userToUserResponseDto(user);
        }
        throw new AccessDeniedException("You do not have permission to update this user");
    }

}
