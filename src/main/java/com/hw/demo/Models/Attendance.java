package com.hw.demo.Models;

import com.hw.demo.Models.Employee;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Attendance")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "TimeIn")
    private LocalTime timeIn;

    @Column(name = "TimeOut")
    private LocalTime timeOut;

    @Column(name = "Status")
    private String status; // Example: PRESENT, ABSENT, LATE, etc.
}
