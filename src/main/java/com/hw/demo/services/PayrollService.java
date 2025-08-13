package com.hw.demo.services;

import com.hw.demo.Models.Bonus;
import com.hw.demo.Models.Deduction;
import com.hw.demo.dto.BonusRequest;
import com.hw.demo.dto.DeductionRequest;
import com.hw.demo.dto.NetSalaryResponse;
import com.hw.demo.Repository.EmployeeRepository;
import com.hw.demo.Repository.EmployeeSalaryRepository;
import com.hw.demo.Repository.BonusRepository;
import com.hw.demo.Repository.DeductionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class PayrollService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryRepository salaryRepository;
    private final BonusRepository bonusRepository;
    private final DeductionRepository deductionRepository;

    public PayrollService(EmployeeRepository employeeRepository,
                          EmployeeSalaryRepository salaryRepository,
                          BonusRepository bonusRepository,
                          DeductionRepository deductionRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
        this.bonusRepository = bonusRepository;
        this.deductionRepository = deductionRepository;
    }

    public NetSalaryResponse calculateNetSalaryForMonth(int employeeId, int year, int month) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Get base salary (if not found, default to 0.00)
        var salaries = salaryRepository.findByEmployee_EmployeeID(employeeId);
        BigDecimal baseSalary = salaries.isEmpty() ? BigDecimal.ZERO : salaries.get(0).getBaseSalary();

        LocalDate start = YearMonth.of(year, month).atDay(1);
        LocalDate end = YearMonth.of(year, month).atEndOfMonth();

        // Sum bonuses
        BigDecimal totalBonus = bonusRepository
                .findByEmployee_EmployeeIDAndDateBetween(employeeId, start, end)
                .stream()
                .map(b -> b.getAmount() == null ? BigDecimal.ZERO : b.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Sum deductions
        BigDecimal totalDeduction = deductionRepository
                .findByEmployee_EmployeeIDAndDateBetween(employeeId, start, end)
                .stream()
                .map(d -> d.getAmount() == null ? BigDecimal.ZERO : d.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netSalary = baseSalary.add(totalBonus).subtract(totalDeduction);

        return new NetSalaryResponse(
                employee.getEmployeeID(),
                employee.getFirstName() + " " + employee.getLastName(),
                baseSalary,
                totalBonus,
                totalDeduction,
                netSalary,
                YearMonth.of(year, month).getMonth().toString()
        );
    }

    public Bonus addBonus(BonusRequest request) {
        var emp = employeeRepository.findById(request.employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Bonus bonus = new Bonus();
        bonus.setEmployee(emp);
        bonus.setAmount(request.amount); // make sure BonusRequest.amount is BigDecimal
        bonus.setReason(request.reason);
        bonus.setDate(request.date);

        return bonusRepository.save(bonus);
    }

    public Deduction addDeduction(DeductionRequest request) {
        var emp = employeeRepository.findById(request.employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Deduction deduction = new Deduction();
        deduction.setEmployee(emp);
        deduction.setAmount(request.amount); // make sure DeductionRequest.amount is BigDecimal
        deduction.setReason(request.reason);
        deduction.setDate(request.date);

        return deductionRepository.save(deduction);
    }
}
