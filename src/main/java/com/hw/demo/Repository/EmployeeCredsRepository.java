package com.hw.demo.Repository;

import com.hw.demo.Models.EmployeeCreds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hw.demo.Models.Employee;

import java.util.Optional;

@Repository
public interface EmployeeCredsRepository extends JpaRepository<EmployeeCreds, Integer> {
    Optional<EmployeeCreds> findByUsername(String username);
    Optional<EmployeeCreds> findByEmployee(Employee employeeID);
}