package com.hw.demo.Repository;

import com.hw.demo.Models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByEmployeeEmployeeID(Integer employeeId);
}
