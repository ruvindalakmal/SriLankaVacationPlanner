package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.requestdto.UserRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.UserResponseDto;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.service.service.EmployeeService;
import com.sampleproject.sampleproject.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/user")
    public ModelAndView userUI(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView userView = new ModelAndView();
        userView.setViewName("User.html");
        userView.addObject("username", auth.getName());
        return userView;
    }

    @GetMapping(value = "/user/alldata")
    public List<UserResponseDto> getUserAllData(){
        return userService.getAllUsers();
    }

    @PostMapping("/user/insert")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestDto userRequestDto) throws AccessDeniedException {
        String result = userService.saveUser(userRequestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("user/delete/{id}")
    public UserResponseDto deleteUser(@PathVariable Integer id) throws AccessDeniedException {
        return userService.deleteUser(id);
    }

    @PutMapping("/user/update/{id}")
    public UserResponseDto updateUser(@PathVariable Integer id ,@RequestBody UserRequestDto userRequestDto) throws AccessDeniedException, AlreadyExistsException {
        return userService.updateUser(id,userRequestDto);
    }

}
