package com.hw.demo.Models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EmployeeCreds", schema = "dbo")
@Getter @Setter
public class EmployeeCreds implements UserDetails {

    @Id
    @Column(name = "EmployeeID")
    private Integer employeeId;

    // MapsId means: use employeeId as both the PK of this entity and FK to Employee
    @OneToOne
    @MapsId
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    private Employee employee;

    @Column(name = "Username", unique = true)
    private String username;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Column(name = "Media")
    private String media;

    // ===== UserDetails implementation =====
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
