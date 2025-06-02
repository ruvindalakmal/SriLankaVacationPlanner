package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.controller.UserPrivilegeController;
import com.sampleproject.sampleproject.dto.requestdto.EmployeeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.EmployeeResponseDto;
import com.sampleproject.sampleproject.entity.Employee;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.EmployeeRepository;
import com.sampleproject.sampleproject.repo.EmployeeStatusRepository;
import com.sampleproject.sampleproject.repo.RoleRepository;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.service.service.EmployeeService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private UserPrivilegeController userPrivilegeController;

    @Override
    public List<EmployeeResponseDto> alldata() {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.DESC , "id"));
        if(!employees.isEmpty()){
            List<EmployeeResponseDto> employeeResponseDtos = objectMapper.employeeToEmployeeResponseDto(employees);
            return employeeResponseDtos;
        }else {
            throw new RuntimeException("No employee found");
        }
    }


    @Override
    public String saveEmployeeData(EmployeeRequestDto employeeRequestDto) throws AlreadyExistsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.getByUsername(auth.getName());

        Employee extEmployeeByNic = employeeRepository.getByNIC(employeeRequestDto.getNic());

        if(extEmployeeByNic != null){
            throw new AlreadyExistsException("Save Employee Failed : Given NIC already exists");
        }

        Employee extEmployeeByEmail = employeeRepository.getByEmail(employeeRequestDto.getEmail());
        if(extEmployeeByEmail != null){
            throw new AlreadyExistsException("Save Employee Failed : Given Email already exists");
        }

        Employee extEmployeeByMobileNumber = employeeRepository.getByMobileNumber(employeeRequestDto.getMobileNumber());
        if(extEmployeeByMobileNumber != null){
            throw new AlreadyExistsException("Save Employee Failed : Given MobileNumber already exists");
        }

        try {
            employeeRequestDto.setAdded_datetime(LocalDateTime.now());
            employeeRequestDto.setAdded_userid(loggedUser.getId());
            employeeRequestDto.setEmpNo(employeeRepository.getNextEmpNo());

            //Dto to Entity
            Employee employee = objectMapper.toEntity(employeeRequestDto);
            employeeRepository.save(employee);

            //Create a User Object
//            User user = new User();
//            user.setUsername(employeeRequestDto.getCallingName());
//            user.setEmail(employeeRequestDto.getEmail());
//            user.setStatus(true);
//            user.setAdded_datetime(LocalDateTime.now());
//            user.setPassword(bCryptPasswordEncoder.encode(employeeRequestDto.getNic()));
//            user.setEmployee(employee);

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findById(employeeRequestDto.getDesignation_id().getRole_id()).orElse(null);

            if(role != null){
                roles.add(role);
                System.out.println("Adding role " + role.getName());
            }else {
                System.out.println("Role not found");
            }

//            user.setRoles(roles);
//            userRepository.save(user);

            return "OK";
        }catch (Exception e){
            return "Save Employee Failed" + e.getMessage();
        }
    }

    @Override
    public EmployeeResponseDto deleteEmployee(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.getByUsername(auth.getName());

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with ID " + id + " not found"));

        employee.setDeleted_datetime(LocalDateTime.now());
        employee.setDeleted_userid(loggedUser.getId());
        employee.setEmployeeStatus_id(employeeStatusRepository.findById(3).orElse(null));
        employeeRepository.save(employee);
        //employeeRepository.delete(employee);
        return objectMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Integer id , EmployeeRequestDto employeeRequestDto) throws AlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.getByUsername(auth.getName());

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with ID " + id + " not found"));

        // Validate NIC
        Employee extEmployeeByNic = employeeRepository.getByNIC(employeeRequestDto.getNic());
        if(extEmployeeByNic != null && !extEmployeeByNic.getId().equals(employee.getId())){
            throw new AlreadyExistsException("Update Failed: Given NIC already exists");
        }

        // Validate Email
        Employee extEmployeeByEmail = employeeRepository.getByEmail(employeeRequestDto.getEmail());
        if(extEmployeeByEmail != null && !extEmployeeByEmail.getId().equals(employee.getId())){
            throw new AlreadyExistsException("Update Failed: Given Email already exists");
        }

        // Validate Mobile Number
        Employee extEmployeeByMobileNumber = employeeRepository.getByMobileNumber(employeeRequestDto.getMobileNumber());
        if(extEmployeeByMobileNumber != null && !extEmployeeByMobileNumber.getId().equals(employee.getId())){
            throw new AlreadyExistsException("Update Failed: Given Mobile Number already exists");
        }

        //update Entity
        objectMapper.updateEntityFromDto(employeeRequestDto , employee);
        employee.setModified_datetime(LocalDateTime.now());
        employee.setModified_userid(loggedUser.getId());

        //save changes
        employeeRepository.save(employee);
        return objectMapper.toDto(employee);

    }

    @Override
    public List<EmployeeResponseDto> listWithoutUserAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "User");

        if (userPrivilege.getPrivi_select()) {
            List<Employee> employees = employeeRepository.listWithoutUserAccount();
            return objectMapper.employeeToEmployeeResponseDto(employees);
        } else {
            return Collections.emptyList();
        }

    }

}
