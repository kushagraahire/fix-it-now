package com.fixitnow.fix_it_now.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ISSUE")
@Data
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISSUE_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "PROJECT")
    private ProjectEntity projectEntity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ASSIGNED_USER")
    private UserEntity assignedUserEntity;

    @Column(name = "CREATE_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updatedDate;
}
