package com.hw.demo.Controllers;

import com.hw.demo.Models.*;
import com.hw.demo.Repository.*;
import com.hw.demo.config.CustomUserDetails;
import com.hw.demo.dto.CreateEmployeeWithCredsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.hw.demo.dto.UpdateEmployeeWithCredsRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees") // Base URL
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCredsRepository employeeCredsRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public EmployeeController(EmployeeRepository employeeRepository,
                              EmployeeCredsRepository employeeCredsRepository,
                              DepartmentRepository departmentRepository,
                              DesignationRepository designationRepository,
                              PasswordEncoder passwordEncoder,
                              UserRoleRepository userRoleRepository,
                              RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeCredsRepository = employeeCredsRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;

    }

    //CREATE new employee with Creds
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @PostMapping("/with-creds")
    @Transactional
    public ResponseEntity<Employee> createEmployeeWithCreds(@RequestBody CreateEmployeeWithCredsRequest request) {
        // 1. Create Employee
        Employee emp = new Employee();
        emp.setFirstName(request.getFirstName());
        emp.setLastName(request.getLastName());
        emp.setEmail(request.getEmail());
        emp.setPhone(request.getPhone());
        emp.setDob(request.getDob());
        emp.setGender(request.getGender());
        emp.setAddress(request.getAddress());
        emp.setPhoto(request.getPhoto());
        emp.setHireDate(request.getHireDate());
        emp.setStatus(request.getStatus());

        emp.setDepartment(departmentRepository.findById(request.getDepartmentId()).orElseThrow());
        emp.setDesignation(designationRepository.findById(request.getDesignationId()).orElseThrow());

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId()).orElseThrow();
            emp.setManager(manager);
        }

        emp = employeeRepository.save(emp);

        // 2. Create EmployeeCreds
        EmployeeCreds creds = new EmployeeCreds();
        creds.setEmployee(emp);
        creds.setUsername(request.getUsername());
        creds.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        creds.setMedia("default.png");
        employeeCredsRepository.save(creds);

        // 3. Create UserRole
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow();
        UserRole userRole = new UserRole();
        userRole.setEmployee(emp);
        userRole.setRole(role);
        userRole.setAssignedDate(LocalDate.now());
        userRoleRepository.save(userRole);

        return ResponseEntity.ok(emp);
    }


    //CREATE new employee
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @PostMapping("/")
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    // GET all employees
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @GetMapping("/get_all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findByStatus("Active");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok("You are employee " + userDetails.getEmployeeId() + " with Role: " + userDetails.getAuthorities());
    }

    // GET single employee by ID
    @PreAuthorize("hasAnyRole('HR_ADMIN','EXECUTIVE') or principal.employeeId == #id")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    //Get manager's subordinates.
    @PreAuthorize("hasAnyRole('MANAGER', 'EXECUTIVE')")
    @GetMapping("/managers/{id}")
    public List<Employee> getSubordinates(@PathVariable("id") Integer managerId) {
        return employeeRepository.findByManagerEmployeeID(managerId);
    }

    //Get projects of employee
    @PreAuthorize("hasAnyRole('MANAGER', 'EXECUTIVE')")
    @GetMapping("/projects/{id}")
    public List<Project> getProjectsOfEmployee(@PathVariable("id") Integer employeeId) {
        return employeeRepository.getProjectsByEmployeeId(employeeId);
    }

    @GetMapping("/test-role")
    public String testRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("== Current User: " + auth.getName());
        System.out.println("== Granted Authorities:");
        auth.getAuthorities().forEach(a -> System.out.println("  -> " + a.getAuthority()));
        return "Check logs!";
    }

    //Get employees by department
    @PreAuthorize("hasAnyRole('HR_ADMIN','EXECUTIVE')")
    @GetMapping("/department/{deptId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Integer deptId) {
        return employeeRepository.findByDepartment(deptId);
    }

    //Get employees by designation
    @PreAuthorize("hasAnyRole('HR_ADMIN','EXECUTIVE')")
    @GetMapping("/designation/{desigId}")
    public List<Employee> getEmployeesByDesignation(@PathVariable Integer desigId) {
        return employeeRepository.findByDesignation(desigId);
    }

    //PUT
    @PatchMapping("/update")
    @Transactional
    public String updateEmployeeAndCreds(@RequestBody UpdateEmployeeWithCredsRequest request) {
        //  Find employee
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        //  Update employee fields
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getEmail() != null) employee.setEmail(request.getEmail());
        if (request.getPhone() != null) employee.setPhone(request.getPhone());
        if (request.getDob() != null) employee.setDob(request.getDob());
        if(request.getGender() != null) employee.setGender(request.getGender());
        if(request.getAddress() != null) employee.setAddress(request.getAddress());
        if(request.getHireDate() != null) employee.setHireDate(request.getHireDate());
        if(request.getStatus() != null) employee.setStatus(request.getStatus());
        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(dept);
        }

//  Update Designation if provided
        if (request.getDesignationId() != null) {
            Designation desig = designationRepository.findById(request.getDesignationId())
                    .orElseThrow(() -> new RuntimeException("Designation not found"));
            employee.setDesignation(desig);
        }
        employeeRepository.save(employee);

        //  Find creds by employee
        Optional<EmployeeCreds> credsOpt = employeeCredsRepository.findById(request.getEmployeeId());
        if (credsOpt.isPresent()) {
            EmployeeCreds creds = credsOpt.get();
            if (request.getUsername() != null) creds.setUsername(request.getUsername());
            if (request.getPasswordHash() != null) creds.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
            if (request.getMedia() != null) creds.setMedia(request.getMedia());
            employeeCredsRepository.save(creds);
        } else {
            // If no creds found, optionally create new
            EmployeeCreds creds = new EmployeeCreds();
            creds.setEmployee(employee);
            creds.setUsername(request.getUsername());
            creds.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
            creds.setMedia(request.getMedia());
            employeeCredsRepository.save(creds);
        }

        return "Employee and credentials updated successfully!";
    }

    @PreAuthorize("hasRole('HR_ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public String deleteEmployeeAndCreds(@PathVariable Integer id) {
        //  Check if employee exists
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));

        //  Find and delete associated credentials
        Optional<EmployeeCreds> credsOpt = employeeCredsRepository.findByEmployee(employee);
        credsOpt.ifPresent(employeeCredsRepository::delete);

        //  Delete the employee itself
        employeeRepository.delete(employee);

        return "Employee and credentials deleted successfully!";
    }

}
