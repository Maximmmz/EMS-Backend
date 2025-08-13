package com.hw.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class CreateEmployeeWithCredsRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;
    private String address;
    private String photo;
    private LocalDate hireDate;
    private String status;
    private Integer departmentId;
    private Integer designationId;
    private Integer managerId;

    private String username;
    private String password;
    private Integer roleId;
}

