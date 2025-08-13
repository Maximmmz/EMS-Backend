package com.hw.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeAssignmentDTO {
    private Integer employeeID;
    private String role;
    private LocalDate assignedDate;
}
