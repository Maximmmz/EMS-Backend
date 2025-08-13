package com.hw.demo.Controllers;

import com.hw.demo.Models.Employee;
import com.hw.demo.Models.LeaveBalance;
import com.hw.demo.Models.LeaveRequest;
import com.hw.demo.Repository.EmployeeRepository;
import com.hw.demo.Repository.LeaveBalanceRepository;
import com.hw.demo.Repository.LeaveRequestRepository;
import com.hw.demo.config.CustomUserDetails;
import com.hw.demo.dto.LeaveApprovalRequestDTO;
import com.hw.demo.dto.LeaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveRequestController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private EmployeeRepository employeeRepository;

    @PostMapping("/request")
    public ResponseEntity<String> requestLeave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody LeaveRequestDTO dto) {
        Employee employee = employeeRepository.findById(userDetails.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setType(dto.getType());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setStatus("PENDING");
        leave.setApplicationDate(LocalDate.now());
        leave.setComments(dto.getComments());
        leave.setApproverID(null); // Could be set later during approval

        leaveRequestRepository.save(leave);
        return ResponseEntity.ok("Leave request submitted successfully.");
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PatchMapping("/approve-or-reject")
    @Transactional
    public ResponseEntity<String> approveOrRejectLeave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody LeaveApprovalRequestDTO dto) {
        LeaveRequest leave = leaveRequestRepository.findById(dto.getLeaveId())
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        Employee employee = employeeRepository.findById(userDetails.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!"APPROVED".equalsIgnoreCase(dto.getStatus()) && !"REJECTED".equalsIgnoreCase(dto.getStatus())) {
            return ResponseEntity.badRequest().body("Status must be APPROVED or REJECTED");
        }

        leave.setApproverID(employee);
        leave.setStatus(dto.getStatus().toUpperCase());
        leave.setComments(dto.getComments());
        leave.setApplicationDate(LocalDate.now());
        leaveRequestRepository.save(leave);

        if ("APPROVED".equalsIgnoreCase(dto.getStatus())) {
            deductLeaveBalance(leave);
        }

        return ResponseEntity.ok("Leave " + dto.getStatus().toUpperCase());
    }

    private void deductLeaveBalance(LeaveRequest leave) {
        Employee employee = leave.getEmployee();
        String leaveType = leave.getType();

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeAndLeaveTypeAndYear(employee, leaveType, LocalDate.now().getYear())
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        int daysRequested = (int) ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;

        if (balance.getAvailableDays() < daysRequested) {
            throw new RuntimeException("Insufficient leave balance");
        }

        balance.setAvailableDays(balance.getAvailableDays() - daysRequested);
        leaveBalanceRepository.save(balance);
    }
}
