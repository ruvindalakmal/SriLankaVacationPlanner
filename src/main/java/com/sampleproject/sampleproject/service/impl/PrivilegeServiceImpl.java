package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.controller.UserPrivilegeController;
import com.sampleproject.sampleproject.dto.requestdto.PrivilegeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.PrivilegeResponseDto;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.repo.PrivilegeRepository;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.service.service.PrivilegeService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserPrivilegeController userPrivilegeController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Privilege> getAllPrivileges() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return new ArrayList<>();
        }
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName() , "Privilege");

        if (userPrivilege != null && Boolean.TRUE.equals(userPrivilege.getPrivi_select())) {
            return privilegeRepository.findAll(Sort.by(Sort.Direction.DESC , "id")).stream().map(privilege -> objectMapper.convertValue(privilege , PrivilegeResponseDto.class)).collect(Collectors.toList()).reversed();
        }
        return new ArrayList<>();
    }

    @Override
    public String savePrivilege(PrivilegeRequestDto privilegeRequestDto) {
        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "Save Not Completed: User is not authenticated";
        }
        User loggedUser = userRepository.getByUsername(auth.getName());

        // Check if the user has permission to insert privileges
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Privilege");

        if (userPrivilege == null || !Boolean.TRUE.equals(userPrivilege.getPrivi_insert())) {
            return "Save Not Completed: You are not allowed to add privilege";
        }

        // Check if the privilege already exists
        Privilege extPrivilege = privilegeRepository.getPrivilegeByRoleModule(
                privilegeRequestDto.getRole_id().getId(),
                privilegeRequestDto.getModule_id().getId()
        );

        if (extPrivilege != null) {
            return "Privilege already exists";
        }
        try {

            Privilege newPrivilege = objectMapper.convertValue(privilegeRequestDto, Privilege.class);

            // Set audit fields
            newPrivilege.setAdded_datetime(LocalDateTime.now());
            newPrivilege.setAdded_userid(loggedUser.getId()); // Dynamic user ID

            // Save privilege to DB
            privilegeRepository.save(newPrivilege);

            return "OK";
        } catch (Exception e) {
            // Log error for debugging
            e.printStackTrace();
            return "Save not Completed: Internal Server Error";
        }
    }

    @Override
    @Transactional
    public PrivilegeResponseDto updatePrivilege(Integer id, PrivilegeRequestDto privilegeRequestDto) throws AlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.getByUsername(auth.getName());
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Privilege");

        // Check if user has update permission
        if (userPrivilege == null || !userPrivilege.getPrivi_update()) {
            throw new AlreadyExistsException("Update not allowed: Insufficient privileges");
        }
        // Fetch the privilege to update
        Privilege existingPrivilege = privilegeRepository.findById(id).orElseThrow(() -> new AlreadyExistsException("Privilege not found with id: " + id));

        // Check for existing privileges with the same role and module
        Privilege extPrivilege = privilegeRepository.getPrivilegeByRoleModule(privilegeRequestDto.getRole_id().getId(), privilegeRequestDto.getModule_id().getId());

        if (extPrivilege != null && !extPrivilege.getId().equals(existingPrivilege.getId())) { throw new AlreadyExistsException("Privilege already exists for this role and module");}

        // Update existing privilege with DTO data
        objectMapper.updatePrivilegeFromDto(privilegeRequestDto, existingPrivilege);

        // Set modification details
        existingPrivilege.setModified_datetime(LocalDateTime.now());
        existingPrivilege.setModified_userid(loggedUser.getId());

        try {
            privilegeRepository.save(existingPrivilege);
        } catch (Exception e) {
            throw new AlreadyExistsException("Update failed: " + e.getMessage());
        }
        // Convert to response DTO
        return objectMapper.toDto(existingPrivilege);
    }

    @Override
    public PrivilegeResponseDto deletePrivilege(Integer id) throws AlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.getByUsername(auth.getName());
        Privilege extPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Privilege");

        Privilege existingPrivilege = privilegeRepository.findById(id).orElseThrow(() -> new AlreadyExistsException("Privilege not found with id: " + id));


        if(extPrivilege.getPrivi_delete()){
            try {
                existingPrivilege.setPrivi_select(false);
                existingPrivilege.setPrivi_insert(false);
                existingPrivilege.setPrivi_update(false);
                existingPrivilege.setPrivi_delete(false);

                existingPrivilege.setDeleted_datetime(LocalDateTime.now());
                existingPrivilege.setDeleted_userid(loggedUser.getId());

                privilegeRepository.save(existingPrivilege);

                PrivilegeResponseDto responseDto = objectMapper.toDto(existingPrivilege);
                responseDto.setMessage("OK");
                return responseDto;
            }catch (Exception e){
                throw new AlreadyExistsException("Delete Not Completed: " + e.getMessage());
            }
        }else {
            throw new AlreadyExistsException("Delete Not Completed");
        }

    }


}
