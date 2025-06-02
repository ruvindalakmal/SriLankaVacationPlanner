package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.requestdto.PrivilegeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.PrivilegeResponseDto;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.PrivilegeRepository;
import com.sampleproject.sampleproject.service.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserPrivilegeController userPrivilegeController;

    @RequestMapping("/privilege")
    public ModelAndView privilege(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView privilege = new ModelAndView();
        privilege.setViewName("Privilege.html");
        privilege.addObject("username", auth.getName());
        return privilege;
    }

    @GetMapping("/privilege/alldata" )
    public List<Privilege> getAllPrivileges(){
        return privilegeService.getAllPrivileges();
    }

    @PostMapping(value = "/privilege/insert", produces = "application/json")
    public Map<String, String> savePrivilege(@RequestBody PrivilegeRequestDto privilegeRequestDto) throws AlreadyExistsException {
        String result = privilegeService.savePrivilege(privilegeRequestDto);
        return Collections.singletonMap("message", result);
    }

    @PutMapping(value = "/privilege/update/{id}" )
    public PrivilegeResponseDto updatePrivilege(@PathVariable Integer id , @RequestBody PrivilegeRequestDto privilegeRequestDto) throws AlreadyExistsException {
        return privilegeService.updatePrivilege(id , privilegeRequestDto);

    }

    @DeleteMapping( "/privilege/delete/{id}")
    public PrivilegeResponseDto deletePrivilege(@PathVariable Integer id) throws AlreadyExistsException {
        return privilegeService.deletePrivilege(id);
    }
}



