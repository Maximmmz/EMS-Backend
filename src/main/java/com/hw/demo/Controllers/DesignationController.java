package com.hw.demo.Controllers;

import com.hw.demo.Models.Designation;
import com.hw.demo.Repository.DesignationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designation") // Base URL
public class DesignationController {

    private final DesignationRepository designationRepository;

    public DesignationController(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    //CREATE new designation
    @PostMapping
    public Designation createDesignation(@RequestBody Designation newDesignation) {
        return designationRepository.save(newDesignation);
    }

    // GET all designations
    @GetMapping
    public List<Designation> getAllEmployees() {
        return designationRepository.findAll();
    }

    // GET single designation by ID
    @GetMapping("/{id}")
    public Designation getDesignationById(@PathVariable Integer id) {
        return designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found with id " + id));
    }
}
