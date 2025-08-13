package com.hw.demo.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BonusRequest {
    public Integer employeeId;
    public BigDecimal amount;
    public String reason;
    public LocalDate date;
}

