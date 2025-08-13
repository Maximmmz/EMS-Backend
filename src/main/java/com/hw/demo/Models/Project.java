package com.hw.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "Project", schema = "dbo")

@Getter @Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProjectID")
    private Integer projectID;

    @Column(name="Name")
    private String Name;

    @Column(name="Description")
    private String description;

    @Column(name="StartDate")
    private LocalDate startDate;

    @Column(name="EndDate")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "ManagerID", referencedColumnName = "EmployeeID")
    private Employee manager;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeProject> employeeProjects;
}
