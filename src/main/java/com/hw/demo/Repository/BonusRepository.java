package com.hw.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import com.hw.demo.Models.Bonus;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {
    List<Bonus> findByEmployee_EmployeeIDAndDateBetween(Integer employeeId, LocalDate start, LocalDate end);
}

