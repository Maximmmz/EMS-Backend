package com.hw.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class TimeOutRequestDTO {
    private LocalDate date;
    private LocalTime timeOut;
}

