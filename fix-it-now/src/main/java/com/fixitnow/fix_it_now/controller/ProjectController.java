package com.fixitnow.fix_it_now.controller;

import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.addProject(project));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProjectDetails(@PathVariable Long id, @RequestBody Project project) {
        return ResponseEntity.ok(projectService.updateProjectDetails(id, project));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

}
