package com.re3aya.re3aya.Repository;

import com.re3aya.re3aya.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    boolean existsByName(String name);
    Optional<Department> findByNameIgnoreCase(String name);

}
