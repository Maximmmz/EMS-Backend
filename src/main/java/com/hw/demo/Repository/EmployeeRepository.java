package com.hw.demo.Repository;

import com.hw.demo.Models.Employee;
import com.hw.demo.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    //Ignore status
    List<Employee> findByStatus(String status);

    @Query(value = "SELECT * FROM Employee WHERE departmentID = :deptId", nativeQuery = true)
    List<Employee> findByDepartment(@Param("deptId") Integer deptId);

    @Query(value = "SELECT * FROM Employee WHERE designationID = :desigId", nativeQuery = true)
    List<Employee> findByDesignation(@Param("desigId") Integer desigId);

    List<Employee> findByManagerEmployeeID(Integer managerId);

    @Query("SELECT ep.project FROM EmployeeProject ep WHERE ep.employee.employeeID = :employeeId")
    List<Project> getProjectsByEmployeeId(@Param("employeeId") Integer employeeId);

}
