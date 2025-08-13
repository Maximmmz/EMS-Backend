package com.hw.demo.Repository;

import com.hw.demo.Models.EmployeeProject;
import com.hw.demo.Models.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {

    List<EmployeeProject> findByProject_ProjectID(Integer projectId);

    List<EmployeeProject> findByEmployee_EmployeeID(Integer employeeId);
}
