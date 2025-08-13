package com.hw.demo.Models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendar_days")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDay {

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "working_day", nullable = false)
    private boolean workingDay;

    @Column(name = "holiday_name")
    private String holidayName;
}
