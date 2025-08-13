package com.hw.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeProjectBatchDTO {
    private Integer projectID;
    private List<EmployeeAssignmentDTO> assignments;
}
