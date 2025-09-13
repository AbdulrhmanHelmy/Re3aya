package com.re3aya.re3aya.Controller;

import com.re3aya.re3aya.DTO.DepartmentDTO;
import com.re3aya.re3aya.Response.API_Response;
import com.re3aya.re3aya.Service.Department.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<API_Response> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO savedDepartment = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(new API_Response("Department created successfully", savedDepartment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(new API_Response("Department found", department));
    }

    @GetMapping
    public ResponseEntity<API_Response> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(new API_Response("Departments list", departments));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<API_Response> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return ResponseEntity.ok(new API_Response("Department updated successfully", updatedDepartment));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<API_Response> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(new API_Response("Department deleted successfully", null));
    }
}
