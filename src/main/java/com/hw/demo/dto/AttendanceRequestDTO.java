package com.hw.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequestDTO {
    private LocalDate date;
    private LocalTime timeIn;
    private String status; // e.g., PRESENT, ABSENT, LATE
}
