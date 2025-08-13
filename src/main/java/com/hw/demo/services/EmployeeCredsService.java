package com.hw.demo.services;

import com.hw.demo.Models.Employee;
import com.hw.demo.Models.EmployeeCreds;
import com.hw.demo.Repository.EmployeeCredsRepository;
import com.hw.demo.Repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCredsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCredsRepository employeeCredsRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeCredsService(EmployeeRepository employeeRepository,
                                EmployeeCredsRepository employeeCredsRepository,
                                PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeCredsRepository = employeeCredsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeCreds createCreds(Integer employeeId, String username, String rawPassword) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        // Create and populate creds
        EmployeeCreds creds = new EmployeeCreds();
        creds.setEmployee(employee);
        creds.setUsername(username);
        creds.setPasswordHash(passwordEncoder.encode(rawPassword)); // ✅ Bcrypt encoded

        // Save to DB
        return employeeCredsRepository.save(creds);
    }
}
