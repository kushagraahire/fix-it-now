package com.fixitnow.fix_it_now.model;

import com.fixitnow.fix_it_now.Entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Project {
    private Long id;

    private String name;

    private Date updatedDate;

    private List<User> users;

    private List<Issue> issues;

    private String description;

    private Date createdDate;
}
