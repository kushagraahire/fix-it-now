package com.fixitnow.fix_it_now.controller;

import com.fixitnow.fix_it_now.Entity.ProjectEntity;
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
    public ResponseEntity<Object> addProject(@RequestBody ProjectEntity projectEntity) {
        return ResponseEntity.ok(projectService.addProject(projectEntity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Long id, @RequestBody ProjectEntity projectEntity) {
        return ResponseEntity.ok(projectService.updateProject(id, projectEntity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PutMapping("/add-user")
    public ResponseEntity<ProjectEntity> addUserToProject(@RequestParam Long projectId, @RequestParam Long userId){
        return ResponseEntity.ok(projectService.addUserToProject(projectId, userId));
    }

}
