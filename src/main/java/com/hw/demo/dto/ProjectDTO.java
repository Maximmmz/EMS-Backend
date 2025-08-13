package com.hw.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectDTO {
    private Integer projectId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer managerID;
    private String managerName;
}
