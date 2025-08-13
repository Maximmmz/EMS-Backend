package com.hw.demo.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EmployeeSalary")

@Getter @Setter
public class EmployeeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeSalaryID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(precision = 15, scale = 2)
    private BigDecimal baseSalary;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    // Getters/Setters
}

