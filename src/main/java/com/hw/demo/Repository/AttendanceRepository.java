package com.hw.demo.Repository;

import com.hw.demo.Models.Attendance;
import com.hw.demo.dto.EmployeeAbsenceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployee_EmployeeIDAndDateBetween(Integer employeeID, LocalDate start, LocalDate end);
    long countByEmployee_EmployeeIDAndDateBetween(Integer employeeID, LocalDate start, LocalDate end);

    @Query(value = """
    SELECT 
        E.EmployeeID AS employeeID,
        COUNT(cd.[date]) AS absenceCount
    FROM Employee E
    CROSS JOIN calendar_days cd
    LEFT JOIN Attendance A
        ON A.EmployeeID = E.EmployeeID
       AND A.[Date] = cd.[date]
    WHERE cd.[date] BETWEEN :startDate AND :endDate
      AND cd.working_day = 1         
      AND E.Status = 'ACTIVE'
      AND A.EmployeeID IS NULL
      AND E.EmployeeID = :employeeID         
    GROUP BY E.EmployeeID
    """, nativeQuery = true)
    EmployeeAbsenceDTO findAbsenceByEmployeeIDAndDateRange(
            @Param("employeeID") Integer employeeID,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    Optional<Attendance> findByEmployeeEmployeeIDAndDate(int employeeId, LocalDate date);

}
