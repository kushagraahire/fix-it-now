package com.fixitnow.fix_it_now.model;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private String role;

    private String status;

    private String profilePicture;

    private List<Project> projects;

    private List<Issue> assignedIssues;

    private Date createdDate;

    private Date updatedDate;
}
