package com.hw.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "UserRole", schema = "dbo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"EmployeeID", "RoleID"}))
@Getter @Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRoleID")
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.EAGER) // many junctions can refer to same employee
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER) // many junctions can refer to same role
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID", nullable = false)
    private Role role;

    @Column(name = "AssignedDate")
    private LocalDate assignedDate;
}
