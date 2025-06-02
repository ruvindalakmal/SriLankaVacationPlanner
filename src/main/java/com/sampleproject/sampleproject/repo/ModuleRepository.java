package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
}
