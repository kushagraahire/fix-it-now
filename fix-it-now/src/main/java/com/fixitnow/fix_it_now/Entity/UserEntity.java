package com.fixitnow.fix_it_now.Entity;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROFILE_PICTURE")
    private String profilePicture;

    @ManyToMany(mappedBy = "users")
    private List<ProjectEntity> projectEntities;

    @OneToMany(mappedBy = "assignedUser")
    private List<IssueEntity> assignedIssueEntities;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
