package com.hw.demo.dto;

import java.time.LocalDate;
import lombok.Setter;
import lombok.Getter;

@Setter @Getter
public class LeaveApprovalRequestDTO {
    private Integer approverID;
    private Integer leaveId;
    private String status; // "APPROVED" or "REJECTED"
    private String comments;
}
