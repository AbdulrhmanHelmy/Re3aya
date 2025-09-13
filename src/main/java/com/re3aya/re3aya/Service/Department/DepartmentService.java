package com.re3aya.re3aya.Service.Department;

import com.re3aya.re3aya.DTO.DepartmentDTO;
import com.re3aya.re3aya.Model.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentById(Long id);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
    Department getDepartmentByName(String name);
}
