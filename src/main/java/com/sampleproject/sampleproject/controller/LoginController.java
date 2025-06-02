package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.repo.RoleRepository;
import com.sampleproject.sampleproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView loginUI = new ModelAndView();
        loginUI.setViewName("Login.html");
        return loginUI;
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView dashboard = new ModelAndView();
        dashboard.setViewName("Dashboard.html");
        dashboard.addObject("username", auth.getName());
        return dashboard;
    }

    @RequestMapping("/errorPage")
    public ModelAndView errorPage(){
        ModelAndView errorPageUI = new ModelAndView();
        errorPageUI.setViewName("ErrorPage.html");
        return errorPageUI;
    }

    @RequestMapping("/create-admin")
    public ModelAndView createAdmin() {
        User extAdminUser = userRepository.getByUsername("Admin");
        if(extAdminUser == null){
            User adminUser = new User();
            adminUser.setUsername("Admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setStatus(true);
            adminUser.setAdded_datetime(LocalDateTime.now());
            adminUser.setPassword(bCryptPasswordEncoder.encode("12345"));

            Set<Role> roles = new HashSet<>();
//            Role adminRole = roleRepository.getReferenceById(1);
            Role adminRole = roleRepository.findById(1).orElse(null);

            if (adminRole != null) {
                roles.add(adminRole);
                System.out.println("Adding role: " + adminRole.getName());
            } else {
                System.out.println("Role not found in database!");
            }

//            roles.add(adminRole);
            adminUser.setRoles(roles);

            adminUser = userRepository.save(adminUser);

            for (Role role : roles) {
                System.out.println("Persisting role: " + role.getName() + " for user " + adminUser.getUsername());
            }
            } else {
            System.out.println("User already exists!");
            }

            ModelAndView loginUI = new ModelAndView();
            loginUI.setViewName("Login.html");
            return loginUI;
    }
}
