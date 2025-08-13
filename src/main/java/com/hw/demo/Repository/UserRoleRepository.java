package com.hw.demo.Repository;

import com.hw.demo.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByEmployeeEmployeeID(Integer employeeId);
}
