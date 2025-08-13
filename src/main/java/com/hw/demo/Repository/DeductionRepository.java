package com.hw.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import com.hw.demo.Models.Deduction;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Integer>, DeductionRepositoryCustom {
    List<Deduction> findByEmployee_EmployeeIDAndDateBetween(Integer employeeId, LocalDate start, LocalDate end);
}


