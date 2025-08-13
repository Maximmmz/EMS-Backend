package com.hw.demo.Repository;

import com.hw.demo.Models.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    // Add custom queries if needed
}
