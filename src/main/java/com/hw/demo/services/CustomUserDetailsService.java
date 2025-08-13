package com.hw.demo.services;

import com.hw.demo.Models.Employee;
import com.hw.demo.Models.EmployeeCreds;
import com.hw.demo.Models.UserRole;
import com.hw.demo.Repository.EmployeeCredsRepository;
import com.hw.demo.Repository.EmployeeRepository;
import com.hw.demo.Repository.UserRoleRepository;
import com.hw.demo.config.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeCredsRepository credsRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(EmployeeCredsRepository credsRepository,
                                    EmployeeRepository employeeRepository,
                                    UserRoleRepository userRoleRepository) {
        this.credsRepository = credsRepository;
        this.employeeRepository = employeeRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeCreds creds = credsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Employee employee = employeeRepository.findById(creds.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found: " + username));

        int level = employee.getDesignation().getLevel();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // 1️⃣ Add authorities from Role table (via UserRole junction)
        List<UserRole> userRoles = userRoleRepository.findByEmployeeEmployeeID(employee.getEmployeeID());
        for (UserRole ur : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + ur.getRole().getRoleName()));
        }

        // 2️⃣ Optionally also add hierarchy-based authorities
        for (int i = 1; i <= level; i++) {
            authorities.add(new SimpleGrantedAuthority("LEVEL_" + i));
        }

        return new CustomUserDetails(
                employee.getEmployeeID(),
                level,
                creds.getUsername(),
                creds.getPassword(),
                authorities
        );
    }
}
