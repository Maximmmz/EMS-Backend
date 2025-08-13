package com.hw.demo.Controllers;

import com.hw.demo.Models.EmployeeCreds;
import com.hw.demo.dto.CreateCredsRequest;
import com.hw.demo.dto.LoginRequest;
import com.hw.demo.dto.LoginResponse;
import com.hw.demo.services.CustomUserDetailsService;
import com.hw.demo.services.EmployeeCredsService;
import com.hw.demo.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final EmployeeCredsService employeeCredsService;

    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            EmployeeCredsService employeeCredsService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.employeeCredsService = employeeCredsService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Connected");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Extract the actual user (UserDetails)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generate the JWT using the correct type
        String token = jwtUtil.generateToken(userDetails);

        // Create the cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set to true in production (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }



    @PostMapping("/create-creds")
    public ResponseEntity<?> createCreds(@RequestBody CreateCredsRequest request) {
        EmployeeCreds creds = employeeCredsService.createCreds(
                request.getEmployeeId(),
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(creds);
    }
}
