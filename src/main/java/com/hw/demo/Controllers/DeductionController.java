package com.hw.demo.Controllers;

import com.hw.demo.Repository.DeductionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deductions")
public class DeductionController {

    private final DeductionRepository deductionRepository;

    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EXECUTIVE')")
    @PostMapping("/generate-unexcused")
    public ResponseEntity<String> generateUnexcusedAbsences(
            @RequestParam int month,
            @RequestParam int year) {
        deductionRepository.insertUnexcusedAbsences(month, year);
        return ResponseEntity.ok("Unexcused absence deductions generated.");
    }

}
