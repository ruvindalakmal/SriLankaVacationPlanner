package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.repo.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserPrivilegeController {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    //define function for get privilege by given username and modulename
    public Privilege getPrivilegeByUserModule(String username, String modulename) {
        Privilege userPrivilege = new Privilege();

        if (username.equals("Admin")) {

            userPrivilege.setPrivi_select(true);
            userPrivilege.setPrivi_insert(true);
            userPrivilege.setPrivi_update(true);
            userPrivilege.setPrivi_delete(true);
        }else {

            String userPrivilegeString = privilegeRepository.getPrivilegeByUserModule(username, modulename);
            String[] userPrivilegeStringArray = userPrivilegeString.split(",");
            System.out.println(userPrivilegeString);

            userPrivilege.setPrivi_select(userPrivilegeStringArray[0].equals("1"));
            userPrivilege.setPrivi_insert(userPrivilegeStringArray[1].equals("1"));
            userPrivilege.setPrivi_update(userPrivilegeStringArray[2].equals("1"));
            userPrivilege.setPrivi_delete(userPrivilegeStringArray[3].equals("1"));
        }

        return userPrivilege;
    }
}
