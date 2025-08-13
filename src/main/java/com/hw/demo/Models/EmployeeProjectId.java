package com.hw.demo.Models;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeProjectId implements Serializable {
    private Integer employee;
    private Integer project;

    public EmployeeProjectId() {}

    public EmployeeProjectId(Integer employee, Integer project) {
        this.employee = employee;
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProjectId that)) return false;
        return Objects.equals(employee, that.employee) &&
                Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, project);
    }
}
