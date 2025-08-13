package com.hw.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.hw.demo.Models.EmployeeSalary;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
    List<EmployeeSalary> findByEmployee_EmployeeID(Integer employeeId);
    // You can add custom queries later if needed
}

