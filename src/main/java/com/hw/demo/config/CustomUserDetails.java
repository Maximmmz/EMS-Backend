package com.hw.demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final Integer employeeId;
    private final int level;
    private final String username;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Integer employeeId,
                             int level,
                             String username,
                             String password,
                             List<? extends GrantedAuthority> authorities) {
        this.employeeId = employeeId;
        this.level = level;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // 👉 Expose custom fields
    public Integer getEmployeeId() {
        return employeeId;
    }

    public int getLevel() {
        return level;
    }

    // 👉 Implement UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
