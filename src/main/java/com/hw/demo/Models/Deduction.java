package com.hw.demo.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "Deduction")

@Getter @Setter
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deductionID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String reason;

    @Column(name = "Date")
    private LocalDate date;

    // Getters/Setters
}

