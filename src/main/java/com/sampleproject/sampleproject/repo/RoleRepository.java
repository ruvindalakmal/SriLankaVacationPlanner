package com.sampleproject.sampleproject.repo;


import com.sampleproject.sampleproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByNameNot(String name);

}
