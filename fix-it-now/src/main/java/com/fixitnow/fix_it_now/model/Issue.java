package com.fixitnow.fix_it_now.model;

import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import com.fixitnow.fix_it_now.Entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Issue {
    private Long id;
    private String title;

    private String description;

    private String status;

    private Project project;

    private User assignedUser;

    private Date createdDate;

    private Date updatedDate;
}
