package com.hw.demo.Controllers;

import com.hw.demo.config.CustomUserDetails;
import com.hw.demo.dto.AttendanceRequestDTO;
import com.hw.demo.dto.AttendanceResponseDTO;
import com.hw.demo.Models.Attendance;
import com.hw.demo.Models.Employee;
import com.hw.demo.Repository.AttendanceRepository;
import com.hw.demo.Repository.EmployeeRepository;
import com.hw.demo.dto.EmployeeAbsenceDTO;
import com.hw.demo.dto.TimeOutRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // POST: Mark attendance
    @PostMapping("/time-in")
    public AttendanceResponseDTO createAttendance(
            @RequestBody AttendanceRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        int employeeId = userDetails.getEmployeeId();

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(dto.getDate());
        attendance.setTimeIn(dto.getTimeIn());
        attendance.setStatus(dto.getStatus());

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    //Add a time-out through Patch
    @PatchMapping("/time-out")
    public AttendanceResponseDTO markTimeOut(
            @RequestBody TimeOutRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        int employeeId = userDetails.getEmployeeId();
        LocalDate date = dto.getDate();

        Attendance attendance = attendanceRepository
                .findByEmployeeEmployeeIDAndDate(employeeId, date)
                .orElseThrow(() -> new RuntimeException("No attendance record found for time-out"));

        attendance.setTimeOut(dto.getTimeOut());

        Attendance updated = attendanceRepository.save(attendance);
        return mapToResponse(updated);
    }

    // GET: Get attendance for an employee in a date range
    @GetMapping("/{employeeID}")
    public List<AttendanceResponseDTO> getAttendanceByEmployee(
            @PathVariable Integer employeeID,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        List<Attendance> records = attendanceRepository
                .findByEmployee_EmployeeIDAndDateBetween(employeeID, LocalDate.parse(startDate), LocalDate.parse(endDate));
        return records.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/count/{employeeID}")
    public Map<String, Object> getEmployeeAttendanceCount(
            @PathVariable Integer employeeID,
            @RequestParam String startDate,
            @RequestParam String endDate
    )
    {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long count = attendanceRepository.countByEmployee_EmployeeIDAndDateBetween(employeeID, start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee " + employeeID + " has " + count + " attendances between " + start + " and " + end + ".");
        response.put("attendanceCount", count);
        return response;

    }

    // Get absences of all employees
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @GetMapping("/absences/{employeeID}")
    public EmployeeAbsenceDTO getAbsenceForEmployee(
            @PathVariable Integer employeeID,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return attendanceRepository.findAbsenceByEmployeeIDAndDateRange(employeeID, startDate, endDate);
    }



    // Utility: Convert Entity -> DTO
    private AttendanceResponseDTO mapToResponse(Attendance a) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setAttendanceId(a.getAttendanceId());
        dto.setEmployeeId(a.getEmployee().getEmployeeID());
        dto.setDate(a.getDate());
        dto.setTimeIn(a.getTimeIn());
        dto.setStatus(a.getStatus());
        return dto;
    }
}
