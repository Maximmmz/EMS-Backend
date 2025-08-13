package com.hw.demo.dto;

public class EmployeeAbsenceDTO {
    private Integer employeeID;
    private Integer absenceCount;

    public EmployeeAbsenceDTO(Integer employeeID, Integer absenceCount) {
        this.employeeID = employeeID;
        this.absenceCount = absenceCount;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Integer getAbsenceCount() {
        return absenceCount;
    }

    public void setAbsenceCount(Integer absenceCount) {
        this.absenceCount = absenceCount;
    }
}

