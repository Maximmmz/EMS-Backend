package com.hw.demo.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class UpdateEmployeeWithCredsRequest {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;
    private String address;
    private LocalDate hireDate;
    private String status;
    private Integer departmentId;
    private Integer designationId;
    private String username;
    private String passwordHash;
    private String media;
}
