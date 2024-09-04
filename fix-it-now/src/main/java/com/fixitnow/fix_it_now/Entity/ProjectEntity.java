package com.fixitnow.fix_it_now.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "PROJECT")
@Data
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PROJECTS",
            joinColumns = @JoinColumn(name = "USER"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT"))
    private List<UserEntity> userEntities;

    @OneToMany(mappedBy = "project")
    private List<IssueEntity> issueEntities;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
}
