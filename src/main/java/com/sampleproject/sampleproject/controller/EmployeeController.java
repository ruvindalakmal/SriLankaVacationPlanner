package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.requestdto.EmployeeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.EmployeeResponseDto;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.EmployeeRepository;
import com.sampleproject.sampleproject.repo.EmployeeStatusRepository;
import com.sampleproject.sampleproject.entity.Employee;
import com.sampleproject.sampleproject.repo.RoleRepository;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("employee")
    public ModelAndView employee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView employeeUI = new ModelAndView();
        employeeUI.setViewName("Employee.html");
        employeeUI.addObject("username", auth.getName());
        return employeeUI;
    }

    @GetMapping(value = "/employee/alldata" , produces = "application/json")
    public List<EmployeeResponseDto> alldata() {
            return employeeService.alldata();
    }

    @GetMapping(value = "/employee/listwithoutuseraccount")
    public List<EmployeeResponseDto> listWithoutUserAccount(){return employeeService.listWithoutUserAccount();}

    @PostMapping(value = "/employee/insert")
    public String saveEmployeeData(@RequestBody EmployeeRequestDto employeeRequestDto) throws AlreadyExistsException {
        return employeeService.saveEmployeeData(employeeRequestDto);
    }

    @DeleteMapping( "/employee/delete/{id}")
    public EmployeeResponseDto deleteEmployee(@PathVariable Integer id) {
         return employeeService.deleteEmployee(id);
    }

    @PutMapping("employee/update/{id}")
    public EmployeeResponseDto updateEmployeeData(@PathVariable Integer id ,@RequestBody EmployeeRequestDto employeeRequestDto ) throws AlreadyExistsException {
        return employeeService.updateEmployee(id , employeeRequestDto);
    }

}
