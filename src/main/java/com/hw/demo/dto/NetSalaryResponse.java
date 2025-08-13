package com.hw.demo.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetSalaryResponse {
    private Integer employeeId;
    private String employeeName;
    private BigDecimal baseSalary;
    private BigDecimal totalBonus;
    private BigDecimal totalDeduction;
    private BigDecimal netSalary;
    private String month;

    public NetSalaryResponse(Integer employeeId, String employeeName, BigDecimal baseSalary,
                             BigDecimal totalBonus, BigDecimal totalDeduction,
                             BigDecimal netSalary, String month) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.baseSalary = baseSalary;
        this.totalBonus = totalBonus;
        this.totalDeduction = totalDeduction;
        this.netSalary = netSalary;
        this.month = month;
    }

    // getters and setters
}

