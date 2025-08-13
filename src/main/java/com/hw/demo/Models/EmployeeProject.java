package com.hw.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="EmployeeProject", schema="dbo")

@Getter @Setter
@IdClass(com.hw.demo.Models.EmployeeProjectId.class)
public class EmployeeProject {
    @Id
    @ManyToOne
    @JoinColumn(name="EmployeeID", referencedColumnName ="EmployeeID")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name="ProjectID", referencedColumnName = "ProjectID")
    private Project project;

    @Column(name="Role")
    private String role;

    @Column(name="AssignedDate")
    private LocalDate assignedDate;
}
