package com.hw.demo.Repository;

import com.hw.demo.Models.Employee;
import com.hw.demo.Models.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {
    List<LeaveBalance> findByEmployeeEmployeeID(Integer employeeId);
    List<LeaveBalance> findByEmployeeEmployeeIDAndYear(Integer employeeId, Integer year);

    Optional<LeaveBalance> findByEmployeeAndLeaveTypeAndYear(Employee employee, String leaveType, int year);
}
