package com.sampleproject.sampleproject.services;

import com.sampleproject.sampleproject.entity.Role;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserServiceDetail implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to fetch user: " + username);
        User extUser = userRepository.getByUsername(username);

        if (extUser == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        System.out.println("User found: " + extUser.getUsername() + " | Password: " + extUser.getPassword());
        System.out.println("Roles assigned: " + extUser.getRoles());

        Set<GrantedAuthority> authority = new HashSet<>();
        for (Role userRole : extUser.getRoles()){
            System.out.println("Adding role: " + userRole.getName());
            authority.add(new SimpleGrantedAuthority(userRole.getName()));
        }

        return new org.springframework.security.core.userdetails.User(extUser.getUsername() , extUser.getPassword() ,extUser.getStatus() , true , true , true , authority);
    }
}
