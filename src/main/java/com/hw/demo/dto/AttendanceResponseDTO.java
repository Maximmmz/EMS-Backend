package com.hw.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceResponseDTO {
    private Integer attendanceId;
    private Integer employeeId;
    private LocalDate date;
    private LocalTime timeIn;
    private String status;
}
