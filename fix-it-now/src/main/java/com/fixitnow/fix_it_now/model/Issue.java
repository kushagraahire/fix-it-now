package com.fixitnow.fix_it_now.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ISSUE")
@Data
public class Issue {
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

    @ManyToOne
    @JoinColumn(name = "PROJECT")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "ASSIGNED_USER")
    private User assignedUser;

    @Column(name = "CREATE_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;
}
