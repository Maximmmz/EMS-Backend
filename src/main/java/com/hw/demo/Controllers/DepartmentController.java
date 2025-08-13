package com.hw.demo.Controllers;

import com.hw.demo.Models.Department;
import com.hw.demo.Models.Employee;
import com.hw.demo.Repository.DepartmentRepository;
import com.hw.demo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments") // Base URL
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // CREATE new department
    @PostMapping
    public Department createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department dept = new Department();
        dept.setDepartmentName(departmentDTO.getDepartmentName());


        return departmentRepository.save(dept);
    }

    // GET all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // GET single department by ID
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    // Nested DTO class inside controller for simplicity
    public static class DepartmentDTO {
        private String departmentName;

        public String getDepartmentName() {
            return departmentName;
        }
        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }
}
