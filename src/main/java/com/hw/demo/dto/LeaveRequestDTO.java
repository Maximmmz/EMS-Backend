package com.hw.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestDTO {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comments;
}
