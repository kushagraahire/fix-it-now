package com.fixitnow.fix_it_now.controller;

import com.fixitnow.fix_it_now.model.Issue;
import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addIssue(@RequestBody Issue issue) {
        return ResponseEntity.ok(issueService.addIssue(issue));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateIssue(@PathVariable Long id, @RequestBody Issue issue) {
        return ResponseEntity.ok(issueService.updateIssue(id, issue));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.deleteIssue(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssue(id));
    }
}
