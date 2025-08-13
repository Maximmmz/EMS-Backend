package com.hw.demo.Controllers;

import com.hw.demo.Models.EmployeeProject;
import com.hw.demo.Models.EmployeeProjectId;
import com.hw.demo.Repository.EmployeeProjectRepository;
import com.hw.demo.Repository.EmployeeRepository;
import com.hw.demo.dto.EmployeeAssignmentDTO;
import com.hw.demo.dto.EmployeeProjectBatchDTO;
import com.hw.demo.dto.ProjectDTO;
import com.hw.demo.Models.Employee;
import com.hw.demo.Models.Project;
import com.hw.demo.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    //POST for inserting project
    @PostMapping("/create-project")
    @PreAuthorize("hasAnyRole('MANAGER', 'EXECUTIVE')")
    public Project createProject(@RequestBody Project project) {
        if (project.getManager() != null && project.getManager().getEmployeeID() != null) {
            Employee manager = employeeRepository.findById(project.getManager().getEmployeeID())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            project.setManager(manager);
        } else {
            project.setManager(null); // Optional manager
        }

        return projectRepository.save(project);
    }

    @PostMapping("/batch-employees")
    public List<EmployeeProject> assignBatch(@RequestBody EmployeeProjectBatchDTO batchDTO) {
        Project project = projectRepository.findById(batchDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<EmployeeProject> result = new ArrayList<>();

        for (EmployeeAssignmentDTO assignment : batchDTO.getAssignments()) {
            Employee employee = employeeRepository.findById(assignment.getEmployeeID())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + assignment.getEmployeeID()));

            EmployeeProject ep = new EmployeeProject();
            ep.setProject(project);
            ep.setEmployee(employee);
            ep.setRole(assignment.getRole());
            ep.setAssignedDate(assignment.getAssignedDate());

            // Prevent duplicates
            EmployeeProjectId epId = new EmployeeProjectId(employee.getEmployeeID(), project.getProjectID());
            if (!employeeProjectRepository.existsById(epId)) {
                result.add(employeeProjectRepository.save(ep));
            }
        }

        return result;
    }


    // Get all projects
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EXECUTIVE')")
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream().map(project -> {
            ProjectDTO dto = new ProjectDTO();
            dto.setProjectId(project.getProjectID());
            dto.setName(project.getName());
            dto.setDescription(project.getDescription());
            dto.setStartDate(project.getStartDate());
            dto.setEndDate(project.getEndDate());

            Employee manager = project.getManager();
            if (manager != null) {
                dto.setManagerID(manager.getEmployeeID());
                dto.setManagerName(manager.getFirstName() + " " + manager.getLastName());
            } else {
                dto.setManagerName("Unassigned");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // Get by ID
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectDTO dto = new ProjectDTO();
        dto.setProjectId(project.getProjectID());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());

        Employee manager = project.getManager();
        dto.setManagerID(manager.getEmployeeID());
        dto.setManagerName(manager != null
                ? manager.getFirstName() + " " + manager.getLastName()
                : "Unassigned");

        return dto;
    }

}
