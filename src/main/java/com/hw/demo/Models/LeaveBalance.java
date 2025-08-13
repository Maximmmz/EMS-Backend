package com.hw.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LeaveBalance")
@Getter
@Setter
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveBalanceID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(name = "LeaveType", length = 50)
    private String leaveType;

    @Column(name = "AvailableDays")
    private Integer availableDays;

    @Column(name = "Year")
    private Integer year;
}
