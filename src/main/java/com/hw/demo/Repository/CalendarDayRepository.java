package com.hw.demo.Repository;

import com.hw.demo.Models.CalendarDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarDayRepository extends JpaRepository<CalendarDay, LocalDate> {
    List<CalendarDay> findByWorkingDayTrueAndDateBetween(LocalDate start, LocalDate end);
}
