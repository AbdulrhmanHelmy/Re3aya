package com.re3aya.re3aya.Service.Department;

import com.re3aya.re3aya.DTO.DepartmentDTO;
import com.re3aya.re3aya.Exeption.ResourceNotFound;
import com.re3aya.re3aya.Model.Department;
import com.re3aya.re3aya.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        departmentRepository.findByNameIgnoreCase(departmentDTO.getName())
                .ifPresent(d -> {
                    throw new RuntimeException("Department with this name already exists");
                });

        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());

        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (!department.getName().equalsIgnoreCase(departmentDTO.getName())) {
            departmentRepository.findByNameIgnoreCase(departmentDTO.getName())
                    .ifPresent(d -> {
                        throw new RuntimeException("Department with this name already exists");
                    });
        }

        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());

        Department updated = departmentRepository.save(department);
        return mapToDTO(updated);
    }


    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(department);
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name).orElseThrow(
                ()-> new ResourceNotFound("Department not found with name: " + name)
        );
    }

    private DepartmentDTO mapToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setId(department.getId());
        return dto;
    }
//    @Override
//    public DepartmentDTO getDepartmentByName(String name) {
//        Department department = departmentRepository.findByNameIgnoreCase(name)
//                .orElseThrow(() -> new RuntimeException("Department not found with name: " + name));
//        return mapToDTO(department);
//    }

}
