package com.hw.demo.Controllers;

import com.hw.demo.Models.Bonus;
import com.hw.demo.Models.Deduction;
import com.hw.demo.dto.BonusRequest;
import com.hw.demo.dto.DeductionRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hw.demo.dto.NetSalaryResponse;
import com.hw.demo.services.PayrollService;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    // Existing endpoint
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @GetMapping("/net-salary/{employeeId}")
    public NetSalaryResponse getNetSalary(
            @PathVariable int employeeId,
            @RequestParam int year,
            @RequestParam int month) {
        return payrollService.calculateNetSalaryForMonth(employeeId, year, month);
    }

    //  Add Bonus
    @PostMapping("/bonus")
    public Bonus addBonus(@RequestBody BonusRequest request) {
        return payrollService.addBonus(request);
    }

    //  Add Deduction
    @PostMapping("/deduction")
    public Deduction addDeduction(@RequestBody DeductionRequest request) {
        return payrollService.addDeduction(request);
    }
}

