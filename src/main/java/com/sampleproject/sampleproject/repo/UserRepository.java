package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Fetch roles with the user to avoid lazy loading issues
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = ?1")
    User getByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles r " +
            "WHERE NOT EXISTS (SELECT 1 FROM u.roles r2 WHERE r2.name = 'Admin')")
    List<User> findAllUsersWithoutAdminRole(Sort id);


    User getByEmail(String email);
}
